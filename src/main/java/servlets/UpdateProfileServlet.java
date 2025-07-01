package servlets;

import model.User;
import utils.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user from session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"provider".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve form data
        int userId = user.getId();
        String phone = request.getParameter("phone");
        String serviceType = request.getParameter("service_type");
        int experience = Integer.parseInt(request.getParameter("experience"));
        double rate = Double.parseDouble(request.getParameter("rate"));
        String bio = request.getParameter("bio");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE technician_profiles SET phone = ?, service_type = ?, experience_years = ?, rate_per_hour = ?, bio = ? WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, serviceType);
            ps.setInt(3, experience);
            ps.setDouble(4, rate);
            ps.setString(5, bio);
            ps.setInt(6, userId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("view_profile.jsp?updated=1");
            } else {
                response.sendRedirect("edit_profile.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("edit_profile.jsp?error=1");
        }
    }
}
