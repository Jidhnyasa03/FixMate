<%@page import="java.sql.*, utils.DBConnection"%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Connection conn = DBConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM technician_profiles WHERE user_id = ?");
    ps.setInt(1, user.getId());
    ResultSet rs = ps.executeQuery();

    if (!rs.next()) {
        response.sendRedirect("create_profile.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Your Profile - FixMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <jsp:include page="navbar.jsp"/>

    <div class="container mt-5">
        <div class="card shadow">
            <div class="card-header bg-primary text-white">
                <h3 class="mb-0">Your Technician Profile</h3>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <img src="https://via.placeholder.com/200" class="img-fluid rounded-circle mb-3" alt="Profile Picture">
                        <h4><%= user.getName() %></h4>
                        <p class="text-muted"><%= rs.getString("service_type") %></p>
                        <a href="edit_profile.jsp" class="btn btn-primary btn-sm">Edit Profile</a>
                    </div>
                    <div class="col-md-8">
                        <div class="profile-details">
                            <p><strong><i class="fas fa-phone"></i> Phone:</strong> <%= rs.getString("phone") != null ? rs.getString("phone") : "Not provided" %></p>
                            <p><strong><i class="fas fa-tools"></i> Service Type:</strong> <%= rs.getString("service_type") %></p>
                            <p><strong><i class="fas fa-map-marker-alt"></i> Area:</strong> <%= rs.getString("area") %></p>
                            <p><strong><i class="fas fa-city"></i> City:</strong> <%= rs.getString("city") %></p>
                            <p><strong><i class="fas fa-briefcase"></i> Experience:</strong> <%= rs.getInt("experience_years") %> years</p>
                            <p><strong><i class="fas fa-money-bill-wave"></i> Rate:</strong> ₹<%= rs.getDouble("rate_per_hour") %> per hour</p>
                            <p><strong><i class="fas fa-info-circle"></i> Bio:</strong></p>
                            <div class="card card-body bg-light">
                                <%= rs.getString("bio") != null ? rs.getString("bio") : "No bio provided" %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%
    rs.close();
    ps.close();
    conn.close();
%>