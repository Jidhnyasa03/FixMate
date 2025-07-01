package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/fixmate?useSSL=false";
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println("✅ Database connected");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to DB");
            e.printStackTrace();
        }
        return conn;
    }
}
