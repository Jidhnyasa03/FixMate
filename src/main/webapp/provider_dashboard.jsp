<%@ page import="java.util.*, model.*, service.*" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"provider".equals(user.getUserType())) {
        response.sendRedirect("login.jsp");
        return;
    }

    RequestService requestService = new RequestService();
    List<ServiceRequest> requests = requestService.getRequestsByProvider(user.getId());
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FixMate | Provider Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .badge-pending { background-color: #ffc107; color: #000; }
        .badge-accepted { background-color: #28a745; }
        .badge-rejected { background-color: #dc3545; }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp" />

<div class="container mt-4">
    <h3>Welcome, <%= user.getName() %>!</h3>

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

    <h5 class="mt-4">Incoming Service Requests</h5>

    <div class="table-responsive">
        <table class="table table-bordered table-hover mt-3">
            <thead class="table-light">
                <tr>
                    <th>Customer ID</th>
                    <th>Service Type</th>
                    <th>Description</th>
                    <th>Preferred Time</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <% if (requests.isEmpty()) { %>
                <tr><td colspan="6" class="text-center">No service requests yet.</td></tr>
            <% } %>
            <% for(ServiceRequest req : requests) { %>
                <tr>
                    <td><%= req.getCustomerId() %></td>
                    <td><%= req.getServiceType() %></td>
                    <td><%= req.getProblemDescription() != null ? req.getProblemDescription() : "-" %></td>
                    <td><%= req.getPreferredDatetime() != null ? req.getPreferredDatetime() : "-" %></td>
                    <td>
                        <span class="badge badge-<%= req.getStatus().toLowerCase() %>">
                            <%= req.getStatus() %>
                        </span>
                    </td>
                    <td>
                        <% if ("pending".equals(req.getStatus())) { %>
                            <form action="handleRequest" method="post" class="d-inline">
                                <input type="hidden" name="requestId" value="<%= req.getId() %>">
                                <button type="submit" name="action" value="accept" class="btn btn-success btn-sm me-1">Accept</button>
                                <button type="submit" name="action" value="reject" class="btn btn-danger btn-sm">Reject</button>
                            </form>
                        <% } else { %>
                            <span class="text-muted">No actions available</span>
                        <% } %>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>