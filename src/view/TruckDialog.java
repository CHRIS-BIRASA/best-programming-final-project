package view;

import dao.TruckDAO;
import dao.UserDAO;
import model.Truck;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TruckDialog extends JDialog {
    private JTextField codeField, modelField, plateField;
    private JComboBox<String> driverCombo;
    private TruckDAO truckDAO;
    private UserDAO userDAO;
    private List<User> drivers;
    private int truckId = -1;
    
    public TruckDialog(JFrame parent) {
        this(parent, -1);
    }
    
    public TruckDialog(JFrame parent, int truckId) {
        super(parent, truckId == -1 ? "Add Truck" : "Update Truck", true);
        this.truckId = truckId;
        this.truckDAO = new TruckDAO();
        this.userDAO = new UserDAO();
        this.drivers = userDAO.getAllDrivers();
        
        setSize(300, 250);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        codeField = new JTextField(15);
        modelField = new JTextField(15);
        plateField = new JTextField(15);
        driverCombo = new JComboBox<>();
        
        driverCombo.addItem("Unassigned");
        for (User driver : drivers) {
            driverCombo.addItem(driver.getName() + " (" + driver.getUserId() + ")");
        }
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        panel.add(new JLabel("Code:"));
        panel.add(codeField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("License:"));
        panel.add(plateField);
        panel.add(new JLabel("Driver:"));
        panel.add(driverCombo);
        panel.add(saveBtn);
        panel.add(cancelBtn);
        
        add(panel);
        
        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());
        
        if (truckId != -1) {
            loadTruckData();
        }
    }
    
    private void loadTruckData() {
        Truck truck = truckDAO.getTruckById(truckId);
        if (truck != null) {
            codeField.setText(truck.getTruckCode());
            modelField.setText(truck.getModel());
            plateField.setText(truck.getLicensePlate());
            
            for (int i = 0; i < drivers.size(); i++) {
                if (drivers.get(i).getUserId() == truck.getAssignedDriverId()) {
                    driverCombo.setSelectedIndex(i + 1);
                    break;
                }
            }
        }
    }
    
    private void save() {
        String code = codeField.getText().trim();
        String model = modelField.getText().trim();
        String plate = plateField.getText().trim();
        
        if (code.isEmpty() || model.isEmpty() || plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }
        
        int driverId = 0;
        int selectedIndex = driverCombo.getSelectedIndex();
        if (selectedIndex > 0) {
            driverId = drivers.get(selectedIndex - 1).getUserId();
        }
        
        Truck truck = new Truck(code, model, plate);
        truck.setAssignedDriverId(driverId);
        
        boolean success;
        String message;
        
        if (truckId == -1) {
            success = truckDAO.addTruck(truck);
            message = success ? "Truck added successfully!" : "Failed to add truck!";
        } else {
            truck.setTruckId(truckId);
            success = truckDAO.updateTruck(truck);
            message = success ? "Truck updated successfully!" : "Failed to update truck!";
        }
        
        if (success) {
            JOptionPane.showMessageDialog(this, message);
            if (getParent() instanceof AdminDashboard) {
                ((AdminDashboard) getParent()).loadData();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, message);
        }
    }
}