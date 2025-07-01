package servlets;

import model.User;
import utils.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/request-service")
public class RequestServicePageServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RequestServicePageServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String techIdParam = request.getParameter("technicianId");
        String serviceType = request.getParameter("serviceType");

        try {
            LOGGER.info("Attempting to load technician ID: " + techIdParam);
            int technicianId = Integer.parseInt(techIdParam);

            // Debug: Directly test database connection
            try (Connection testConn = DBConnection.getConnection()) {
                LOGGER.info("Database connection test successful");
            }

            User technician = getTechnicianById(technicianId);

            if (technician == null) {
                LOGGER.warning("Technician not found in database for ID: " + technicianId);
                response.sendRedirect("browse.jsp?error=technician_not_found");
                return;
            }

            request.setAttribute("technician", technician);
            request.setAttribute("serviceType", serviceType);
            request.getRequestDispatcher("request-service.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid technician ID format: " + techIdParam, e);
            request.setAttribute("error", "Invalid technician ID format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error details:", e);
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private User getTechnicianById(int id) throws SQLException {
        String sql = "SELECT u.id, u.name, u.email, u.phone, u.city, " +
                "tp.service_type, tp.experience_years, " +
                "tp.rate_per_hour, tp.bio, tp.area " +
                "FROM users u JOIN technician_profiles tp " +
                "ON u.id = tp.user_id " +
                "WHERE u.id = ? AND u.user_type = 'provider'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User tech = new User();
                // Set basic user info
                tech.setId(rs.getInt("id"));
                tech.setName(rs.getString("name"));
                tech.setEmail(rs.getString("email"));
                tech.setPhone(rs.getString("phone"));
                tech.setCity(rs.getString("city"));

                // Set technician-specific info
//                tech.setServiceType(rs.getString("service_type"));
                tech.setRatePerHour(rs.getDouble("rate_per_hour"));
                tech.setExperienceYears(rs.getInt("experience_years"));
                tech.setBio(rs.getString("bio"));
                tech.setArea(rs.getString("area"));

                return tech;
            }
        }
        return null;
    }
}
