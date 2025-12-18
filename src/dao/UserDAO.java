package dao;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public User authenticate(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("role"), rs.getString("username"), rs.getString("password"));
                user.setUserId(rs.getInt("user_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addUser(User user) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, role, username, password) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean addUserWithId(User user) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE user_id = ?");
            checkStmt.setInt(1, user.getUserId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("ID " + user.getUserId() + " already exists");
                return false;
            }
            
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (user_id, name, role, username, password) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<User> getAllDrivers() {
        List<User> drivers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE role = 'Driver'");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("role"), rs.getString("username"), rs.getString("password"));
                user.setUserId(rs.getInt("user_id"));
                drivers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }
    
    public boolean updateUser(User user) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET name = ?, role = ?, username = ?, password = ? WHERE user_id = ?")) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateUserWithId(User user, int oldId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (user.getUserId() != oldId) {
                PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE user_id = ? AND user_id != ?");
                checkStmt.setInt(1, user.getUserId());
                checkStmt.setInt(2, oldId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("New ID " + user.getUserId() + " already exists for another user");
                    return false;
                }
            }
            
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET user_id = ?, name = ?, role = ?, username = ?, password = ? WHERE user_id = ?");
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, oldId);
            int result = stmt.executeUpdate();
            System.out.println("Update result: " + result + " for oldId: " + oldId + " newId: " + user.getUserId());
            return result > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error in update: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteUser(int userId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public User getUserById(int userId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("role"), rs.getString("username"), rs.getString("password"));
                user.setUserId(rs.getInt("user_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}