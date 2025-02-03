package assistantmoderatordashboard;

import studentresults.StudentResultsFrame;
import studentdashboard.SavedQuestionsFrame;
import examtakerapproval.ExamTakerApprovalFrame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AssistantModeratorDashboardFrame extends JFrame {
    public AssistantModeratorDashboardFrame() {
        setTitle("Assistant Moderator Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton questionModerationButton = new JButton("Question Moderation");
        questionModerationButton.setPreferredSize(new Dimension(200, 30));
        questionModerationButton.addActionListener(e -> new SavedQuestionsFrame());

        JButton cancelQuizButton = new JButton("Cancel Quiz");
        cancelQuizButton.setPreferredSize(new Dimension(200, 30));
        cancelQuizButton.addActionListener(e -> cancelQuiz());

        JButton examTakerApprovalButton = new JButton("Exam Taker Approval");
        examTakerApprovalButton.setPreferredSize(new Dimension(200, 30));
        examTakerApprovalButton.addActionListener(e -> new ExamTakerApprovalFrame());

        JButton studentResultsButton = new JButton("Student Results");
        studentResultsButton.setPreferredSize(new Dimension(200, 30));
        studentResultsButton.addActionListener(e -> new StudentResultsFrame());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(studentResultsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(questionModerationButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(examTakerApprovalButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(cancelQuizButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cancelQuiz() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("questions.txt"))) {
            writer.write("");
            JOptionPane.showMessageDialog(this, "All questions deleted successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting questions.");
        }


        resetTimer();
    }

    private void resetTimer() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("timer.txt"))) {
            writer.write("0");
            JOptionPane.showMessageDialog(this, "Timer reset successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error resetting timer.");
        }
    }

    public static void main(String[] args) {
        new AssistantModeratorDashboardFrame();
    }
}