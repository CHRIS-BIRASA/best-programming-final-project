package view;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private UserDAO userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();
        setTitle("🚛 Cargo Tracker - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 320);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove default window decorations
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        
        // Create gradient background panel
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180), 
                    0, getHeight(), new Color(135, 206, 235));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        
        // Title panel with logo effect
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("🚛 CARGO TRACKER", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titlePanel.add(titleLabel);
        
        // Main form panel with rounded corners effect
        JPanel panel = new JPanel(new GridLayout(4, 2, 15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setOpaque(false);
        
        usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(180, 30));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(180, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        roleCombo = new JComboBox<>(new String[]{"Driver", "Admin", "Manager"});
        roleCombo.setPreferredSize(new Dimension(180, 30));
        roleCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        roleCombo.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        
        // Modern styled buttons with hover effects
        JButton loginBtn = createModernButton("✓ LOGIN", new Color(70, 130, 180));
        JButton registerBtn = createModernButton("👤 REGISTER", new Color(100, 149, 237));
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(25, 25, 112));
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(new Color(25, 25, 112));
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(new Color(25, 25, 112));
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleCombo);
        panel.add(loginBtn);
        panel.add(registerBtn);
        
        // Add shadow effect panel
        JPanel shadowPanel = new JPanel(new BorderLayout());
        shadowPanel.setOpaque(false);
        shadowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        shadowPanel.add(panel, BorderLayout.CENTER);
        
        gradientPanel.add(titlePanel, BorderLayout.NORTH);
        gradientPanel.add(shadowPanel, BorderLayout.CENTER);
        add(gradientPanel);
        
        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> new RegisterDialog((JFrame)null).setVisible(true));
    }
    
    private JButton createModernButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(baseColor.brighter());
                } else {
                    g2d.setColor(baseColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Add subtle shadow
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRoundRect(2, 2, getWidth()-2, getHeight()-2, 15, 15);
                
                // Draw text
                g2d.setColor(getForeground());
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String selectedRole = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        User user = userDAO.authenticate(username, password);
        if (user != null) {
            // Check if user's actual role matches selected role
            if (!user.getRole().equals(selectedRole)) {
                JOptionPane.showMessageDialog(this, "Access denied! You cannot login as " + selectedRole + ". Your role is " + user.getRole());
                return;
            }
            
            if (user.isDriver()) {
                new DriverDashboard(user).setVisible(true);
            } else if (user.isAdmin()) {
                new AdminDashboard(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Access denied for role: " + user.getRole());
                return;
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}