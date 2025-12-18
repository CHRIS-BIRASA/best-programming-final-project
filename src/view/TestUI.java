package view;

import javax.swing.*;
import java.awt.*;

public class TestUI extends JFrame {
    public TestUI() {
        setTitle("Text Visibility Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        
        JLabel label1 = new JLabel("Black Text");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel label2 = new JLabel("Red Text");
        label2.setForeground(Color.RED);
        label2.setFont(new Font("Arial", Font.BOLD, 16));
        
        panel.add(label1);
        panel.add(label2);
        add(panel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TestUI().setVisible(true);
            }
        });
    }
}