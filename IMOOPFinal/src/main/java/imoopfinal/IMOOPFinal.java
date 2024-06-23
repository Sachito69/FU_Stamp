package imoopfinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class IMOOPFinal {
    public static void main(String[] args) {
        // Ensure the SQLite database and necessary tables exist
        try (Connection conn = getConnection()) {
            createTables(conn);
            System.out.println("Database tables created successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }

        // Launch the Login UI
        javax.swing.SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }

    private static void createTables(Connection conn) throws SQLException {
        createUsersTable(conn);
        createScheduleTable(conn);
        createAppointmentsTable(conn);
    }

    private static void createUsersTable(Connection conn) throws SQLException {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "firstname TEXT NOT NULL," +
                "lastname TEXT NOT NULL," +
                "department TEXT NOT NULL," +
                "usertype TEXT NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
        }
    }

    private static void createScheduleTable(Connection conn) throws SQLException {
        String createScheduleTable = "CREATE TABLE IF NOT EXISTS schedule (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "teacher TEXT NOT NULL," +
                "department TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "year TEXT NOT NULL," +
                "month TEXT NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createScheduleTable);
        }
    }

    private static void createAppointmentsTable(Connection conn) throws SQLException {
        String createAppointmentsTable = "CREATE TABLE IF NOT EXISTS appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "student TEXT NOT NULL," +
                "teacher TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "year TEXT NOT NULL," +
                "course TEXT NOT NULL," +
                "month TEXT NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createAppointmentsTable);
        }
    }

    public static Connection getConnection() throws SQLException {
        String URL = "jdbc:sqlite:testDB.db";
        return DriverManager.getConnection(URL);
    }
}
