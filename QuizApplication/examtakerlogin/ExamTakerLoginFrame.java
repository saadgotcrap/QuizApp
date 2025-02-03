package examtakerlogin;

import examtakerdashboard.ExamTakerDashboardFrame;
import studentdashboard.SavedQuestionsFrame;
import startquiz.StartQuizFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExamTakerLoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public ExamTakerLoginFrame() {
        setTitle("Exam Taker Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticate(email, password)) {
                Map<String, ArrayList<SavedQuestionsFrame.Question>> questionMap = loadQuestionsFromFile();
                new ExamTakerDashboardFrame(questionMap, new ExamTakerDashboardFrame.OnStartQuizListener() {
                    @Override
                    public void onStartQuiz(String subject, String set, int minutes, int seconds) {

                        String key = subject + "-" + set;
                        ArrayList<SavedQuestionsFrame.Question> questions = questionMap.get(key);
                        if (questions != null) {
                            new StartQuizFrame(email, subject, questions, key);
                        } else {
                            JOptionPane.showMessageDialog(null, "No questions available for the selected subject and set.");
                        }
                    }
                });
                dispose();
            } else {
                JOptionPane.showMessageDialog(ExamTakerLoginFrame.this, "Invalid email or password.");
            }
        }
    }

    private boolean authenticate(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("examTakerCredentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(";");
                if (credentials.length == 4 && credentials[1].equals(email) && credentials[3].equals(password)) {
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
        new ExamTakerLoginFrame();
    }
}