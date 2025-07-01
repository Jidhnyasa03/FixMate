package servlets;

import model.User;
import utils.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/createProfile")
public class CreateProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !"provider".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getId();
        String phone = request.getParameter("phone");
        String serviceType = request.getParameter("service_type");
        int experience = Integer.parseInt(request.getParameter("experience"));
        double rate = Double.parseDouble(request.getParameter("rate"));
        String bio = request.getParameter("bio");

        // ✅ New: Extract area and city
        String area = request.getParameter("area");
        String city = request.getParameter("city");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO technician_profiles (user_id, service_type, experience_years, rate_per_hour, bio, phone, area, city) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, serviceType);
            ps.setInt(3, experience);
            ps.setDouble(4, rate);
            ps.setString(5, bio);
            ps.setString(6, phone);
            ps.setString(7, area);  // ✅ Insert area
            ps.setString(8, city);  // ✅ Insert city
            ps.executeUpdate();

            response.sendRedirect("provider_dashboard.jsp?profile_created=1");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("create_profile.jsp?error=1");
        }
    }
}
