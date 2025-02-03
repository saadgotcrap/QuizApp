package adminpanel.assistantmoderatorlist;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class AssistantModeratorCellRenderer extends JPanel implements ListCellRenderer<AssistantModeratorPanel> {
    public AssistantModeratorCellRenderer() {
        this.setLayout(new GridBagLayout());
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends AssistantModeratorPanel> list, AssistantModeratorPanel value, int index, boolean isSelected, boolean cellHasFocus) {
        this.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(value.getName()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(value.getEmail()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(value.getDepartment()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(value.getPhone()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        this.add(new JLabel(value.getPassword()), gbc);

        if (isSelected) {
            this.setBackground(list.getSelectionBackground());
        } else {
            this.setBackground(list.getBackground());
        }

        return this;
    }
}
