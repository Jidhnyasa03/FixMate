<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - FixMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="alert alert-danger text-center">
        <h4>Oops! Something went wrong.</h4>
        <p>
            ${requestScope.error != null ? requestScope.error : "An unexpected error occurred. Please try again later."}
        </p>
        <a href="browse.jsp" class="btn btn-primary mt-3">Go Back</a>
    </div>
</div>
</body>
</html>
