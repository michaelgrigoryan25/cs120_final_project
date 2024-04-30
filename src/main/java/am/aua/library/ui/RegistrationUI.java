package am.aua.library.ui;

import am.aua.library.ui.core.LibraryManagementSystemUI;
import am.aua.library.ui.core.Page;
import am.aua.library.ui.core.Text;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistrationUI extends Page {
    private enum UserType {STUDENT, PROFESSOR}

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<UserType> userTypeComboBox;
    private JComboBox institutionComboBox;

    public RegistrationUI() {
        super("Registration");
    }

    @Override
    public void setupPage() {
        this.setLayout(new GridLayout(3, 1));
    }

    @Override
    public void setupComponents() {
        this.add(createTextPanel());
        this.add(createInputPanel());
        this.add(createButtonsPanel());
    }

    private JPanel createTextPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        JLabel text = new Text("Registration", Text.Size.LG);
        panel.add(text);
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(4, 1);
        layout.setVgap(10);
        panel.setLayout(layout);
        panel.setBorder(new EmptyBorder(0, 50, 0, 50));

        JLabel usernameLabel = new Text("Username:");
        JLabel passwordLabel = new Text("Password:");
        JLabel userTypeLabel = new Text("User Type: ");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        userTypeComboBox = createUserTypeComboBox();
        institutionComboBox = createInstitutionsComboBox();

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(userTypeLabel);
        panel.add(userTypeComboBox);
        panel.add(passwordLabel);
        panel.add(passwordField);

        rootPanel.add(panel, BorderLayout.CENTER);
        return rootPanel;
    }

    private JComboBox<UserType> createUserTypeComboBox() {
        return new JComboBox<>(UserType.values());
    }

    private JComboBox createInstitutionsComboBox() {
        return new JComboBox();
    }

    private JPanel createButtonsPanel() {
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(createBackButton());
        panel.add(createRegisterButton());

        rootPanel.add(panel);
        return rootPanel;
    }

    private JButton createBackButton() {
        JButton button = new JButton("Back to Main Menu");
        button.addActionListener(e -> {
            dispose();
            new LibraryManagementSystemUI();
        });

        return button;
    }

    private JButton createRegisterButton() {
        JButton button = new JButton("Register");
        button.addActionListener(e -> {
            String username = this.usernameField.getText().toLowerCase();
            String password = new String(this.passwordField.getPassword());

            if (username.isEmpty() || username.isBlank()) {
                JOptionPane.showMessageDialog(RegistrationUI.this, "Username cannot be empty");
                return;
            }

            if (password.isBlank() || password.length() < 8) {
                JOptionPane.showMessageDialog(RegistrationUI.this, "Password must contain at least 8 characters");
                return;
            }
        });

        return button;
    }
}
