package service;

import model.User;
import utils.DBConnection;

import java.sql.*;
import java.util.*;

public class MatchingService {
    static class TechnicianScore {
        User technician;
        int score;

        TechnicianScore(User technician, int score) {
            this.technician = technician;
            this.score = score;
        }
    }

    public List<User> getBestMatchedTechnicians(String userCity, String userArea, String serviceType) {
        List<User> matched = new ArrayList<>();

        String sql = "SELECT u.*, tp.service_type, tp.experience_years, tp.rate_per_hour, " +
                "tp.bio, tp.phone, tp.area, tp.city AS profile_city " +
                "FROM users u JOIN technician_profiles tp ON u.id = tp.user_id " +
                "WHERE u.user_type = 'provider' AND tp.service_type = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serviceType);
            ResultSet rs = stmt.executeQuery();

            // Use max-heap by inverting the comparator
            PriorityQueue<TechnicianScore> pq = new PriorityQueue<>(
                    (a, b) -> Integer.compare(b.score, a.score)
            );

            while (rs.next()) {
                User tech = new User();
                tech.setId(rs.getInt("id"));
                tech.setName(rs.getString("name"));
                tech.setEmail(rs.getString("email"));
                tech.setCity(rs.getString("profile_city"));
                tech.setUserType("provider");
                tech.setPhone(rs.getString("phone"));
                tech.setArea(rs.getString("area"));
                tech.setExperienceYears(rs.getInt("experience_years"));
                tech.setRatePerHour(rs.getDouble("rate_per_hour"));
                tech.setBio(rs.getString("bio"));

                int score = 0;

                // Location scoring (higher for better matches)
                if (userCity.equalsIgnoreCase(tech.getCity())) {
                    score += 50; // Base city match
                    if (userArea != null && !userArea.isEmpty() &&
                            userArea.equalsIgnoreCase(tech.getArea())) {
                        score += 30; // Bonus for exact area match
                    }
                }

                // Experience scoring (more experience = better)
                score += tech.getExperienceYears();

                // Rate scoring (lower rates = better)
                score -= (int)(tech.getRatePerHour() / 10);

                pq.add(new TechnicianScore(tech, score));
            }

            // Convert priority queue to list
            while (!pq.isEmpty()) {
                matched.add(pq.poll().technician);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matched;
    }
}