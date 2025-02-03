package examtakerdashboard;

import studentdashboard.SavedQuestionsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class ExamTakerDashboardFrame extends JFrame {
    private JPanel mainPanel;
    private Map<String, ArrayList<SavedQuestionsFrame.Question>> questionMap;
    private OnStartQuizListener listener;

    public ExamTakerDashboardFrame(Map<String, ArrayList<SavedQuestionsFrame.Question>> questionMap, OnStartQuizListener listener) {
        this.questionMap = questionMap;
        this.listener = listener;

        setTitle("Exam Taker Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        loadQuestions();

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadQuestions() {
        mainPanel.removeAll();
        for (Map.Entry<String, ArrayList<SavedQuestionsFrame.Question>> entry : questionMap.entrySet()) {
            String subjectAndSet = entry.getKey();
            JLabel subjectLabel = new JLabel("Subject and Set: " + subjectAndSet);
            subjectLabel.setFont(new Font("Arial", Font.BOLD, 16));
            mainPanel.add(subjectLabel);

            for (SavedQuestionsFrame.Question question : entry.getValue()) {
                JPanel questionPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel questionLabel = new JLabel("Question: " + question.getQuestion());
                gbc.gridx = 0;
                gbc.gridy = 0;
                questionPanel.add(questionLabel, gbc);

                JLabel option1Label = new JLabel("Option 1: " + question.getOption1());
                gbc.gridy = 1;
                questionPanel.add(option1Label, gbc);

                JLabel option2Label = new JLabel("Option 2: " + question.getOption2());
                gbc.gridy = 2;
                questionPanel.add(option2Label, gbc);

                JLabel option3Label = new JLabel("Option 3: " + question.getOption3());
                gbc.gridy = 3;
                questionPanel.add(option3Label, gbc);

                JLabel option4Label = new JLabel("Option 4: " + question.getOption4());
                gbc.gridy = 4;
                questionPanel.add(option4Label, gbc);

                JLabel answerLabel = new JLabel("Answer: " + question.getAnswer());
                gbc.gridy = 5;
                questionPanel.add(answerLabel, gbc);

                JLabel timeLabel = new JLabel("Set Time (Minutes):");
                JTextField timeField = new JTextField(5);
                gbc.gridy = 6;
                questionPanel.add(timeLabel, gbc);

                gbc.gridx = 1;
                questionPanel.add(timeField, gbc);

                JButton deleteButton = new JButton("Delete");
                gbc.gridx = 2;
                deleteButton.addActionListener(e -> {
                    entry.getValue().remove(question);
                    saveQuestionsToFile();
                    loadQuestions();
                    JOptionPane.showMessageDialog(this, "Question deleted successfully.");
                });
                questionPanel.add(deleteButton, gbc);

                JButton saveButton = new JButton("Save");
                gbc.gridx = 3;
                saveButton.addActionListener(e -> {
                    String timeInput = timeField.getText();
                    try {
                        int time = Integer.parseInt(timeInput);
                        saveTimeToFile(subjectAndSet, time);
                        JOptionPane.showMessageDialog(this, "Time saved successfully.");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Please enter a valid number for time.");
                    }
                });
                questionPanel.add(saveButton, gbc);

                mainPanel.add(questionPanel);
                mainPanel.add(new JSeparator(JSeparator.HORIZONTAL));
            }
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void saveQuestionsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("saved_questions.txt"))) {
            for (Map.Entry<String, ArrayList<SavedQuestionsFrame.Question>> entry : questionMap.entrySet()) {
                String subjectAndSet = entry.getKey();
                for (SavedQuestionsFrame.Question question : entry.getValue()) {
                    writer.println(subjectAndSet + ";" + question.getQuestion() + ";" + question.getOption1() + ";" + question.getOption2() + ";" + question.getOption3() + ";" + question.getOption4() + ";" + question.getAnswer());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving questions to file.");
        }
    }

    private void saveTimeToFile(String subjectSet, int time) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("time_data.txt", true))) {
            writer.println(subjectSet + "=" + time);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving time to file.");
        }
    }

    public interface OnStartQuizListener {
        void onStartQuiz(String subject, String set, int minutes, int seconds);
    }

    public static void main(String[] args) {
        SavedQuestionsFrame savedQuestionsFrame = new SavedQuestionsFrame();
        new ExamTakerDashboardFrame(savedQuestionsFrame.getQuestionMap(), (subject, set, minutes, seconds) -> {

            System.out.println("Quiz starting for subject: " + subject + ", set: " + set + ", duration: " + minutes + " minutes.");
        });
    }
}