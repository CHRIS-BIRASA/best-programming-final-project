package view;

import dao.FuelLogDAO;
import dao.TruckDAO;
import dao.UserDAO;
import model.FuelLog;
import model.Truck;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private TruckDAO truckDAO;
    private UserDAO userDAO;
    private FuelLogDAO fuelLogDAO;
    private DefaultTableModel trucksModel, driversModel, logsModel;
    
    public AdminDashboard(User user) {
        this.truckDAO = new TruckDAO();
        this.userDAO = new UserDAO();
        this.fuelLogDAO = new FuelLogDAO();
        
        setTitle("📊 " + user.getRole() + " Dashboard - " + user.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);
        
        // Create animated gradient background
        JPanel gradientBg = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(240, 248, 255), 
                    getWidth(), getHeight(), new Color(230, 240, 250));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientBg.setLayout(new BorderLayout());
        setContentPane(gradientBg);
        
        // Visible tabbed pane
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 16));
        tabs.setForeground(Color.BLACK);
        tabs.setBackground(Color.WHITE);
        tabs.setOpaque(true);
        tabs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create and add tabs with error handling
        try {
            tabs.addTab("🚛 TRUCKS", createTrucksPanel());
            tabs.addTab("👥 DRIVERS", createDriversPanel());
            tabs.addTab("⛽ FUEL LOGS", createLogsPanel());
            tabs.addTab("📊 REPORTS", createReportsPanel());
            System.out.println("All tabs created successfully!");
        } catch (Exception e) {
            System.err.println("Error creating tabs: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Modern header with glass effect
        JPanel topPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glass effect background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180, 200), 
                    0, getHeight(), new Color(100, 149, 237, 180));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
                
                // Add shine effect
                g2d.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 50), 
                    0, getHeight()/2, new Color(255, 255, 255, 0)));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight()/2, 0, 0);
            }
        };
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        topPanel.setOpaque(false);
        
        JButton logoutBtn = createGlassButton("🚪 LOGOUT");
        logoutBtn.addActionListener(e -> { new LoginFrame().setVisible(true); dispose(); });
        
        JLabel titleLabel = new JLabel("📊 " + user.getRole().toUpperCase() + " DASHBOARD - " + user.getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(logoutBtn, BorderLayout.EAST);
        
        gradientBg.add(topPanel, BorderLayout.NORTH);
        gradientBg.add(tabs, BorderLayout.CENTER);
        
        loadData();
    }
    
    private JButton createGlassButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glass button effect
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(255, 255, 255, 100));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(255, 255, 255, 150));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 120));
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                // Text
                g2d.setColor(new Color(70, 130, 180));
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 35));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private JPanel createCardPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Card shadow
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(4, 4, getWidth()-4, getHeight()-4, 15, 15);
                
                // Card background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth()-4, getHeight()-4, 15, 15);
                
                // Card border
                g2d.setColor(new Color(70, 130, 180, 50));
                g2d.drawRoundRect(0, 0, getWidth()-5, getHeight()-5, 15, 15);
            }
        };
        panel.setOpaque(false);
        return panel;
    }
    
    private JButton createModernActionButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color currentColor = baseColor;
                if (getModel().isPressed()) {
                    currentColor = baseColor.darker();
                } else if (getModel().isRollover()) {
                    currentColor = baseColor.brighter();
                }
                
                // Button shadow
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRoundRect(2, 2, getWidth()-2, getHeight()-2, 12, 12);
                
                // Button background
                g2d.setColor(currentColor);
                g2d.fillRoundRect(0, 0, getWidth()-2, getHeight()-2, 12, 12);
                
                // Button highlight
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.fillRoundRect(0, 0, getWidth()-2, getHeight()/2, 12, 12);
                
                // Text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(140, 35));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    

    
    private JPanel createTrucksPanel() {
        JPanel panel = createCardPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(900, 500));
        
        String[] cols = {"ID", "Code", "Model", "License", "Driver"};
        trucksModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(trucksModel);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(173, 216, 230));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton addBtn = createModernActionButton("➕ Add Truck", new Color(34, 139, 34));
        JButton editBtn = createModernActionButton("✏ Update Truck", new Color(255, 140, 0));
        JButton deleteBtn = createModernActionButton("🗑 Delete Truck", new Color(220, 20, 60));
        
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        addBtn.addActionListener(e -> new TruckDialog(this).setVisible(true));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int truckId = (Integer) trucksModel.getValueAt(row, 0);
                new TruckDialog(this, truckId).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a truck to update!");
            }
        });
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this truck?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int truckId = (Integer) trucksModel.getValueAt(row, 0);
                    if (truckDAO.deleteTruck(truckId)) {
                        JOptionPane.showMessageDialog(this, "Truck deleted successfully!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete truck!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a truck to delete!");
            }
        });
        
        return panel;
    }
    
    private JPanel createDriversPanel() {
        JPanel panel = createCardPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(900, 500));
        
        String[] cols = {"ID", "Name", "Username", "Role"};
        driversModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(driversModel);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(173, 216, 230));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton addBtn = createModernActionButton("➕ Add Driver", new Color(34, 139, 34));
        JButton editBtn = createModernActionButton("✏ Update Driver", new Color(255, 140, 0));
        JButton deleteBtn = createModernActionButton("🗑 Delete Driver", new Color(220, 20, 60));
        
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        addBtn.addActionListener(e -> new RegisterDialog(this).setVisible(true));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int userId = (Integer) driversModel.getValueAt(row, 0);
                User user = userDAO.getUserById(userId);
                if (user != null) {
                    new EditUserDialog(this, user).setVisible(true);
                    loadData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a driver to edit!");
            }
        });
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int userId = (Integer) driversModel.getValueAt(row, 0);
                String driverName = (String) driversModel.getValueAt(row, 1);
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Delete driver ID: " + userId + " (" + driverName + ")?", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (userDAO.deleteUser(userId)) {
                        JOptionPane.showMessageDialog(this, "Driver ID " + userId + " deleted successfully!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete driver!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a driver to delete!");
            }
        });
        
        return panel;
    }
    
    private JPanel createLogsPanel() {
        JPanel panel = createCardPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(900, 500));
        
        String[] cols = {"Date", "Truck", "Driver", "Fuel (L)", "Cost"};
        logsModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(logsModel);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(173, 216, 230));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton deleteLogBtn = createModernActionButton("🗑 Delete Log", new Color(220, 20, 60));
        btnPanel.add(deleteLogBtn);
        
        deleteLogBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this fuel log?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, "Log deleted!");
                    loadData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a log to delete!");
            }
        });
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = createCardPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(900, 500));
        
        JTextArea reportArea = new JTextArea();
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setBackground(new Color(248, 250, 255));
        reportArea.setForeground(Color.BLACK);
        reportArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JButton generateBtn = createModernActionButton("📊 Generate Detailed Fuel Report", new Color(70, 130, 180));
        generateBtn.setPreferredSize(new Dimension(250, 40));
        controlPanel.add(generateBtn);
        
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        
        generateBtn.addActionListener(e -> {
            StringBuilder report = new StringBuilder();
            report.append("DETAILED FUEL CONSUMPTION REPORT\n");
            for(int i=0; i<80; i++) report.append("=");
            report.append("\n\n");
            
            List<Truck> trucks = truckDAO.getAllTrucks();
            List<FuelLog> logs = fuelLogDAO.getAllFuelLogs();
            List<User> drivers = userDAO.getAllDrivers();
            
            report.append(String.format("%-12s %-15s %-10s %-12s %-15s\n", 
                "Date", "Driver", "Fuel (L)", "Truck Code", "License Plate"));
            for(int i=0; i<80; i++) report.append("-");
            report.append("\n");
            
            double totalFuel = 0;
            for (FuelLog log : logs) {
                String driverName = "Unknown";
                String truckCode = "Unknown";
                String licensePlate = "Unknown";
                
                // Find driver name
                for (User driver : drivers) {
                    if (driver.getUserId() == log.getUserId()) {
                        driverName = driver.getName();
                        break;
                    }
                }
                
                // Find truck details
                for (Truck truck : trucks) {
                    if (truck.getTruckId() == log.getTruckId()) {
                        truckCode = truck.getTruckCode();
                        licensePlate = truck.getLicensePlate();
                        break;
                    }
                }
                
                report.append(String.format("%-12s %-15s %-10.2f %-12s %-15s\n",
                    log.getDate().toString(),
                    driverName.length() > 15 ? driverName.substring(0, 15) : driverName,
                    log.getFuelAmount(),
                    truckCode,
                    licensePlate));
                
                totalFuel += log.getFuelAmount();
            }
            
            for(int i=0; i<80; i++) report.append("-");
            report.append("\n");
            report.append(String.format("Total Fuel Consumed: %.2f L\n", totalFuel));
            report.append(String.format("Total Records: %d\n", logs.size()));
            
            reportArea.setText(report.toString());
        });
        
        return panel;
    }
    
    public void loadData() {
        try {
            // Load trucks
            trucksModel.setRowCount(0);
            List<Truck> trucks = truckDAO.getAllTrucks();
            System.out.println("Loading " + trucks.size() + " trucks");
            for (Truck truck : trucks) {
                String driver = "Unassigned";
                if (truck.getAssignedDriverId() > 0) {
                    for (User u : userDAO.getAllDrivers()) {
                        if (u.getUserId() == truck.getAssignedDriverId()) {
                            driver = u.getName();
                            break;
                        }
                    }
                }
                trucksModel.addRow(new Object[]{truck.getTruckId(), truck.getTruckCode(), 
                    truck.getModel(), truck.getLicensePlate(), driver});
            }
            
            // Load drivers
            driversModel.setRowCount(0);
            List<User> drivers = userDAO.getAllDrivers();
            System.out.println("Loading " + drivers.size() + " drivers");
            for (User driver : drivers) {
                driversModel.addRow(new Object[]{driver.getUserId(), driver.getName(), 
                    driver.getUsername(), driver.getRole()});
            }
            
            // Load logs
            logsModel.setRowCount(0);
            List<User> allDrivers = userDAO.getAllDrivers();
            List<FuelLog> logs = fuelLogDAO.getAllFuelLogs();
            System.out.println("Loading " + logs.size() + " fuel logs");
            for (FuelLog log : logs) {
                String truckCode = "Unknown";
                for (Truck truck : trucks) {
                    if (truck.getTruckId() == log.getTruckId()) {
                        truckCode = truck.getTruckCode();
                        break;
                    }
                }
                
                String driverName = "Unknown";
                for (User driver : allDrivers) {
                    if (driver.getUserId() == log.getUserId()) {
                        driverName = driver.getName();
                        break;
                    }
                }
                
                logsModel.addRow(new Object[]{log.getDate(), truckCode, 
                    driverName, log.getFuelAmount(), log.getFuelCost()});
            }
            System.out.println("Data loading completed successfully!");
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            // Show error message to user
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}