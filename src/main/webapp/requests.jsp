<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Request Service - FixMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">FixMate</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h4>Service Request</h4>
            </div>
            <div class="card-body">
                <%
                    User user = (User) session.getAttribute("user");
                    String providerId = request.getParameter("provider_id");
                    String serviceType = request.getParameter("service");
                %>
                <form action="createRequest" method="POST">
                    <input type="hidden" name="customer_id" value="<%= user.getId() %>">
                    <input type="hidden" name="provider_id" value="<%= providerId %>">
                    <input type="hidden" name="service_type" value="<%= serviceType %>">

                    <div class="mb-3">
                        <label class="form-label">Service Type</label>
                        <input type="text" class="form-control" value="<%= serviceType %>" readonly>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Your Details</label>
                        <input type="text" class="form-control"
                               value="<%= user.getName() %> (<%= user.getEmail() %>) - <%= user.getCity() %>" readonly>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Problem Description</label>
                        <textarea class="form-control" name="problem_description" rows="3" required></textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Preferred Date</label>
                        <input type="date" class="form-control" name="preferred_date" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit Request</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>