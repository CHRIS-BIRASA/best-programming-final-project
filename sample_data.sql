-- Sample Data for Cargo Fuel Tracker Database
USE cargo_fuel_tracker_db;

-- Insert sample users (you can replace these names with your own)
INSERT INTO users (name, role, username, password) VALUES
('John Smith', 'Driver', 'jsmith', 'password123'),
('Maria Garcia', 'Driver', 'mgarcia', 'password123'),
('Robert Johnson', 'Driver', 'rjohnson', 'password123'),
('Sarah Wilson', 'Manager', 'swilson', 'manager123'),
('David Brown', 'Driver', 'dbrown', 'password123');

-- Insert sample trucks
INSERT INTO trucks (truck_code, model, license_plate, assigned_driver_id) VALUES
('TRK001', 'Volvo FH16', 'ABC-1234', 2),
('TRK002', 'Mercedes Actros', 'DEF-5678', 3),
('TRK003', 'Scania R500', 'GHI-9012', 4),
('TRK004', 'MAN TGX', 'JKL-3456', 6),
('TRK005', 'Iveco Stralis', 'MNO-7890', NULL);

-- Insert sample fuel logs
INSERT INTO fuel_logs (truck_id, user_id, date, fuel_amount, fuel_price, mileage) VALUES
(1, 2, '2024-01-15', 150.50, 1.45, 1250.75),
(2, 3, '2024-01-15', 180.25, 1.47, 1450.30),
(1, 2, '2024-01-16', 165.00, 1.46, 1420.50),
(3, 4, '2024-01-16', 175.75, 1.44, 1380.25),
(4, 6, '2024-01-17', 155.30, 1.48, 1320.80);

COMMIT;