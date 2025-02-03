package assistantmoderatorsettimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AssistantModeratorSetTimerFrame extends JFrame {
    private JTextField timerField;

    public AssistantModeratorSetTimerFrame() {
        setTitle("Set Timer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        timerField = new JTextField();
        timerField.setPreferredSize(new Dimension(200, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Set Time (in seconds):"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(timerField, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTimer();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(saveButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveTimer() {
        String timer = timerField.getText();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("timer.txt"))) {
            writer.write(timer);
            JOptionPane.showMessageDialog(this, "Timer set successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error setting timer.");
        }
    }

    public static void main(String[] args) {
        new AssistantModeratorSetTimerFrame();
    }
}