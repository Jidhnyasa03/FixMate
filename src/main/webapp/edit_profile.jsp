<%@ page import="model.User, java.sql.*, utils.DBConnection" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"provider".equals(user.getUserType())) {
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
    <title>Edit Technician Profile | FixMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container mt-5">
    <h3 class="mb-4">Edit Your Technician Profile</h3>
    <% if ("1".equals(request.getParameter("error"))) { %>
        <div class="alert alert-danger">Something went wrong while updating. Please try again.</div>
    <% } %>

    <form action="updateProfile" method="post">
        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <label><i class="fas fa-phone"></i> Phone Number</label>
                    <input type="tel" name="phone" class="form-control" value="<%= rs.getString("phone") %>" required>
                </div>

                <div class="mb-3">
                    <label><i class="fas fa-tools"></i> Service Type</label>
                    <select name="service_type" class="form-select" required>
                        <option value="Plumber" <%= "Plumber".equals(rs.getString("service_type")) ? "selected" : "" %>>Plumber</option>
                        <option value