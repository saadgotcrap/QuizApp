package studentdashboard;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SavedQuestionsFrame extends JFrame {
    private JPanel mainPanel;
    private static Map<String, ArrayList<Question>> questionMap;

    public SavedQuestionsFrame() {
        setTitle("Saved Questions");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        questionMap = new HashMap<>();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        loadQuestions();

        JScrollPane scrollPane = new JScrollPane(mainPanel);

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(scrollPane, BorderLayout.CENTER);

        add(containerPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        displayQuestions();
    }

    private void loadQuestions() {
        try (BufferedReader reader = new BufferedReader(new FileReader("saved_questions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length < 7) continue;
                String subjectSet = data[0];
                String questionText = data[1];
                String option1 = data[2];
                String option2 = data[3];
                String option3 = data[4];
                String option4 = data[5];
                String answer = data[6];

                Question question = new Question(questionText, option1, option2, option3, option4, answer);

                questionMap.computeIfAbsent(subjectSet, k -> new ArrayList<>()).add(question);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading questions.");
        }
    }

    private void displayQuestions() {
        mainPanel.removeAll();
        for (String subjectSet : questionMap.keySet()) {
            JLabel subjectSetLabel = new JLabel("Subject Set: " + subjectSet);
            mainPanel.add(subjectSetLabel);

            ArrayList<Question> questions = questionMap.get(subjectSet);
            for (Question question : questions) {
                JPanel questionPanel = new JPanel();
                questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

                JLabel questionLabel = new JLabel("Question: " + question.getQuestion());
                questionLabel.setFont(new Font("Arial", Font.BOLD, 12));
                questionLabel.setForeground(Color.BLACK);

                questionPanel.add(questionLabel);

                JLabel option1Label = new JLabel("Option 1: " + question.getOption1());
                JLabel option2Label = new JLabel("Option 2: " + question.getOption2());
                JLabel option3Label = new JLabel("Option 3: " + question.getOption3());
                JLabel option4Label = new JLabel("Option 4: " + question.getOption4());
                JLabel answerLabel = new JLabel("Answer: " + question.getAnswer());

                questionPanel.add(option1Label);
                questionPanel.add(option2Label);
                questionPanel.add(option3Label);
                questionPanel.add(option4Label);
                questionPanel.add(answerLabel);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(e -> {
                    questions.remove(question);
                    displayQuestions();
                });
                questionPanel.add(deleteButton);

                mainPanel.add(questionPanel);
            }
        }
        revalidate();
        repaint();
    }

    public static Map<String, ArrayList<Question>> getQuestionMap() {
        return questionMap;
    }

    public static class Question {
        private String question;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private String answer;

        public Question(String question, String option1, String option2, String option3, String option4, String answer) {
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public String getAnswer() {
            return answer;
        }
    }

    public static void main(String[] args) {
        new SavedQuestionsFrame();
    }
}