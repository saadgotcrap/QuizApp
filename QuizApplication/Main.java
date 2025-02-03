import adminlogin.AdminLoginFrame;
import moderatorlogin.ModeratorLogin;
import moderatorregister.ModeratorRegister;
import studentregister.StudentRegisterFrame;
import studentlogin.StudentLoginFrame;
import assistantmoderatorregister.AssistantModeratorRegisterFrame;
import assistantmoderatorlogin.AssistantModeratorLogin;
import examtakerregister.ExamTakerRegisterFrame;
import examtakerlogin.ExamTakerLoginFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login and Register");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("quiz/image.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Background image not found!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        Image finalBackgroundImage = backgroundImage;
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalBackgroundImage != null) {
                    g.drawImage(finalBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(null);
        JLabel heading = new JLabel("WELCOME TO QUIZ PANDA", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(Color.BLACK);

        heading.setFont(new Font("Serif", Font.BOLD, 30));
        heading.setBounds(100, 30, 500, 60);
        backgroundPanel.add(heading);
        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setForeground(Color.BLACK);
        roleLabel.setBounds(260, 160, 200, 30);
        backgroundPanel.add(roleLabel);

        String[] roles = {"Admin", "Moderator", "Assistant Moderator","Exam Taker","Student"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(250, 200, 200, 30);
        backgroundPanel.add(roleComboBox);
        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(150, 300, 150, 50);
        backgroundPanel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(400, 300, 150, 50);
        backgroundPanel.add(registerButton);

        loginButton.setEnabled(true);
        registerButton.setEnabled(!"Admin".equals(roleComboBox.getSelectedItem()));

        roleComboBox.addActionListener(e -> {
            String selectedRole = (String) roleComboBox.getSelectedItem();
            registerButton.setEnabled(!"Admin".equals(selectedRole));
        });

        loginButton.addActionListener(e -> {
            String selectedRole = (String) roleComboBox.getSelectedItem();
            switch (selectedRole) {
                case "Admin" -> new AdminLoginFrame();
                case "Moderator" -> new ModeratorLogin();
                case "Assistant Moderator" -> new AssistantModeratorLogin();
                case "Student" -> new StudentLoginFrame();
                case "Exam Taker" -> new ExamTakerLoginFrame();
            }
        });

        registerButton.addActionListener(e -> {
            String selectedRole = (String) roleComboBox.getSelectedItem();
            switch (selectedRole) {
                case "Admin" -> JOptionPane.showMessageDialog(frame, "Admin registration is not allowed.");
                case "Moderator" -> new ModeratorRegister();
                case "Assistant Moderator" -> new AssistantModeratorRegisterFrame();
                case "Student" -> new StudentRegisterFrame();
                case "Exam Taker" -> new ExamTakerRegisterFrame();
            }
        });

        frame.add(backgroundPanel);

        frame.setVisible(true);
    }
}