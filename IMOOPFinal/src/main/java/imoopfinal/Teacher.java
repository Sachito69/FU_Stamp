package imoopfinal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Teacher extends JFrame {
    private JTextField teacherNameField;
    private JTextField departmentField;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> startHourComboBox;
    private JComboBox<String> startMinuteComboBox;
    private JComboBox<String> endHourComboBox;
    private JComboBox<String> endMinuteComboBox;
    private JComboBox<String> studentFilterComboBox;
    private JComboBox<String> yearFilterComboBox;
    private JComboBox<String> monthFilterComboBox;
    private JComboBox<String> courseFilterComboBox;
    private JTable scheduleTable;
    private DefaultTableModel scheduleTableModel;
    private JTable appointmentTable;
    private DefaultTableModel appointmentTableModel;

    public Teacher(String firstName, String lastName, String department) {
        setTitle("Teacher Schedule Management");
        setSize(630, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel teacherNameLabel = new JLabel("Teacher Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        panel.add(teacherNameLabel, gbc);

        teacherNameField = new JTextField(firstName + " " + lastName);
        teacherNameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        panel.add(teacherNameField, gbc);

        JLabel departmentLabel = new JLabel("Department:");
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        panel.add(departmentLabel, gbc);

        departmentField = new JTextField(department);
        departmentField.setEditable(false);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        panel.add(departmentField, gbc);

        JLabel dateLabel = new JLabel("Date:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        panel.add(dateLabel, gbc);

        yearComboBox = new JComboBox<>();
        for (int year = 2024; year <= 2030; year++) {
            yearComboBox.addItem(String.valueOf(year));
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.33;
        gbc.gridwidth = 2;
        panel.add(yearComboBox, gbc);

        monthComboBox = new JComboBox<>();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            monthComboBox.addItem(month);
        }
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.33;
        gbc.gridwidth = 2;
        panel.add(monthComboBox, gbc);

        dayComboBox = new JComboBox<>();
        for (int day = 1; day <= 31; day++) {
            dayComboBox.addItem(String.format("%02d", day));
        }
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.weightx = 0.34;
        gbc.gridwidth = 2;
        panel.add(dayComboBox, gbc);

        JLabel startTimeLabel = new JLabel("Start Time:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        panel.add(startTimeLabel, gbc);

        startHourComboBox = new JComboBox<>();
        for (int hour = 0; hour < 24; hour++) {
            startHourComboBox.addItem(String.format("%02d", hour));
        }
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.33;
        gbc.gridwidth = 1;
        panel.add(startHourComboBox, gbc);

        startMinuteComboBox = new JComboBox<>();
        for (int minute = 0; minute < 60; minute++) {
            startMinuteComboBox.addItem(String.format("%02d", minute));
        }
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.33;
        gbc.gridwidth = 1;
        panel.add(startMinuteComboBox, gbc);

        JLabel endTimeLabel = new JLabel("End Time:");
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        panel.add(endTimeLabel, gbc);

        endHourComboBox = new JComboBox<>();
        for (int hour = 0; hour < 24; hour++) {
            endHourComboBox.addItem(String.format("%02d", hour));
        }
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 0.33;
        gbc.gridwidth = 1;
        panel.add(endHourComboBox, gbc);

        endMinuteComboBox = new JComboBox<>();
        for (int minute = 0; minute < 60; minute++) {
            endMinuteComboBox.addItem(String.format("%02d", minute));
        }
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.weightx = 0.33;
        gbc.gridwidth = 1;
        panel.add(endMinuteComboBox, gbc);
        
        JButton addButton = new JButton("Add to Schedule");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        panel.add(addButton, gbc);

        scheduleTableModel = new DefaultTableModel(new Object[]{"Date", "Time"}, 0);
        scheduleTable = new JTable(scheduleTableModel);
        JScrollPane scheduleScrollPane = new JScrollPane(scheduleTable);
        scheduleScrollPane.setPreferredSize(new Dimension(600, 180));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 12;
        gbc.weightx = 1.0;
        panel.add(scheduleScrollPane, gbc);

        JButton deleteButton = new JButton("Delete Schedule");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(deleteButton, gbc);

        JLabel appointmentLabel = new JLabel("Appointments:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 12;
        gbc.weightx = 1.0;
        panel.add(appointmentLabel, gbc);

        studentFilterComboBox = new JComboBox<>();
        studentFilterComboBox.addItem("All Students");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        panel.add(studentFilterComboBox, gbc);

        yearFilterComboBox = new JComboBox<>();
        yearFilterComboBox.addItem("All Years");
        for (int year = 2024; year <= 2030; year++) {
            yearFilterComboBox.addItem(String.valueOf(year));
        }
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        panel.add(yearFilterComboBox, gbc);

        monthFilterComboBox = new JComboBox<>();
        monthFilterComboBox.addItem("All Months");
        for (String month : months) {
            monthFilterComboBox.addItem(month);
        }
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        panel.add(monthFilterComboBox, gbc);

        courseFilterComboBox = new JComboBox<>();
        courseFilterComboBox.addItem("All Courses");
        courseFilter();
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        panel.add(courseFilterComboBox, gbc);

        appointmentTableModel = new DefaultTableModel(new Object[]{"Student", "Course", "Date", "Time"}, 0);
        appointmentTable = new JTable(appointmentTableModel);
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentTable);
        appointmentScrollPane.setPreferredSize(new Dimension(600, 180));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 12;
        gbc.weightx = 1.0;
        panel.add(appointmentScrollPane, gbc);

        JButton logoutButton = new JButton("Logout");
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        panel.add(logoutButton, gbc);

        add(panel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teacherName = teacherNameField.getText().trim();
                String department = departmentField.getText().trim();
                String date = monthComboBox.getSelectedItem() + "-" + dayComboBox.getSelectedItem() + "-" + yearComboBox.getSelectedItem();
                String time = startHourComboBox.getSelectedItem() + ":" + startMinuteComboBox.getSelectedItem() +" - "+ endHourComboBox.getSelectedItem()+ ":" + endMinuteComboBox.getSelectedItem();
                String selectedYear = (String) yearComboBox.getSelectedItem();
                String selectedMonth = (String) monthComboBox.getSelectedItem(); // Get the month as a string

                if (date.isEmpty() || time.isEmpty()) {
                    JOptionPane.showMessageDialog(Teacher.this, "Date and time must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn = IMOOPFinal.getConnection()) {
                    String sql = "INSERT INTO schedule (teacher, department, date, time, year, month) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, teacherName);
                        pstmt.setString(2, department);
                        pstmt.setString(3, date);
                        pstmt.setString(4, time);
                        pstmt.setString(5, selectedYear);
                        pstmt.setString(6, selectedMonth); // Insert the month as a string
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(Teacher.this, "Schedule added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadSchedule();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(Teacher.this, "Error adding schedule: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(Teacher.this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    dispose();
                    new Login().setVisible(true);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = scheduleTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Teacher.this, "Please select a schedule to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get the date and time from the selected row
                String date = (String) scheduleTableModel.getValueAt(selectedRow, 0);
                String time = (String) scheduleTableModel.getValueAt(selectedRow, 1);

                try (Connection conn = IMOOPFinal.getConnection()) {
                    String sql = "DELETE FROM schedule WHERE date = ? AND time = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, date);
                        pstmt.setString(2, time);

                        int rowsDeleted = pstmt.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(Teacher.this, "Schedule deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            loadSchedule();
                        } else {
                            JOptionPane.showMessageDialog(Teacher.this, "No schedule found to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(Teacher.this, "Error deleting schedule: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ActionListener filterListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAppointments();
            }
        };

        studentFilterComboBox.addActionListener(filterListener);
        yearFilterComboBox.addActionListener(filterListener);
        monthFilterComboBox.addActionListener(filterListener);
        courseFilterComboBox.addActionListener(filterListener);

        loadStudents();
        loadSchedule();
        loadAppointments();
    }

    private void loadSchedule() {
        scheduleTableModel.setRowCount(0);
        try (Connection conn = IMOOPFinal.getConnection()) {
            String sql = "SELECT date, time FROM schedule WHERE teacher = ?";
            String teacherName = teacherNameField.getText().trim();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, teacherName);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String date = rs.getString("date");
                        String time = rs.getString("time");

                        scheduleTableModel.addRow(new Object[]{date, time});
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading schedule: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAppointments() {
        appointmentTableModel.setRowCount(0);
        try (Connection conn = IMOOPFinal.getConnection()) {
            String sql = "SELECT student, course, date, time FROM appointments a " +
                    "LEFT JOIN users u ON student = username WHERE teacher = ?";
            String teacherName = teacherNameField.getText().trim();
            StringBuilder sqlBuilder = new StringBuilder(sql);

            if (studentFilterComboBox.getSelectedItem() != null && !studentFilterComboBox.getSelectedItem().equals("All Students")) {
                sqlBuilder.append(" AND student = ?");
            }

            if (yearFilterComboBox.getSelectedItem() != null && !yearFilterComboBox.getSelectedItem().equals("All Years")) {
                sqlBuilder.append(" AND year = ?");
            }

            if (monthFilterComboBox.getSelectedItem() != null && !monthFilterComboBox.getSelectedItem().equals("All Months")) {
                sqlBuilder.append(" AND month = ?");
            }

            if (courseFilterComboBox.getSelectedItem() != null && !courseFilterComboBox.getSelectedItem().equals("All Courses")) {
                sqlBuilder.append(" AND course = ?");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
                pstmt.setString(1, teacherName);
                int paramIndex = 2;

                if (studentFilterComboBox.getSelectedItem() != null && !studentFilterComboBox.getSelectedItem().equals("All Students")) {
                    pstmt.setString(paramIndex++, (String) studentFilterComboBox.getSelectedItem());
                }

                if (yearFilterComboBox.getSelectedItem() != null && !yearFilterComboBox.getSelectedItem().equals("All Years")) {
                    pstmt.setString(paramIndex++, (String) yearFilterComboBox.getSelectedItem());
                }

                if (monthFilterComboBox.getSelectedItem() != null && !monthFilterComboBox.getSelectedItem().equals("All Months")) {
                    String selectedMonth = (String) monthFilterComboBox.getSelectedItem();
                    pstmt.setString(paramIndex++, selectedMonth);
                }

                if (courseFilterComboBox.getSelectedItem() != null && !courseFilterComboBox.getSelectedItem().equals("All Courses")) {
                    pstmt.setString(paramIndex++, (String) courseFilterComboBox.getSelectedItem());
                }

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String student = rs.getString("student");
                        String course = rs.getString("course");
                        String date = rs.getString("date");
                        String time = rs.getString("time");
                        appointmentTableModel.addRow(new Object[]{student, course, date, time});
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudents() {
    studentFilterComboBox.removeAllItems(); // Clear existing items
    studentFilterComboBox.addItem("All Students");

    try (Connection conn = IMOOPFinal.getConnection()) {
        String sql = "SELECT DISTINCT student FROM appointments";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String student = rs.getString("student");
                studentFilterComboBox.addItem(student);
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error loading students: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void courseFilter() {
        courseFilterComboBox.removeAllItems(); // Clear existing items
        courseFilterComboBox.addItem("All Courses");

        try (Connection conn = IMOOPFinal.getConnection()) {
            String sql = "SELECT DISTINCT course FROM appointments";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String course = rs.getString("course");
                    courseFilterComboBox.addItem(course);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Teacher("John", "Doe", "Dep1").setVisible(true);
            }
        });
    }
}
