package adminpanel.examtakerlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExamTakerListFrame extends JFrame {
    private static final String EXAM_TAKER_FILE = "examTakers.txt";

    public ExamTakerListFrame() {
        setTitle("Exam Taker List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Name", "Email", "Username"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        loadExamTakers(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadExamTakers(DefaultTableModel tableModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(EXAM_TAKER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                tableModel.addRow(new Object[]{data[0], data[1], data[2]});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading exam takers.");
        }
    }

    public static void main(String[] args) {
        new ExamTakerListFrame();
    }
}
