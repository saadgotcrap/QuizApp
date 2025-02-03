package assistantmoderatorregister;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AssistantModeratorRegisterFrame extends JFrame {
    public AssistantModeratorRegisterFrame() {
        this.setTitle("Assistant Moderator Register");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            } else {
                savePendingAssistantModerator(name, email, department, phone, password);
                JOptionPane.showMessageDialog(this, "Registration pending approval by Moderator.");
                this.dispose();
            }
        });

        this.add(panel);
        this.setLocationRelativeTo((Component) null);
        this.setVisible(true);
    }

    private void savePendingAssistantModerator(String name, String email, String department, String phone, String password) {
        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter("pendingAssistantModerators.txt", true));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter("assistantModeratorsData.txt", true))) {
            String data = name + ";" + email + ";" + department + ";" + phone + ";" + password;
            writer1.write(data);
            writer1.newLine();
            writer2.write(data);
            writer2.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving pending assistant moderator.");
        }
    }

    public static void main(String[] args) {
        new AssistantModeratorRegisterFrame();
    }
}
