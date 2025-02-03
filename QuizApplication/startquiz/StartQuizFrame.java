package startquiz;

import studentdashboard.SavedQuestionsFrame.Question;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartQuizFrame extends JFrame {
    private List<Question> questions;
    private List<String> studentAnswers;
    private int currentQuestionIndex = 0;
    private JLabel timerLabel;
    private int timeRemaining;
    private Timer timer;
    private JPanel questionPanel;
    private String studentEmail;
    private String subjectSet;

    public StartQuizFrame(String studentEmail, String subject, List<Question> questions, String subjectSet) {
        this.studentEmail = studentEmail;
        this.subjectSet = subjectSet;
        setTitle("Quiz - " + subject);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.questions = questions;
        this.studentAnswers = new ArrayList<>();
        this.timeRemaining = getTimeFromDataFile(subjectSet) * 60;
        timerLabel = new JLabel();
        updateTimerLabel();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(timerLabel, BorderLayout.NORTH);

        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        panel.add(questionPanel, BorderLayout.CENTER);

        add(panel);

        displayQuestion();

        setLocationRelativeTo(null);
        setVisible(true);

        startTimer();
    }

    private int getTimeFromDataFile(String subjectSet) {
        int time = 30;
        try (BufferedReader reader = new BufferedReader(new FileReader("time_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equals(subjectSet)) {
                    time = Integer.parseInt(parts[1]);
                    break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error reading time data. Using default time of 30 minutes.");
        }
        return time;
    }

    private void startTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeRemaining--;
                    updateTimerLabel();
                    if (timeRemaining <= 0) {
                        timer.cancel();
                        JOptionPane.showMessageDialog(StartQuizFrame.this, "Time's up! Calculating your score...");
                        calculateScore();
                        dispose();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    private void updateTimerLabel() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
    }

    private void displayQuestion() {
        questionPanel.removeAll();

        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);

            JLabel questionLabel = new JLabel("Question:" + question.getQuestion() + "  ");
            questionPanel.add(questionLabel);

            JRadioButton option1 = new JRadioButton(question.getOption1());
            JRadioButton option2 = new JRadioButton(question.getOption2());
            JRadioButton option3 = new JRadioButton(question.getOption3());
            JRadioButton option4 = new JRadioButton(question.getOption4());

            ButtonGroup optionsGroup = new ButtonGroup();
            optionsGroup.add(option1);
            optionsGroup.add(option2);
            optionsGroup.add(option3);
            optionsGroup.add(option4);

            questionPanel.add(option1);
            questionPanel.add(option2);
            questionPanel.add(option3);
            questionPanel.add(option4);

            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(e -> {
                String selectedAnswer = null;
                if (option1.isSelected()) selectedAnswer = option1.getText();
                else if (option2.isSelected()) selectedAnswer = option2.getText();
                else if (option3.isSelected()) selectedAnswer = option3.getText();
                else if (option4.isSelected()) selectedAnswer = option4.getText();

                if (selectedAnswer == null || selectedAnswer.isEmpty()) {
                    JOptionPane.showMessageDialog(StartQuizFrame.this, "Please select an answer before proceeding.");
                    return;
                }

                System.out.println("Selected Answer: " + selectedAnswer);

                studentAnswers.add(selectedAnswer);

                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    JOptionPane.showMessageDialog(StartQuizFrame.this, "Quiz completed! Calculating your score...");
                    calculateScore();
                }
            });

            questionPanel.add(nextButton);

            revalidate();
            repaint();
        }
    }

    private void saveScore(String studentEmail, int score) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentEmail + "_scores.txt", true))) {
            writer.write("Score: " + score + " out of " + questions.size() + " Date: " + dtf.format(now) + " Subject-Set: " + subjectSet + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving score.");
        }
    }

    private void calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String correctAnswer = question.getAnswer();
            String studentAnswer = studentAnswers.size() > i ? studentAnswers.get(i) : "";


            System.out.println("Question: " + question.getQuestion());
            System.out.println("Correct Answer (Option Number): " + correctAnswer);
            System.out.println("Student Answer (Text): " + studentAnswer);

            String selectedOptionNumber = "";
            if (studentAnswer.equals(question.getOption1())) selectedOptionNumber = "Option 1";
            else if (studentAnswer.equals(question.getOption2())) selectedOptionNumber = "Option 2";
            else if (studentAnswer.equals(question.getOption3())) selectedOptionNumber = "Option 3";
            else if (studentAnswer.equals(question.getOption4())) selectedOptionNumber = "Option 4";

            if (correctAnswer.equals(selectedOptionNumber)) {
                score++;
            } else {
                System.out.println("Answer is incorrect.");
            }
        }
        JOptionPane.showMessageDialog(this, "Your score is: " + score + " out of " + questions.size());
        saveScore(studentEmail, score);
    }

    public static void main(String[] args) {
        List<Question> exampleQuestions = List.of(
                new Question("Sample Question 1", "Option 1", "Option 2", "Option 3", "Option 4", "Option 1"),
                new Question("Sample Question 2", "Option A", "Option B", "Option C", "Option D", "Option A")
        );
        new StartQuizFrame("student@example.com", "Java", exampleQuestions, "Java-SetA");
    }
}