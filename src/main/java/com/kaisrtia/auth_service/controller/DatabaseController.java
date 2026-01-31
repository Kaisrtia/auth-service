package com.kaisrtia.auth_service.controller;

import com.kaisrtia.auth_service.util.DatabaseInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for database inspection endpoints.
 * WARNING: Only use in development! Remove or secure in production.
 */
@RestController
@RequestMapping("/api/database")
public class DatabaseController {
    
    @Autowired
    private DatabaseInspector databaseInspector;
    
    /**
     * GET /api/database/tables
     * Returns list of all tables
     */
    @GetMapping("/tables")
    public ResponseEntity<Map<String, Object>> listTables() {
        List<String> tables = databaseInspector.listAllTables();
        Map<String, Object> response = new HashMap<>();
        response.put("tables", tables);
        response.put("count", tables.size());
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/database/tables/{tableName}/count
     * Returns row count for a specific table
     */
    @GetMapping("/tables/{tableName}/count")
    public ResponseEntity<Map<String, Object>> countRows(@PathVariable String tableName) {
        long count = databaseInspector.countRows(tableName);
        Map<String, Object> response = new HashMap<>();
        response.put("table", tableName);
        response.put("rowCount", count);
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/database/tables/{tableName}/describe
     * Returns table structure
     */
    @GetMapping("/tables/{tableName}/describe")
    public ResponseEntity<String> describeTable(@PathVariable String tableName) {
        databaseInspector.describeTable(tableName);
        return ResponseEntity.ok("Table structure logged. Check application logs.");
    }
    
    /**
     * GET /api/database/summary
     * Prints complete database summary to logs
     */
    @GetMapping("/summary")
    public ResponseEntity<String> getDatabaseSummary() {
        databaseInspector.printDatabaseSummary();
        return ResponseEntity.ok("Database summary logged. Check application logs.");
    }
}
