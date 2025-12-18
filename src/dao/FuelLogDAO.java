package dao;

import model.FuelLog;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuelLogDAO {
    
    public boolean addFuelLog(FuelLog log) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO fuel_logs (truck_id, user_id, date, fuel_amount, fuel_price, mileage) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1, log.getTruckId());
            stmt.setInt(2, log.getUserId());
            stmt.setDate(3, Date.valueOf(log.getDate()));
            stmt.setDouble(4, log.getFuelAmount());
            stmt.setDouble(5, log.getFuelPrice());
            stmt.setDouble(6, log.getMileage());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<FuelLog> getFuelLogsByDriver(int userId) {
        List<FuelLog> logs = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fuel_logs WHERE user_id = ? ORDER BY date DESC")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FuelLog log = new FuelLog(rs.getInt("truck_id"), rs.getInt("user_id"), 
                    rs.getDate("date").toLocalDate(), rs.getDouble("fuel_amount"), 
                    rs.getDouble("fuel_price"), rs.getDouble("mileage"));
                log.setLogId(rs.getInt("log_id"));
                log.setFuelCost(rs.getDouble("fuel_cost"));
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    public List<FuelLog> getAllFuelLogs() {
        List<FuelLog> logs = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fuel_logs ORDER BY date DESC");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FuelLog log = new FuelLog(rs.getInt("truck_id"), rs.getInt("user_id"), 
                    rs.getDate("date").toLocalDate(), rs.getDouble("fuel_amount"), 
                    rs.getDouble("fuel_price"), rs.getDouble("mileage"));
                log.setLogId(rs.getInt("log_id"));
                log.setFuelCost(rs.getDouble("fuel_cost"));
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}