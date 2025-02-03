package moderatorregister;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModeratorRegister extends JFrame {
    private static final Map<String, Map<String, String>> usersDatabase = new HashMap<>();

    public ModeratorRegister() {
        setTitle("Moderator Register");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField departmentField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JPasswordField confirmPasswordField = new JPasswordField(15);
        JButton registerButton = new JButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Department:"), gbc);

        gbc.gridx = 1;
        panel.add(departmentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String department = departmentField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("name", name);
            userInfo.put("department", department);
            userInfo.put("phone", phone);
            userInfo.put("password", password);

            usersDatabase.put(email, userInfo);
            savePendingModerator(name, email, department, phone, password);
            saveRegisteredModerator(name, email, department, phone, password);
            JOptionPane.showMessageDialog(this, "Registration pending approval.");
            dispose();
        });

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void savePendingModerator(String name, String email, String department, String phone, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pendingModerators.txt", true))) {
            writer.write(name + ";" + email + ";" + department + ";" + phone + ";" + password);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving pending moderator.");
        }
    }

    private void saveRegisteredModerator(String name, String email, String department, String phone, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("moderators.txt", true))) {
            writer.write(name + ";" + email + ";" + department + ";" + phone + ";" + password);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving registered moderator.");
        }
    }

    public static Map<String, Map<String, String>> getUsersDatabase() {
        return usersDatabase;
    }

    public static void main(String[] args) {
        new ModeratorRegister();
    }
}
