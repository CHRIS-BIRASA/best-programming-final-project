package view;

import dao.DatabaseConnection;
import java.sql.*;

public class TestUserDB {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Show users table structure
            System.out.println("=== USERS TABLE STRUCTURE ===");
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "users", null);
            while (columns.next()) {
                System.out.println(columns.getString("COLUMN_NAME") + " - " + 
                    columns.getString("TYPE_NAME") + " - " + 
                    columns.getString("IS_AUTOINCREMENT"));
            }
            
            // Show existing users
            System.out.println("\n=== EXISTING USERS ===");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("userId") + 
                    ", Name: " + rs.getString("name") + 
                    ", Role: " + rs.getString("role"));
            }
            
            // Test if we can insert with specific ID
            System.out.println("\n=== TESTING INSERT WITH ID 999 ===");
            try {
                PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO users (userId, name, role, username, password) VALUES (?, ?, ?, ?, ?)");
                insertStmt.setInt(1, 999);
                insertStmt.setString(2, "Test User");
                insertStmt.setString(3, "Driver");
                insertStmt.setString(4, "testuser");
                insertStmt.setString(5, "test123");
                int result = insertStmt.executeUpdate();
                System.out.println("Insert result: " + result);
                
                // Clean up test data
                PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM users WHERE userId = 999");
                deleteStmt.executeUpdate();
                System.out.println("Test data cleaned up");
            } catch (SQLException e) {
                System.out.println("Insert failed: " + e.getMessage());
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}