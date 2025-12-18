package view;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {
    private JTextField nameField, usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private UserDAO userDAO;
    private AdminDashboard adminParent;
    
    public RegisterDialog(JFrame parent) {
        super(parent, "Register User", true);
        this.adminParent = (parent instanceof AdminDashboard) ? (AdminDashboard) parent : null;
        userDAO = new UserDAO();
        initComponents();
    }
    
    public RegisterDialog(AdminDashboard parent) {
        super(parent, "Register User", true);
        this.adminParent = parent;
        userDAO = new UserDAO();
        initComponents();
    }
    
    private void initComponents() {
        setSize(350, 300);
        setLocationRelativeTo(getParent());
        getContentPane().setBackground(new Color(230, 240, 250));
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panel.setBackground(Color.WHITE);
        
        nameField = new JTextField(15);
        nameField.setPreferredSize(new Dimension(180, 30));
        nameField.setFont(new Font("Arial", Font.PLAIN, 13));
        usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(180, 30));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 13));
        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(180, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
        roleCombo = new JComboBox<>(new String[]{"Driver", "Manager", "Admin"});
        roleCombo.setPreferredSize(new Dimension(180, 30));
        roleCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(70, 130, 180));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 12));
        registerBtn.setPreferredSize(new Dimension(90, 35));
        registerBtn.setFocusPainted(false);
        registerBtn.setOpaque(true);
        registerBtn.setBorderPainted(false);
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(100, 149, 237));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
        cancelBtn.setPreferredSize(new Dimension(90, 35));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setOpaque(true);
        cancelBtn.setBorderPainted(false);
        
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(new Color(25, 25, 112));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(25, 25, 112));
        userLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(new Color(25, 25, 112));
        passLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(new Color(25, 25, 112));
        roleLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleCombo);
        panel.add(registerBtn);
        panel.add(cancelBtn);
        
        add(panel);
        
        registerBtn.addActionListener(e -> register());
        cancelBtn.addActionListener(e -> dispose());
    }
    
    private void register() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleCombo.getSelectedItem();
        
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }
        
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters!");
            return;
        }
        
        User user = new User(name, role, username, password);
        if (userDAO.addUser(user)) {
            JOptionPane.showMessageDialog(this, "User registered successfully!");
            if (adminParent != null) {
                adminParent.loadData();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed!");
        }
    }
}