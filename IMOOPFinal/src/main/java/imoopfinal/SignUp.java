package imoopfinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp extends JFrame {
    private JTextField signUpUsernameField;
    private JPasswordField signUpPasswordField;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JCheckBox teacherCheckBox;
    private JCheckBox studentCheckBox;
    private JComboBox<String> departmentComboBox;
    private JLabel departmentLabel;


    public SignUp() {
        setTitle("Sign Up");
        setSize(400, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        signUpUsernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(signUpUsernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        signUpPasswordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(signUpPasswordField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        emailField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(emailField, gbc);

        JLabel firstNameLabel = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(firstNameLabel, gbc);

        firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(firstNameField, gbc);

        JLabel lastNameLabel = new JLabel("Last Name:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lastNameLabel, gbc);

        lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(lastNameField, gbc);

        JLabel userTypeLabel = new JLabel("User Type:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(userTypeLabel, gbc);

        JPanel userTypePanel = new JPanel();
        teacherCheckBox = new JCheckBox("Teacher");
        studentCheckBox = new JCheckBox("Student");

        userTypePanel.add(teacherCheckBox);
        userTypePanel.add(studentCheckBox);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(userTypePanel, gbc);

        departmentLabel = new JLabel("Department:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(departmentLabel, gbc);

        String[] departments = {"CCS", "Fine Arts", "Department 3", "Department 4"};
        departmentComboBox = new JComboBox<>(departments);
        departmentComboBox.setPreferredSize(new Dimension(150, 25));
       
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(departmentComboBox, gbc);


        JButton signUpButton = new JButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(signUpButton, gbc);

        JButton logoutButton = new JButton("Cancel");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        panel.add(logoutButton, gbc);

        add(panel);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login().setVisible(true);
            }
        });

        signUpButton.addActionListener(e -> {
            String username = signUpUsernameField.getText().trim();
            String password = new String(signUpPasswordField.getPassword()).trim();
            String email = emailField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String userType = teacherCheckBox.isSelected() ? "Teacher" : studentCheckBox.isSelected() ? "Student" : "";
            String department = (String) departmentComboBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || userType.isEmpty()) {
                JOptionPane.showMessageDialog(SignUp.this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = IMOOPFinal.getConnection()) {
                String sql = "INSERT INTO users (username, password, email, firstname, lastname, usertype, department) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    pstmt.setString(3, email);
                    pstmt.setString(4, firstName);
                    pstmt.setString(5, lastName);
                    pstmt.setString(6, userType);
                    pstmt.setString(7, department);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(SignUp.this, "Sign Up successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new Login().setVisible(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(SignUp.this, "Username already taken!" );
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignUp().setVisible(true);
        });
    }
}
