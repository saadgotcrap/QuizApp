package examtakerapproval;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExamTakerApprovalFrame extends JFrame {
    private DefaultListModel<String> listModel;
    private List<String> pendingCredentials;

    public ExamTakerApprovalFrame() {
        setTitle("Exam Taker Approval");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();
        JList<String> pendingList = new JList<>(listModel);
        pendingCredentials = new ArrayList<>();
        loadPendingRegistrations();

        JScrollPane scrollPane = new JScrollPane(pendingList);

        JButton approveButton = new JButton("Approve");
        approveButton.setPreferredSize(new Dimension(150, 30));
        approveButton.addActionListener(e -> approveSelected(pendingList.getSelectedIndices()));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(approveButton, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadPendingRegistrations() {
        listModel.clear();
        pendingCredentials.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("pendingExamTakerCredentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pendingCredentials.add(line);
                String[] data = line.split(";");
                listModel.addElement("Name: " + data[0] + ", Email: " + data[1] + ", Username: " + data[2]);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading pending registrations.");
        }
    }

    private void approveSelected(int[] selectedIndices) {
        List<String> remainingRegistrations = new ArrayList<>(pendingCredentials);
        List<String> approvedRegistrations = new ArrayList<>();

        for (int index : selectedIndices) {
            approvedRegistrations.add(pendingCredentials.get(index));
            remainingRegistrations.remove(pendingCredentials.get(index));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pendingExamTakerCredentials.txt"))) {
            for (String reg : remainingRegistrations) {
                writer.write(reg);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating pending registrations.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("examTakerCredentials.txt", true))) {
            for (String reg : approvedRegistrations) {
                writer.write(reg);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error approving registrations.");
        }

        loadPendingRegistrations();
    }

    public static void main(String[] args) {
        new ExamTakerApprovalFrame();
    }
}