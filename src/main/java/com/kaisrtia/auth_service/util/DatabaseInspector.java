package com.kaisrtia.auth_service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for inspecting H2 database contents programmatically.
 * Useful for debugging and monitoring database state.
 */
@Component
public class DatabaseInspector {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInspector.class);
    
    @Autowired
    private DataSource dataSource;
    
    /**
     * Lists all tables in the database
     */
    public List<String> listAllTables() {
        List<String> tables = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            logger.info("=== Database Tables ===");
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
                logger.info("Table: {}", tableName);
            }
            logger.info("Total tables: {}", tables.size());
        } catch (Exception e) {
            logger.error("Error listing tables", e);
        }
        return tables;
    }
    
    /**
     * Shows the structure of a specific table
     */
    public void describeTable(String tableName) {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            
            logger.info("=== Table Structure: {} ===", tableName);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("TYPE_NAME");
                int columnSize = rs.getInt("COLUMN_SIZE");
                String isNullable = rs.getString("IS_NULLABLE");
                
                logger.info("Column: {} | Type: {}({}) | Nullable: {}", 
                    columnName, columnType, columnSize, isNullable);
            }
        } catch (Exception e) {
            logger.error("Error describing table: {}", tableName, e);
        }
    }
    
    /**
     * Counts rows in a specific table
     */
    public long countRows(String tableName) {
        long count = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
            if (rs.next()) {
                count = rs.getLong(1);
            }
            logger.info("Table '{}' has {} rows", tableName, count);
        } catch (Exception e) {
            logger.error("Error counting rows in table: {}", tableName, e);
        }
        return count;
    }
    
    /**
     * Executes a custom query and logs results
     */
    public void executeQuery(String query) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery(query);
            int columnCount = rs.getMetaData().getColumnCount();
            
            logger.info("=== Query Results ===");
            logger.info("Query: {}", query);
            
            // Print column names
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                header.append(rs.getMetaData().getColumnName(i)).append("\t");
            }
            logger.info(header.toString());
            
            // Print rows
            int rowCount = 0;
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(rs.getString(i)).append("\t");
                }
                logger.info(row.toString());
                rowCount++;
            }
            logger.info("Total rows: {}", rowCount);
        } catch (Exception e) {
            logger.error("Error executing query: {}", query, e);
        }
    }
    
    /**
     * Prints a complete database summary
     */
    public void printDatabaseSummary() {
        logger.info("========================================");
        logger.info("     DATABASE SUMMARY");
        logger.info("========================================");
        
        List<String> tables = listAllTables();
        
        for (String table : tables) {
            logger.info("\n--- Table: {} ---", table);
            describeTable(table);
            countRows(table);
        }
        
        logger.info("========================================");
    }
}
