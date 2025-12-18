package view;

import dao.FuelLogDAO;
import dao.TruckDAO;
import model.FuelLog;
import model.Truck;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class DriverDashboard extends JFrame {
    private User currentUser;
    private TruckDAO truckDAO;
    private FuelLogDAO fuelLogDAO;
    private JComboBox<Truck> truckCombo;
    private JTextField fuelField, priceField, mileageField;
    private DefaultTableModel tableModel;
    
    public DriverDashboard(User user) {
        this.currentUser = user;
        this.truckDAO = new TruckDAO();
        this.fuelLogDAO = new FuelLogDAO();
        
        setTitle("Driver Dashboard - " + user.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 240, 250));
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Enter Fuel Log Details", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(70, 130, 180)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        inputPanel.setBackground(Color.WHITE);
        
        truckCombo = new JComboBox<>();
        truckCombo.setPreferredSize(new Dimension(200, 30));
        truckCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        fuelField = new JTextField(10);
        fuelField.setPreferredSize(new Dimension(200, 30));
        fuelField.setFont(new Font("Arial", Font.PLAIN, 13));
        priceField = new JTextField(10);
        priceField.setPreferredSize(new Dimension(200, 30));
        priceField.setFont(new Font("Arial", Font.PLAIN, 13));

        
        JLabel truckLabel = new JLabel("Select Truck:");
        truckLabel.setForeground(new Color(25, 25, 112));
        truckLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel fuelLabel = new JLabel("Fuel Amount (Liters):");
        fuelLabel.setForeground(new Color(25, 25, 112));
        fuelLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel priceLabel = new JLabel("Price per Liter:");
        priceLabel.setForeground(new Color(25, 25, 112));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 13));

        
        inputPanel.add(truckLabel);
        inputPanel.add(truckCombo);
        inputPanel.add(fuelLabel);
        inputPanel.add(fuelField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(new Color(230, 240, 250));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton saveBtn = new JButton("SAVE FUEL LOG");
        saveBtn.setBackground(new Color(70, 130, 180));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setPreferredSize(new Dimension(160, 35));
        saveBtn.setFocusPainted(false);
        saveBtn.setOpaque(true);
        saveBtn.setBorderPainted(false);
        
        JButton clearBtn = new JButton("Clear");
        clearBtn.setBackground(new Color(100, 149, 237));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 12));
        clearBtn.setPreferredSize(new Dimension(80, 35));
        clearBtn.setFocusPainted(false);
        clearBtn.setOpaque(true);
        clearBtn.setBorderPainted(false);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(65, 105, 225));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setOpaque(true);
        logoutBtn.setBorderPainted(false);
        
        btnPanel.add(saveBtn);
        btnPanel.add(clearBtn);
        btnPanel.add(logoutBtn);
        
        // Table
        String[] columns = {"Date", "Truck", "Fuel (L)", "Price/L", "Total Cost"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(173, 216, 230));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        saveBtn.addActionListener(e -> addFuelLog());
        clearBtn.addActionListener(e -> {
            fuelField.setText("");
            priceField.setText("");
            if (truckCombo.getItemCount() > 0) {
                truckCombo.setSelectedIndex(0);
            }
        });
        logoutBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        
        loadTrucks();
        loadLogs();
    }
    
    private void loadTrucks() {
        truckCombo.removeAllItems();
        List<Truck> trucks = truckDAO.getAllTrucks();
        for (Truck truck : trucks) {
            truckCombo.addItem(truck);
        }
        if (trucks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No trucks available. Contact admin.");
        }
    }
    
    private void loadLogs() {
        tableModel.setRowCount(0);
        List<FuelLog> logs = fuelLogDAO.getFuelLogsByDriver(currentUser.getUserId());
        for (FuelLog log : logs) {
            List<Truck> trucks = truckDAO.getAllTrucks();
            String truckCode = "Unknown";
            for (Truck truck : trucks) {
                if (truck.getTruckId() == log.getTruckId()) {
                    truckCode = truck.getTruckCode();
                    break;
                }
            }
            tableModel.addRow(new Object[]{
                log.getDate(), truckCode, log.getFuelAmount(),
                log.getFuelPrice(), log.getFuelCost()
            });
        }
    }
    
    private void addFuelLog() {
        try {
            if (truckCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No trucks assigned to you!");
                return;
            }
            
            double fuel = Double.parseDouble(fuelField.getText());
            double price = Double.parseDouble(priceField.getText());
            
            if (fuel <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(this, "All values must be positive!");
                return;
            }
            
            Truck truck = (Truck) truckCombo.getSelectedItem();
            FuelLog log = new FuelLog(truck.getTruckId(), currentUser.getUserId(), 
                LocalDate.now(), fuel, price, 0.0);
            
            if (fuelLogDAO.addFuelLog(log)) {
                JOptionPane.showMessageDialog(this, "Fuel log added successfully!");
                fuelField.setText("");
                priceField.setText("");
                loadLogs();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add fuel log!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!");
        }
    }
}