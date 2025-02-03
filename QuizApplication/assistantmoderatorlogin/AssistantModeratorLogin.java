package assistantmoderatorlogin;

import assistantmoderatordashboard.AssistantModeratorDashboardFrame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AssistantModeratorLogin extends JFrame {

    public AssistantModeratorLogin() {
        setTitle("Assistant Moderator Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(20);
        JButton loginButton = new JButton("Log In");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        add(panel);

        loginButton.addActionListener(e -> {
            String email = emailText.getText();
            String password = new String(passwordText.getPassword());

            if (verifyCredentials(email, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new AssistantModeratorDashboardFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean verifyCredentials(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("approvedAssistantModerators.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(";");
                if (credentials[1].equals(email) && credentials[4].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading credentials.");
        }
        return false;
    }

    public static void main(String[] args) {
        new AssistantModeratorLogin();
    }
}