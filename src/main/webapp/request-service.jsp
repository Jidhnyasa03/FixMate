<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*,model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Request Service | FixMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container mt-4">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h4>Request Service from ${technician.name}</h4>
        </div>
        <div class="card-body">
            <form action="CreateServiceRequest" method="POST">
                <input type="hidden" name="technicianId" value="${technician.id}">
                <input type="hidden" name="serviceType" value="${param.serviceType}">

                <div class="mb-3">
                    <label class="form-label">Service Date and Time</label>
                    <input type="datetime-local" name="scheduledDate" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Service Address</label>
                    <textarea name="address" class="form-control" rows="3" required></textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label">Problem Description</label>
                    <textarea name="problemDescription" class="form-control" rows="5" required></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Submit Request</button>
                <a href="browse.jsp" class="btn btn-outline-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>