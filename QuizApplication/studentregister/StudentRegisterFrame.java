package studentregister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StudentRegisterFrame extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField classField;
    private JPasswordField passwordField;

    public StudentRegisterFrame() {
        setTitle("Student Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        classField = new JTextField();
        passwordField = new JPasswordField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField.setPreferredSize(new Dimension(200, 30));
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField.setPreferredSize(new Dimension(200, 30));
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        phoneField.setPreferredSize(new Dimension(200, 30));
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Class:"), gbc);

        gbc.gridx = 1;
        classField.setPreferredSize(new Dimension(200, 30));
        panel.add(classField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField.setPreferredSize(new Dimension(200, 30));
        panel.add(passwordField, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 30));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerStudent();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerStudent() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String className = classField.getText();
        String password = new String(passwordField.getPassword());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("studentCredentials.txt", true))) {
            writer.write(email + ";" + password + ";" + name + ";" + phone + ";" + className);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error registering student.");
        }

        saveStudentDetails(name, email, phone, className);
        JOptionPane.showMessageDialog(this, "Student registered successfully!");
    }

    private void saveStudentDetails(String name, String email, String phone, String className) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt", true))) {
            writer.write(name + ";" + email + ";" + phone + ";" + className);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving student details.");
        }
    }

    public static void main(String[] args) {
        new StudentRegisterFrame();
    }
}
