package servlets;

import model.User;
import service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get parameters from the form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Authenticate user
        UserService userService = new UserService();
        User user = userService.loginUser(email, password);

        if (user != null) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on user type
            switch (user.getUserType()) {
                case "admin":
                    response.sendRedirect("admin_dashboard.jsp");
                    break;
                case "provider":
                    response.sendRedirect("provider_dashboard.jsp");
                    break;
                default:
                    response.sendRedirect("myrequest.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}