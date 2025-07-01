package servlets;

import model.User;
import service.MatchingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import service.UserService;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String serviceType = request.getParameter("service");
        String city = request.getParameter("city");
        String area = request.getParameter("area");

        // Validate required parameters
        if (serviceType == null || serviceType.isEmpty() ||
                city == null || city.isEmpty()) {
            response.sendRedirect("browse.jsp?error=missing_params");
            return;
        }

        UserService userService = new UserService();
        List<User> technicians;

        // Use the matching service for better results
        MatchingService matchingService = new MatchingService();
        technicians = matchingService.getBestMatchedTechnicians(city, area, serviceType);

        // Fallback to simple search if no matches
        if (technicians.isEmpty()) {
            technicians = userService.searchTechnicians(serviceType, city, area);
        }

        request.setAttribute("serviceType", serviceType);
        request.setAttribute("city", city);
        request.setAttribute("area", area);
        request.setAttribute("technicians", technicians);

        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }
}