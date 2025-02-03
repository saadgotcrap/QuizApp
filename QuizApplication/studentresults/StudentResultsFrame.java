package studentresults;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class StudentResultsFrame extends JFrame {
    private static final String SCORES_DIRECTORY = ".";

    public StudentResultsFrame() {
        setTitle("Student Results");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Email", "Score", "Date", "Subject-Set"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        loadScores(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadScores(DefaultTableModel tableModel) {
        File dir = new File(SCORES_DIRECTORY);
        File[] files = dir.listFiles((d, name) -> name.endsWith("_scores.txt"));
        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" ");
                        if (parts.length >= 8) {
                            String email = file.getName().replace("_scores.txt", "");
                            String score = parts[1];
                            String date = parts[3] + " " + parts[4];
                            String subjectSet = parts[7];
                            tableModel.addRow(new Object[]{email, score, date, subjectSet});
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error loading scores from " + file.getName());
                }
            }
        }
    }

    public static void main(String[] args) {
        new StudentResultsFrame();
    }
}
