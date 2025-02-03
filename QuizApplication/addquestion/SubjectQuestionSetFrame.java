package addquestion;

import javax.swing.*;
import java.awt.*;

public class SubjectQuestionSetFrame extends JFrame {
    private JTextField subjectField;
    private JTextField questionSetField;

    public SubjectQuestionSetFrame() {
        setTitle("Subject and Question Set");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        subjectField = new JTextField();
        questionSetField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Subject Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        subjectField.setPreferredSize(new Dimension(200, 30));
        panel.add(subjectField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Set Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        questionSetField.setPreferredSize(new Dimension(200, 30));
        panel.add(questionSetField, gbc);

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(150, 30));
        nextButton.addActionListener(e -> {
            String subject = subjectField.getText();
            String questionSet = questionSetField.getText();
            new AddQuestionFrame(subject, questionSet);
            dispose();
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(nextButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SubjectQuestionSetFrame();
    }
}