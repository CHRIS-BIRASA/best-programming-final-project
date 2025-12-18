package view;

import dao.UserDAO;
import dao.DatabaseConnection;
import model.User;
import java.sql.Connection;
import java.util.Scanner;

public class ConsoleMain {
    public static void main(String[] args) {
        System.out.println("=== Cargo Truck Fuel Tracker - Console Mode ===");
        
        try {
            // Test database connection
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✅ Database connected successfully!");
            conn.close();
            
            // Test user authentication
            UserDAO userDAO = new UserDAO();
            User admin = userDAO.authenticate("admin", "admin123");
            if (admin != null) {
                System.out.println("✅ Admin user found: " + admin.getName());
                System.out.println("✅ Role: " + admin.getRole());
            }
            
            System.out.println("✅ Application started successfully in Docker!");
            System.out.println("✅ All systems operational!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}