<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Request Confirmation | FixMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container mt-4">
    <div class="card shadow text-center">
        <div class="card-body">
            <div class="mb-4">
                <i class="fas fa-check-circle text-success" style="font-size: 5rem;"></i>
            </div>
            <h3 class="card-title">Service Request Submitted Successfully!</h3>
            <p class="card-text">Your technician will respond shortly to confirm your appointment.</p>
            <div class="mt-4">
                <a href="myrequest.jsp" class="btn btn-primary">View Dashboard</a>
                <a href="browse.jsp" class="btn btn-outline-secondary">Find Another Technician</a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
</body>
</html>