package adminlogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nameField;

    public AdminLoginFrame() {
        setTitle("Admin Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField.setPreferredSize(new Dimension(200, 30));
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField.setPreferredSize(new Dimension(200, 30));
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField.setPreferredSize(new Dimension(200, 30));
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateAdmin();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void authenticateAdmin() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (name.equals("1") && email.equals("1") && password.equals("1")) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            new adminlogin.AdminPanelFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid name, email, or password.");
        }
    }

    public static void main(String[] args) {
        new AdminLoginFrame();
    }
}