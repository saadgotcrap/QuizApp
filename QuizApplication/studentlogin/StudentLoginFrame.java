package studentlogin;

import studentdashboard.SavedQuestionsFrame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentLoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public StudentLoginFrame() {
        setTitle("Student Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        emailField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Log In");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (verifyCredentials(email, password)) {
                Map<String, ArrayList<SavedQuestionsFrame.Question>> questionMap = loadQuestionsFromFile();
                new StudentDashboardFrame(email, questionMap);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }
        });

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean verifyCredentials(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("studentCredentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(";");
                if (credentials[0].equals(email) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading credentials.");
        }
        return false;
    }

    private Map<String, ArrayList<SavedQuestionsFrame.Question>> loadQuestionsFromFile() {
        Map<String, ArrayList<SavedQuestionsFrame.Question>> questionMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("saved_questions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String subjectSet = data[0];
                String questionText = data[1];
                String option1 = data[2];
                String option2 = data[3];
                String option3 = data[4];
                String option4 = data[5];
                String answer = data[6];

                SavedQuestionsFrame.Question question = new SavedQuestionsFrame.Question(questionText, option1, option2, option3, option4, answer);

                questionMap.computeIfAbsent(subjectSet, k -> new ArrayList<>()).add(question);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading questions.");
        }
        return questionMap;
    }

    public static void main(String[] args) {
        new StudentLoginFrame();
    }
}