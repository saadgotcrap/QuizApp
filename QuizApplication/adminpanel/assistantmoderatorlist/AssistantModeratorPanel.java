package adminpanel.assistantmoderatorlist;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class AssistantModeratorPanel extends JPanel {
    private String name;
    private String email;
    private String department;
    private String phone;
    private String password;

    public AssistantModeratorPanel(String name, String email, String department, String phone, String password) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.phone = phone;
        this.password = password;
        initializePanel();
    }

    private void initializePanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(name), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(email), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(department), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(phone), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(password), gbc);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
