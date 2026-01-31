package com.kaisrtia.auth_service.config;

import com.kaisrtia.auth_service.util.DatabaseInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for database initialization and startup tasks.
 */
@Configuration
public class DatabaseConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Autowired
    private DatabaseInspector databaseInspector;
    
    /**
     * Runs on application startup to display database information
     */
    @Bean
    public CommandLineRunner databaseStartupInfo() {
        return args -> {
            logger.info("\n\n");
            logger.info("╔════════════════════════════════════════════════════════════╗");
            logger.info("║           H2 DATABASE CONSOLE ACCESS                       ║");
            logger.info("╠════════════════════════════════════════════════════════════╣");
            logger.info("║  URL:      http://localhost:8080/h2-console               ║");
            logger.info("║  JDBC URL: jdbc:h2:file:./data/authdb                     ║");
            logger.info("║  Username: sa                                              ║");
            logger.info("║  Password: password                                        ║");
            logger.info("╠════════════════════════════════════════════════════════════╣");
            logger.info("║  Database File Location: ./data/authdb.mv.db               ║");
            logger.info("╚════════════════════════════════════════════════════════════╝");
            logger.info("\n");
            
            // Print database summary on startup
            databaseInspector.printDatabaseSummary();
        };
    }
}
