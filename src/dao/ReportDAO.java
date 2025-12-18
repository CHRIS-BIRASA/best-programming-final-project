package dao;

import model.Report;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    
    public Report generateFuelSummary(LocalDate dateFrom, LocalDate dateTo) {
        Report report = new Report("Fuel Summary");
        report.setDateFrom(dateFrom);
        report.setDateTo(dateTo);
        
        String sql = "SELECT fl.*, t.truck_code, u.name FROM fuel_logs fl " +
                    "JOIN trucks t ON fl.truck_id = t.truck_id " +
                    "JOIN users u ON fl.user_id = u.user_id " +
                    "WHERE fl.date BETWEEN ? AND ? ORDER BY fl.date DESC";
        
        List<String> data = new ArrayList<>();
        double totalCost = 0, totalAmount = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(dateFrom));
            stmt.setDate(2, Date.valueOf(dateTo));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String line = String.format("%s | %s | %s | %.2fL | $%.2f",
                    rs.getDate("date"), rs.getString("truck_code"),
                    rs.getString("name"), rs.getDouble("fuel_amount"),
                    rs.getDouble("fuel_cost"));
                data.add(line);
                totalCost += rs.getDouble("fuel_cost");
                totalAmount += rs.getDouble("fuel_amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        report.setData(data);
        report.setTotalFuelCost(totalCost);
        report.setTotalFuelAmount(totalAmount);
        return report;
    }
    
    public Report generateDriverPerformance(int driverId, LocalDate dateFrom, LocalDate dateTo) {
        Report report = new Report("Driver Performance");
        report.setUserId(driverId);
        report.setDateFrom(dateFrom);
        report.setDateTo(dateTo);
        
        String sql = "SELECT fl.*, t.truck_code FROM fuel_logs fl " +
                    "JOIN trucks t ON fl.truck_id = t.truck_id " +
                    "WHERE fl.user_id = ? AND fl.date BETWEEN ? AND ? ORDER BY fl.date DESC";
        
        List<String> data = new ArrayList<>();
        double totalCost = 0, totalAmount = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, driverId);
            stmt.setDate(2, Date.valueOf(dateFrom));
            stmt.setDate(3, Date.valueOf(dateTo));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String line = String.format("%s | %s | %.2fL | %.2f miles | $%.2f",
                    rs.getDate("date"), rs.getString("truck_code"),
                    rs.getDouble("fuel_amount"), rs.getDouble("mileage"),
                    rs.getDouble("fuel_cost"));
                data.add(line);
                totalCost += rs.getDouble("fuel_cost");
                totalAmount += rs.getDouble("fuel_amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        report.setData(data);
        report.setTotalFuelCost(totalCost);
        report.setTotalFuelAmount(totalAmount);
        return report;
    }
}