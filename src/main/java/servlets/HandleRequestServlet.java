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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/handleRequest")
public class HandleRequestServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HandleRequestServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"provider".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            String action = request.getParameter("action");

            String status = "pending";
            if ("accept".equalsIgnoreCase(action)) {
                status = "accepted";
            } else if ("reject".equalsIgnoreCase(action)) {
                status = "rejected";
            }

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                         "UPDATE requests SET status = ? WHERE id = ? AND provider_id = ?")) {
                ps.setString(1, status);
                ps.setInt(2, requestId);
                ps.setInt(3, user.getId());

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    session.setAttribute("message", "Request has been " + status + " successfully");
                } else {
                    session.setAttribute("error", "Failed to update request. It may not exist or you don't have permission.");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error while updating request status", e);
                session.setAttribute("error", "Database error occurred");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid request ID format", e);
            session.setAttribute("error", "Invalid request ID");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in HandleRequestServlet", e);
            session.setAttribute("error", "An unexpected error occurred");
        }

        response.sendRedirect("provider_dashboard.jsp");
    }
}