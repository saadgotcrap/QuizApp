package adminlogin;

import adminpanel.assistantmoderatorlist.AssistantModeratorListFrame;
import adminpanel.studentlist.StudentListFrame;
import adminpanel.moderatorapproval.ModeratorApprovalFrame;
import studentresults.StudentResultsFrame;
import adminpanel.moderatorlist.ModeratorListFrame;
import adminpanel.examtakerlist.ExamTakerListFrame;

import javax.swing.*;
import java.awt.*;

public class AdminPanelFrame extends JFrame {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 700;  // Adjusted height to accommodate buttons
    private static final Dimension BUTTON_SIZE = new Dimension(200, 30);

    public AdminPanelFrame() {
        setTitle("Admin Panel");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Welcome to the Admin Panel", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(label, gbc);

        JButton assistantModeratorListButton = createButton("Assistant Moderator List");
        assistantModeratorListButton.setToolTipText("View the list of Assistant Moderators");
        assistantModeratorListButton.addActionListener(e -> new AssistantModeratorListFrame());

        JButton studentListButton = createButton("Student List");
        studentListButton.setToolTipText("View the list of Students");
        studentListButton.addActionListener(e -> new StudentListFrame());

        JButton moderatorApprovalButton = createButton("Moderator Approval");
        moderatorApprovalButton.setToolTipText("Approve or reject Moderator requests");
        moderatorApprovalButton.addActionListener(e -> new ModeratorApprovalFrame());

        JButton studentScorecardButton = createButton("Student Scorecard");
        studentScorecardButton.setToolTipText("View student scorecards");
        studentScorecardButton.addActionListener(e -> new StudentResultsFrame());

        JButton moderatorListButton = createButton("Moderator List");
        moderatorListButton.setToolTipText("View the list of Moderators");
        moderatorListButton.addActionListener(e -> new ModeratorListFrame());

        JButton examTakerListButton = createButton("Exam Taker List");
        examTakerListButton.setToolTipText("View the list of Exam Takers");
        examTakerListButton.addActionListener(e -> new ExamTakerListFrame());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(assistantModeratorListButton, gbc);

        gbc.gridy = 2;
        panel.add(studentListButton, gbc);

        gbc.gridy = 3;
        panel.add(moderatorApprovalButton, gbc);

        gbc.gridy = 4;
        panel.add(studentScorecardButton, gbc);

        gbc.gridy = 5;
        panel.add(moderatorListButton, gbc);

        gbc.gridy = 6;
        panel.add(examTakerListButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        return button;
    }

    public static void main(String[] args) {
        new AdminPanelFrame();
    }
}
