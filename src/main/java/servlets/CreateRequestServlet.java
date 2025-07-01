package servlets;

import model.User;
import utils.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/CreateServiceRequest")
public class CreateRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"customer".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int technicianId = Integer.parseInt(request.getParameter("technicianId"));
            String serviceType = request.getParameter("serviceType");
            String scheduledDateStr = request.getParameter("scheduledDate");
            String address = request.getParameter("address");
            String problemDescription = request.getParameter("problemDescription");

            // Debug: Print received parameters
            System.out.println("Received parameters:");
            System.out.println("technicianId: " + technicianId);
            System.out.println("serviceType: " + serviceType);
            System.out.println("scheduledDate: " + scheduledDateStr);
            System.out.println("address: " + address);
            System.out.println("problemDescription: " + problemDescription);

            // Convert input date-time string to Timestamp
            LocalDateTime scheduledDateTime = LocalDateTime.parse(scheduledDateStr.replace(" ", "T")); // Fix for datetime format
            Timestamp scheduledDate = Timestamp.valueOf(scheduledDateTime);

            try (Connection conn = DBConnection.getConnection()) {
                // Use the same table name as in RequestService class
                String sql = "INSERT INTO requests (customer_id, provider_id, service_type, " +
                        "status, problem_description, preferred_datetime, address) " +
                        "VALUES (?, ?, ?, 'pending', ?, ?, ?)";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, user.getId());
                stmt.setInt(2, technicianId);
                stmt.setString(3, serviceType);
                stmt.setString(4, problemDescription);
                stmt.setTimestamp(5, scheduledDate);
                stmt.setString(6, address);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    response.sendRedirect("request_confirmation.jsp");
                    return; // Important to return after redirect
                } else {
                    throw new SQLException("Failed to create service request");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Add error to session so it persists across redirect
            request.getSession().setAttribute("error", "Error creating request: " + e.getMessage());
            response.sendRedirect("request-service.jsp?technicianId=" + request.getParameter("technicianId") +
                    "&serviceType=" + request.getParameter("serviceType"));
        }
    }
}