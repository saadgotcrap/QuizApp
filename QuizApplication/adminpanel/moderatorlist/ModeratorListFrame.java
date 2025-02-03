package adminpanel.moderatorlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ModeratorListFrame extends JFrame {
    private static final String MODERATOR_FILE = "moderators.txt";

    public ModeratorListFrame() {
        setTitle("Moderator List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Name", "Email", "Department", "Phone"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        loadModerators(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadModerators(DefaultTableModel tableModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MODERATOR_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                tableModel.addRow(new Object[]{data[0], data[1], data[2], data[3]});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading moderators.");
        }
    }

    public static void main(String[] args) {
        new ModeratorListFrame();
    }
}
