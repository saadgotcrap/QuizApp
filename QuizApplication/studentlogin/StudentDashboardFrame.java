package studentlogin;

import studentdashboard.SelectQuizFrame;
import studentdashboard.SavedQuestionsFrame;
import studentperformance.PerformanceFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class StudentDashboardFrame extends JFrame {
    private String studentEmail;
    private Map<String, ArrayList<SavedQuestionsFrame.Question>> questionMap;

    public StudentDashboardFrame(String studentEmail, Map<String, ArrayList<SavedQuestionsFrame.Question>> questionMap) {
        this.studentEmail = studentEmail;
        this.questionMap = questionMap;

        setTitle("Student Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton startQuizButton = new JButton("Start Quiz");
        startQuizButton.setPreferredSize(new Dimension(150, 30));
        startQuizButton.addActionListener(e -> new SelectQuizFrame(studentEmail, questionMap));

        JButton myPerformanceButton = new JButton("My Performance");
        myPerformanceButton.setPreferredSize(new Dimension(150, 30));
        myPerformanceButton.addActionListener(e -> new PerformanceFrame(studentEmail));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(startQuizButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(myPerformanceButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Map<String, ArrayList<SavedQuestionsFrame.Question>> mockQuestionMap = null;
        new StudentDashboardFrame("student@example.com", mockQuestionMap);
    }
}