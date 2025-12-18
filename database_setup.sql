-- Cargo Truck Fuel Tracker Database Setup
-- Run this script in MySQL to create the database and tables

-- Create database
CREATE DATABASE IF NOT EXISTS cargo_fuel_tracker_db;
USE cargo_fuel_tracker_db;

-- Create Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role ENUM('Admin', 'Manager', 'Driver') NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Trucks table
CREATE TABLE IF NOT EXISTS trucks (
    truck_id INT AUTO_INCREMENT PRIMARY KEY,
    truck_code VARCHAR(20) UNIQUE NOT NULL,
    model VARCHAR(100) NOT NULL,
    license_plate VARCHAR(20) UNIQUE NOT NULL,
    assigned_driver_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (assigned_driver_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Create Fuel Logs table
CREATE TABLE IF NOT EXISTS fuel_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    truck_id INT NOT NULL,
    user_id INT NOT NULL,
    date DATE NOT NULL,
    fuel_amount DECIMAL(10,2) NOT NULL,
    fuel_price DECIMAL(10,2) NOT NULL,
    mileage DECIMAL(10,2) NOT NULL,
    fuel_cost DECIMAL(10,2) GENERATED ALWAYS AS (fuel_amount * fuel_price) STORED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (truck_id) REFERENCES trucks(truck_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);



-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_trucks_code ON trucks(truck_code);
CREATE INDEX idx_trucks_assigned_driver ON trucks(assigned_driver_id);
CREATE INDEX idx_fuel_logs_truck ON fuel_logs(truck_id);
CREATE INDEX idx_fuel_logs_user ON fuel_logs(user_id);
CREATE INDEX idx_fuel_logs_date ON fuel_logs(date);


-- Insert default admin user
INSERT INTO users (name, role, username, password) VALUES 
('System Admin', 'Admin', 'admin', 'admin123')
ON DUPLICATE KEY UPDATE name = VALUES(name);

COMMIT;