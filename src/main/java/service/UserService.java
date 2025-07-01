package service;

import model.User;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {

    // ✅ UPDATED searchTechnicians method
    public List<User> searchTechnicians(String serviceType, String city, String area) {
        List<User> technicians = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT u.*, tp.service_type, tp.experience_years, tp.rate_per_hour, " +
                        "tp.bio, tp.phone, tp.area, tp.city AS profile_city " +
                        "FROM users u JOIN technician_profiles tp ON u.id = tp.user_id " +
                        "WHERE tp.service_type = ? AND tp.city = ?"
        );

        // Add area condition if provided
        if (area != null && !area.trim().isEmpty()) {
            sql.append(" AND LOWER(tp.area) LIKE LOWER(?)");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            stmt.setString(1, serviceType);
            stmt.setString(2, city);

            if (area != null && !area.trim().isEmpty()) {
                stmt.setString(3, "%" + area + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User technician = mapTechnicianFromResultSet(rs);
                technicians.add(technician);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return technicians;
    }

    private User mapTechnicianFromResultSet(ResultSet rs) throws SQLException {
        User technician = new User();
        technician.setId(rs.getInt("id"));
        technician.setName(rs.getString("name"));
        technician.setEmail(rs.getString("email"));
        technician.setUserType(rs.getString("user_type"));
        technician.setCity(rs.getString("profile_city"));
        technician.setPhone(rs.getString("phone"));
        technician.setArea(rs.getString("area"));
        technician.setRatePerHour(rs.getDouble("rate_per_hour"));
        technician.setExperienceYears(rs.getInt("experience_years"));
        technician.setBio(rs.getString("bio"));
        return technician;
    }
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (name, email, password, user_type, city) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = hashPassword(user.getPassword());

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashedPassword);
            stmt.setString(4, user.getUserType());
            stmt.setString(5, user.getCity());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String inputHash = hashPassword(password);

                if (storedHash.equals(inputHash)) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setUserType(rs.getString("user_type"));
                    user.setCity(rs.getString("city"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getProvidersByService(int currentUserId, String serviceType) {
        List<User> providers = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE user_type = 'provider' AND city IN " +
                "(SELECT city FROM users WHERE id = ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User provider = new User();
                provider.setId(rs.getInt("id"));
                provider.setName(rs.getString("name"));
                provider.setEmail(rs.getString("email"));
                provider.setCity(rs.getString("city"));
                providers.add(provider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return providers;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type"));
                user.setCity(rs.getString("city"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type"));
                user.setCity(rs.getString("city"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
