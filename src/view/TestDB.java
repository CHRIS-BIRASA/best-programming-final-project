package view;

import dao.DatabaseConnection;
import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Show all tables
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            System.out.println("=== TABLES ===");
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }
            
            // Show trucks table structure
            System.out.println("\n=== TRUCKS TABLE COLUMNS ===");
            ResultSet columns = metaData.getColumns(null, null, "trucks", null);
            while (columns.next()) {
                System.out.println(columns.getString("COLUMN_NAME") + " - " + columns.getString("TYPE_NAME"));
            }
            
            // Show existing data
            System.out.println("\n=== EXISTING TRUCKS DATA ===");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM trucks LIMIT 3");
            ResultSetMetaData rsmd = rs.getMetaData();
            
            // Print column headers
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println();
            
            // Print data
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}