package studentdashboard;

import startquiz.StartQuizFrame;
import studentdashboard.SavedQuestionsFrame.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectQuizFrame extends JFrame {
    private JComboBox<String> subjectComboBox;
    private JComboBox<String> setComboBox;
    private Map<String, ArrayList<Question>> questionMap;
    private String studentEmail;

    public SelectQuizFrame(String studentEmail, Map<String, ArrayList<Question>> questionMap) {
        this.studentEmail = studentEmail;
        this.questionMap = questionMap;

        setTitle("Select Quiz");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel subjectLabel = new JLabel("Select Subject:");
        subjectComboBox = new JComboBox<>(getUniqueSubjects());
        subjectComboBox.addActionListener(new SubjectComboBoxListener());

        JLabel setLabel = new JLabel("Select Set:");
        setComboBox = new JComboBox<>(getUniqueSets());

        JButton startQuizButton = new JButton("Start Quiz");
        startQuizButton.addActionListener(new StartQuizButtonListener());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(subjectLabel, gbc);

        gbc.gridx = 1;
        panel.add(subjectComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(setLabel, gbc);

        gbc.gridx = 1;
        panel.add(setComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(startQuizButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String[] getUniqueSubjects() {
        return questionMap.keySet().stream()
                .map(key -> key.split("-")[0])
                .distinct()
                .toArray(String[]::new);
    }

    private String[] getUniqueSets() {
        return questionMap.keySet().stream()
                .map(key -> key.split("-")[1])
                .distinct()
                .toArray(String[]::new);
    }

    private class SubjectComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setComboBox.setModel(new DefaultComboBoxModel<>(getUniqueSets()));
        }
    }

    private class StartQuizButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedSubject = (String) subjectComboBox.getSelectedItem();
            String selectedSet = (String) setComboBox.getSelectedItem();

            String key = selectedSubject + "-" + selectedSet;
            ArrayList<Question> questions = questionMap.get(key);

            if (questions != null && !questions.isEmpty()) {
                new StartQuizFrame(studentEmail, selectedSubject, questions, key);
                dispose();
            } else {
                JOptionPane.showMessageDialog(SelectQuizFrame.this, "No questions available for the selected subject and set.");
            }
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<Question>> mockQuestionMap = new HashMap<>();
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Which is not oop principle?","encapsulation","inheritance","swing","abstraction","polymorphism"));
        mockQuestionMap.put("Java-A", questions);

        new SelectQuizFrame("student@example.com", mockQuestionMap);
    }
}