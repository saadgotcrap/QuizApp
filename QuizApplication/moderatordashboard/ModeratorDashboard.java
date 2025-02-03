package moderatordashboard;

import assistantmoderatorapproval.AssistantModeratorApprovalFrame;

import javax.swing.*;
import java.awt.*;

public class ModeratorDashboard extends JFrame {
    public ModeratorDashboard() {
        setTitle("Moderator Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton addQuestionButton = new JButton("Add Question");
        JButton savedQuestionsButton = new JButton("Saved Questions");
        JButton approveAssistantModeratorButton = new JButton("Assistant Moderator Approval");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(addQuestionButton, gbc);



        gbc.gridy = 1;
        panel.add(savedQuestionsButton, gbc);

        gbc.gridy = 2;
        panel.add(approveAssistantModeratorButton, gbc);

        add(panel);
        setVisible(true);

        addQuestionButton.addActionListener(e -> new addquestion.SubjectQuestionSetFrame());
        savedQuestionsButton.addActionListener(e -> new studentdashboard.SavedQuestionsFrame().setVisible(true));


        approveAssistantModeratorButton.addActionListener(e -> new AssistantModeratorApprovalFrame().setVisible(true));
    }

    private void cancelQuiz() {

    }

    public static void main(String[] args) {
        new ModeratorDashboard();
    }
}