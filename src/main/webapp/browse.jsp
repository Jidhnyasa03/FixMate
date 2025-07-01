<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Find Technicians - FixMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .technician-card { transition: all 0.3s; }
        .technician-card:hover { transform: translateY(-5px); box-shadow: 0 5px 15px rgba(0,0,0,0.1); }
        .rating { color: gold; }
        .error-message { color: red; margin-bottom: 15px; }
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
    <!-- Error Messages -->
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">
            <c:choose>
                <c:when test="${param.error == 'missing_params'}">
                    Please provide both service type and city
                </c:when>
                <c:when test="${param.error == 'server_error'}">
                    An error occurred while searching. Please try again.
                </c:when>
                <c:otherwise>
                    An unknown error occurred
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>

    <div class="card mb-4 shadow">
        <div class="card-body">
            <form method="GET" action="SearchServlet">
                <div class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Service Type</label>
                        <select name="service" class="form-select" required>
                            <option value="">Select a service</option>
                            <option value="Plumber" ${param.service eq 'Plumber' ? 'selected' : ''}>Plumber</option>
                            <option value="Electrician" ${param.service eq 'Electrician' ? 'selected' : ''}>Electrician</option>
                            <option value="Carpenter" ${param.service eq 'Carpenter' ? 'selected' : ''}>Carpenter</option>
                            <option value="AC Technician" ${param.service eq 'AC Technician' ? 'selected' : ''}>AC Technician</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">City</label>
                        <input type="text" name="city" class="form-control" value="${param.city}" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Area (Optional)</label>
                        <input type="text" name="area" class="form-control" value="${param.area}">
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">Search</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Search Results Section -->
    <c:if test="${not empty technicians}">
        <h3 class="mb-3">
            Available ${param.service} Technicians in
            <c:if test="${not empty param.area}">${param.area}, </c:if>${param.city}
        </h3>
        <div class="row row-cols-1 row-cols-md-2 g-4">
            <c:forEach var="tech" items="${technicians}">
                <div class="col">
                    <div class="card technician-card h-100">
                        <div class="card-body">
                            <div class="d-flex">
                                <div class="flex-shrink-0">
                                    <img src="https://via.placeholder.com/80" class="rounded-circle" alt="Technician">
                                </div>
                                <div class="flex-grow-1 ms-3">
                                    <h5 class="card-title">${tech.name}</h5>

                                    <!-- ✂️ Removed rating block to avoid PropertyNotFoundException -->

                                    <p class="card-text">
                                        <i class="fas fa-map-marker-alt"></i> ${tech.city}
                                        <c:if test="${not empty tech.area}">, ${tech.area}</c:if><br>
                                        <i class="fas fa-money-bill-wave"></i> ₹${tech.ratePerHour} per hour<br>
                                        <i class="fas fa-briefcase"></i> ${tech.experienceYears} years experience<br>
                                        <i class="fas fa-envelope"></i> ${tech.email}<br>
                                        <i class="fas fa-phone"></i>
                                        <c:choose>
                                            <c:when test="${not empty tech.phone}">${tech.phone}</c:when>
                                            <c:otherwise>Not provided</c:otherwise>
                                        </c:choose><br>
                                        <i class="fas fa-info-circle"></i> ${tech.bio}
                                    </p>

                                    <div class="d-flex justify-content-between align-items-center">
                                      <a href="request-service?technicianId=${tech.id}&serviceType=${param.service}"

                                          class="btn btn-primary">Request Service</a>

                                        <a href="view-profile.jsp?id=${tech.id}"
                                           class="btn btn-outline-secondary">View Profile</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
