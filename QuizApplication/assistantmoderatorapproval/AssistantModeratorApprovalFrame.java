package assistantmoderatorapproval;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AssistantModeratorApprovalFrame extends JFrame {
    private static final Map<String, Boolean> assistantApprovalDatabase = new HashMap<>();

    public AssistantModeratorApprovalFrame() {
        setTitle("Assistant Moderator Approval");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel headingLabel = new JLabel("Assistant Moderator Approval List");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(headingLabel, gbc);

        loadPendingAssistantModerators(panel, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadPendingAssistantModerators(JPanel panel, GridBagConstraints gbc) {
        try (BufferedReader reader = new BufferedReader(new FileReader("pendingAssistantModerators.txt"))) {
            String line;
            int y = 1;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String name = data[0];
                String email = data[1];
                String department = data[2];
                String phone = data[3];
                String password = data[4];

                JLabel nameLabel = new JLabel("Name: " + name);
                JLabel emailLabel = new JLabel("Email: " + email);
                JLabel departmentLabel = new JLabel("Department: " + department);
                JLabel phoneLabel = new JLabel("Phone: " + phone);
                JButton approveButton = new JButton("Approve");
                JButton deleteButton = new JButton("Delete");

                gbc.gridx = 0;
                gbc.gridy = y;
                gbc.gridwidth = 1;
                panel.add(nameLabel, gbc);

                gbc.gridx = 1;
                panel.add(emailLabel, gbc);

                y++;
                gbc.gridx = 0;
                gbc.gridy = y;
                panel.add(departmentLabel, gbc);

                gbc.gridx = 1;
                panel.add(phoneLabel, gbc);

                y++;
                gbc.gridx = 0;
                gbc.gridy = y;
                panel.add(approveButton, gbc);

                gbc.gridx = 1;
                panel.add(deleteButton, gbc);

                y++;

                approveButton.addActionListener(e -> {
                    assistantApprovalDatabase.put(email, true);
                    saveApprovedAssistantModerator(name, email, department, phone, password);
                    removePendingAssistantModerator(email);
                    JOptionPane.showMessageDialog(this, "Assistant Moderator approved!");
                    refreshPanel(panel);
                });

                deleteButton.addActionListener(e -> {
                    removePendingAssistantModerator(email);
                    JOptionPane.showMessageDialog(this, "Assistant Moderator deleted!");
                    refreshPanel(panel);
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading pending assistant moderators.");
        }
    }

    private void saveApprovedAssistantModerator(String name, String email, String department, String phone, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("approvedAssistantModerators.txt", true))) {
            writer.write(name + ";" + email + ";" + department + ";" + phone + ";" + password);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving approved assistant moderator.");
        }
    }

    private void removePendingAssistantModerator(String email) {
        try {
            File inputFile = new File("pendingAssistantModerators.txt");
            File tempFile = new File("pendingAssistantModeratorsTemp.txt");

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.contains(email)) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }

            if (!inputFile.delete()) {
                JOptionPane.showMessageDialog(this, "Error deleting pending assistant moderator.");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                JOptionPane.showMessageDialog(this, "Error renaming temp file.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error processing pending assistant moderator file.");
        }
    }

    private void refreshPanel(JPanel panel) {
        panel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        loadPendingAssistantModerators(panel, gbc);
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        new AssistantModeratorApprovalFrame();
    }

    public static Map<String, Boolean> getAssistantApprovalDatabase() {
        return assistantApprovalDatabase;
    }
}