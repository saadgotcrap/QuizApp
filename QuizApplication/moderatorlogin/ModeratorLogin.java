package moderatorlogin;

import moderatordashboard.ModeratorDashboard;
import adminpanel.moderatorapproval.ModeratorApprovalFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ModeratorLogin extends JFrame {

    public ModeratorLogin() {
        setTitle("Moderator Login");
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
                if (isModeratorApproved(email)) {
                    if (isModeratorActive(email)) {
                        JOptionPane.showMessageDialog(this, "Login successful!");
                        new ModeratorDashboard();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Your account has been removed by the admin.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Your account is not approved by the admin.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean verifyCredentials(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("approvedModerators.txt"))) {
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
    private boolean isModeratorApproved(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("approvedModerators.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(";");
                if (credentials[1].equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading approved moderators.");
        }
        return false;
    }

    private boolean isModeratorActive(String email) {
        return ModeratorApprovalFrame.getActiveDatabase().getOrDefault(email, true);
    }

    public static void main(String[] args) {
        new ModeratorLogin();
    }
}