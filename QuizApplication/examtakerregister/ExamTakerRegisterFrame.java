package examtakerregister;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExamTakerRegisterFrame extends JFrame {
    public ExamTakerRegisterFrame() {
        setTitle("Exam Taker Register");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField usernameField = new JTextField(15);
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
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            savePendingCredentials(name, email, username, password);
            saveExamTakerDetails(name, email, username);
            JOptionPane.showMessageDialog(this, "Registration pending approval.");
            dispose();
        });

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void savePendingCredentials(String name, String email, String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pendingExamTakerCredentials.txt", true))) {
            writer.write(name + ";" + email + ";" + username + ";" + password);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving credentials.");
        }
    }

    private void saveExamTakerDetails(String name, String email, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("examTakers.txt", true))) {
            writer.write(name + ";" + email + ";" + username);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving exam taker details.");
        }
    }

    public static void main(String[] args) {
        new ExamTakerRegisterFrame();
    }
}
