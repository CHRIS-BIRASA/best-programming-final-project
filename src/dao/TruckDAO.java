package dao;

import model.Truck;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TruckDAO {
    
    public boolean addTruck(Truck truck) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO trucks (truck_code, model, license_plate, assigned_driver_id) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, truck.getTruckCode());
            stmt.setString(2, truck.getModel());
            stmt.setString(3, truck.getLicensePlate());
            stmt.setObject(4, truck.getAssignedDriverId() == 0 ? null : truck.getAssignedDriverId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateTruck(Truck truck) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE trucks SET truck_code=?, model=?, license_plate=?, assigned_driver_id=? WHERE truck_id=?")) {
            stmt.setString(1, truck.getTruckCode());
            stmt.setString(2, truck.getModel());
            stmt.setString(3, truck.getLicensePlate());
            stmt.setObject(4, truck.getAssignedDriverId() == 0 ? null : truck.getAssignedDriverId());
            stmt.setInt(5, truck.getTruckId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteTruck(int truckId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM trucks WHERE truck_id=?")) {
            stmt.setInt(1, truckId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Truck> getAllTrucks() {
        List<Truck> trucks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM trucks");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Truck truck = new Truck(rs.getString("truck_code"), rs.getString("model"), rs.getString("license_plate"));
                truck.setTruckId(rs.getInt("truck_id"));
                truck.setAssignedDriverId(rs.getObject("assigned_driver_id") != null ? rs.getInt("assigned_driver_id") : 0);
                trucks.add(truck);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trucks;
    }
    
    public Truck getTruckById(int truckId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM trucks WHERE truck_id=?")) {
            stmt.setInt(1, truckId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Truck truck = new Truck(rs.getString("truck_code"), rs.getString("model"), rs.getString("license_plate"));
                truck.setTruckId(rs.getInt("truck_id"));
                truck.setAssignedDriverId(rs.getObject("assigned_driver_id") != null ? rs.getInt("assigned_driver_id") : 0);
                return truck;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Truck> getTrucksByDriver(int driverId) {
        List<Truck> trucks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM trucks WHERE assigned_driver_id = ?")) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Truck truck = new Truck(rs.getString("truck_code"), rs.getString("model"), rs.getString("license_plate"));
                truck.setTruckId(rs.getInt("truck_id"));
                truck.setAssignedDriverId(rs.getInt("assigned_driver_id"));
                trucks.add(truck);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trucks;
    }
}