package imoopfinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("FU Student-Teacher Appointment Manager Program");
        setSize(500, 550); // Increased height to accommodate the logo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
        JLabel logoLabel = new JLabel(logoIcon);

        JLabel textLabel = new JLabel("Welcome to FU STAMP!");
        textLabel.setFont(new Font("Arial", Font.BOLD, 22)); 
        textLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Span across two columns
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        logoPanel.add(textLabel, BorderLayout.SOUTH);
        panel.add(logoPanel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset gridwidth
        gbc.anchor = GridBagConstraints.LINE_END; 
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START; 
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END; 
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START; // Align to the start of the column
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        panel.add(loginButton, gbc);

        JButton signUpButton = new JButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        panel.add(signUpButton, gbc);

        add(panel);
        
        usernameField.setPreferredSize(new Dimension(200, 25));
        passwordField.setPreferredSize(new Dimension(200, 25));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Username and password must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn = IMOOPFinal.getConnection()) {
                    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, username);
                        pstmt.setString(2, password);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                String userType = rs.getString("usertype");
                                String firstName = rs.getString("firstname");
                                String lastName = rs.getString("lastname");
                                String department = rs.getString("department");
                                
                                if ("Teacher".equals(userType)) {
                                    new Teacher(firstName, lastName, department).setVisible(true);
                                } else if ("Student".equals(userType)) {
                                    new Student(firstName, lastName, department).setVisible(true);
                                }
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(Login.this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(Login.this, "Error logging in: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signUpButton.addActionListener(e -> {
            dispose();
            new SignUp().setVisible(true);
        });
    }
}
/*
 private String authenticateAndGetDepartment(String username, String password, String userType) {
        // Perform authentication and get department from the database
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "SELECT department FROM users WHERE username = ? AND password = ? AND usertype = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, userType);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("department"); // Return the department if user is found
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Return null if authentication fails or user not found
    }

    private String authenticateAndGetCourse(String username, String password) {
        // Perform authentication and get course from the database
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "SELECT course FROM users WHERE username = ? AND password = ? AND usertype = 'student'";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("course"); // Return the course if user is found
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Return null if authentication fails or user not found
    }
*/