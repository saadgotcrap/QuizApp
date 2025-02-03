package addquestion;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddQuestionFrame extends JFrame {
    private JTextField questionField;
    private JTextField[] optionFields;
    private JComboBox<String> answerComboBox;
    private String subject;
    private String questionSet;

    public AddQuestionFrame(String subject, String questionSet) {
        this.subject = subject;
        this.questionSet = questionSet;
        setTitle("Add Question");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        questionField = new JTextField();
        optionFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            optionFields[i] = new JTextField();
        }

        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        answerComboBox = new JComboBox<>(options);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Question:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        questionField.setPreferredSize(new Dimension(300, 30));
        panel.add(questionField, gbc);

        for (int i = 0; i < 4; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            panel.add(new JLabel("Option " + (i + 1) + ":"), gbc);

            gbc.gridx = 1;
            gbc.gridwidth = 2;
            optionFields[i].setPreferredSize(new Dimension(300, 30));
            panel.add(optionFields[i], gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Answer:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        answerComboBox.setPreferredSize(new Dimension(300, 30));
        panel.add(answerComboBox, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(150, 30));
        saveButton.addActionListener(e -> saveQuestion());

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveQuestion() {
        String question = questionField.getText();
        String[] options = new String[4];
        for (int i = 0; i < 4; i++) {
            options[i] = optionFields[i].getText();
        }
        String answer = (String) answerComboBox.getSelectedItem();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("saved_questions.txt", true))) {
            String questionLine = subject + "-" + questionSet + ";" + question + ";" + options[0] + ";" + options[1] + ";" + options[2] + ";" + options[3] + ";" + answer;
            writer.write(questionLine);
            writer.newLine();
            System.out.println("Saved Question: " + questionLine);
            JOptionPane.showMessageDialog(this, "Question saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving question.");
        }
    }
}