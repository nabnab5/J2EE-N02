package com.example.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBconnection {
    private Connection con;

    public DBconnection() {
        con = null;
    }

    public Connection getConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish connection
            String hostname = "localhost";
            String database = "db1";
            String username = "root";
            String password = "";
            String url = "jdbc:mysql://" + hostname + ":3306/" + database; // Include the port number separately
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connect successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
        } catch (SQLException e) {
            System.out.println("Connection Failed. Check output console.");
            e.printStackTrace();
        }
        return con;
    }
    
    public int authenticateUser(String email, String password) {
        int count = 0;
        try {
            if (con == null || con.isClosed()) {
                con = getConnection();
            }
            // Create SQL statement with parameterized query
            String sql = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            
            // Set parameters to avoid SQL injection
            statement.setString(1, email);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the ResultSet
            if (resultSet.next()) {
                count = resultSet.getInt(1); // Retrieve count of rows
            }

            // Close resources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to execute query.");
            e.printStackTrace();
        }
        return count;
    }

}
