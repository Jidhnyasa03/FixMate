<%@ page import="javax.servlet.http.*, javax.servlet.*, java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DBConnection" %>
<%@ page import="model.User" %>
<%@ page session="true" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null || user.getUserType() == null || !"provider".equals(user.getUserType())) {
        response.sendRedirect("login.jsp");
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        ps = conn.prepareStatement("SELECT * FROM technician_profiles WHERE user_id = ?");
        ps.setInt(1, user.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            response.sendRedirect("view_profile.jsp");
            return;
        }
    } catch (Exception e) {
        e.printStackTrace();  // optional: log it for debugging
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (conn != null) conn.close();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Create Technician Profile | FixMate</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="container mt-5">
    <h3 class="mb-4">Create Your Technician Profile</h3>

    <form action="createProfile" method="post">
        <div class="mb-3">
            <label for="phone">Phone Number</label>
            <input type="tel" name="phone" id="phone" class="form-control" pattern="[0-9]{10}" maxlength="10" required>
        </div>

        <div class="mb-3">
            <label>Service Area / Locality</label>
            <input type="text" name="area" class="form-control" required>
        </div>

        <div class="mb-3">
            <label>City</label>
            <input type="text" name="city" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="service_type">Service Type</label>
            <select name="service_type" id="service_type" class="form-select" required>
                <option value="">-- Select --</option>
                <option value="Plumber">Plumber</option>
                <option value="Electrician">Electrician</option>
                <option value="Carpenter">Carpenter</option>
                <option value="AC Technician">AC Technician</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="experience">Years of Experience</label>
            <input type="number" name="experience" id="experience" class="form-control" min="0" required>
        </div>

        <div class="mb-3">
            <label for="rate">Rate per Hour (₹)</label>
            <input type="number" step="0.1" name="rate" id="rate" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="bio">About You / Bio</label>
            <textarea name="bio" id="bio" class="form-control" rows="4" required></textarea>
        </div>

        <button type="submit" class="btn btn-success">Create Profile</button>
    </form>
</div>

</body>
</html>
