<%@page import="service.RequestService"%>
<%@page import="model.ServiceRequest"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>My Requests - FixMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .badge-pending { background-color: #ffc107; color: #000; }
        .badge-accepted { background-color: #28a745; }
        .badge-rejected { background-color: #dc3545; }
        .badge-completed { background-color: #17a2b8; }
    </style>
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
        <% if (session.getAttribute("message") != null) { %>
            <div class="alert alert-success alert-dismissible fade show">
                <%= session.getAttribute("message") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% session.removeAttribute("message"); %>
        <% } %>
        <% if (session.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show">
                <%= session.getAttribute("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% session.removeAttribute("error"); %>
        <% } %>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>My Service Requests</h2>
            <a href="browse.jsp" class="btn btn-primary">Find More Services</a>
        </div>

        <%
            User user = (User) session.getAttribute("user");
            RequestService requestService = new RequestService();
            List<ServiceRequest> requests = requestService.getRequestsByCustomer(user.getId());

            if (requests != null && !requests.isEmpty()) {
        %>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>Service</th>
                        <th>Provider</th>
                        <th>Status</th>
                        <th>Request Date</th>
                        <th>Preferred Date</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (ServiceRequest req : requests) { %>
                    <tr>
                        <td><%= req.getServiceType() %></td>
                        <td>Provider #<%= req.getProviderId() %></td>
                        <td>
                            <span class="badge badge-<%= req.getStatus().toLowerCase() %>">
                                <%= req.getStatus().toUpperCase() %>
                            </span>
                        </td>
                        <td><%= req.getRequestTime() %></td>
                        <td><%= req.getPreferredDatetime() != null ? req.getPreferredDatetime() : "Not specified" %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        <% } else { %>
        <div class="alert alert-info">You haven't made any service requests yet.</div>
        <% } %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>