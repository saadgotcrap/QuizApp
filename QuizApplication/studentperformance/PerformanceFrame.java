package studentperformance;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PerformanceFrame extends JFrame {
    private String studentEmail;

    public PerformanceFrame(String studentEmail) {
        this.studentEmail = studentEmail;

        setTitle("My Performance");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(scoreArea);
        add(scrollPane, BorderLayout.CENTER);

        loadScores(scoreArea);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadScores(JTextArea scoreArea) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(studentEmail + "_scores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            scoreArea.setText("No scores found.");
            return;
        }

        scoreArea.setText(contentBuilder.toString());
        scoreArea.revalidate();
        scoreArea.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PerformanceFrame("student@example.com"));
    }
}