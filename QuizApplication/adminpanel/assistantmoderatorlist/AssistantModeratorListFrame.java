package adminpanel.assistantmoderatorlist;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AssistantModeratorListFrame extends JFrame {
    private DefaultListModel<AssistantModeratorPanel> assistantModeratorListModel;

    public AssistantModeratorListFrame() {
        this.setTitle("Assistant Moderator List");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.assistantModeratorListModel = new DefaultListModel<>();
        final JList<AssistantModeratorPanel> moderatorList = new JList<>(this.assistantModeratorListModel);
        moderatorList.setCellRenderer(new AssistantModeratorCellRenderer());
        JScrollPane scrollPane = new JScrollPane(moderatorList);
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = moderatorList.getSelectedIndex();
                if (selectedIndex != -1) {
                    assistantModeratorListModel.remove(selectedIndex);
                    saveAssistantModeratorsToFile();
                }
            }
        });

        loadAssistantModeratorsFromFiles();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(removeButton, BorderLayout.SOUTH);
        this.add(panel);
        this.setLocationRelativeTo((Component) null);
        this.setVisible(true);
    }

    private void loadAssistantModeratorsFromFiles() {
        loadAssistantModeratorsFromFile("pendingAssistantModerators.txt");
        loadAssistantModeratorsFromFile("assistantModeratorsData.txt");
    }

    private void loadAssistantModeratorsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(";");
                if (details.length == 5) { // Including phone and password
                    AssistantModeratorPanel panel = new AssistantModeratorPanel(details[0], details[1], details[2], details[3], details[4]);
                    this.assistantModeratorListModel.addElement(panel);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading assistant moderators from " + fileName);
        }
    }

    private void saveAssistantModeratorsToFile() {
        // Implement saving logic if needed
    }

    public static void main(String[] args) {
        new AssistantModeratorListFrame();
    }
}
