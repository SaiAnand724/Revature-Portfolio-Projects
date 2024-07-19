package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    // Postgres DB Configuration through JDBC
    private static final String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema={insert schema here}";
    private static final String username = "postgres";
    private static final String password = "{insert postgres password here}";

    // Connection method that throws SQLException if the connection can't be made
    public static Connection getConnection() throws SQLException {

        // Register PGSQL Driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // Print error stack trace if Driver can't be found
            e.printStackTrace();
            System.out.println("Unable to locate driver");
        }

        // Return Connection object
        return DriverManager.getConnection(url, username, password);
    }
}
