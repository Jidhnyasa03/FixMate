<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>FixMate - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .hero-section { background-color: #007BFF; color: white; padding: 5rem 0; }
        .service-card { transition: transform 0.3s; margin-bottom: 20px; }
        .service-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.1); }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">FixMate</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="login.jsp">Login</a>
                <a class="nav-link" href="register.jsp">Register</a>
            </div>
        </div>
    </nav>

    <div class="hero-section text-center">
        <div class="container">
            <h1 class="display-4">Find Trusted Local Services</h1>
            <p class="lead">Connecting you with reliable service providers in your city</p>
            <a href="register.jsp" class="btn btn-light btn-lg mt-3">Get Started</a>
        </div>
    </div>

    <div class="container mb-5">
        <h2 class="text-center my-4">Popular Services</h2>
        <div class="row">
            <div class="col-md-4">
                <div class="card service-card">
                    <div class="card-body text-center">
                        <h5 class="card-title">Plumbers</h5>
                        <p class="card-text">Find experienced plumbers for all your needs.</p>
                        <a href="browse.jsp?service=Plumber" class="btn btn-primary">Find Now</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card service-card">
                    <div class="card-body text-center">
                        <h5 class="card-title">Electricians</h5>
                        <p class="card-text">Professional electricians for wiring and repairs.</p>
                        <a href="browse.jsp?service=Electrician" class="btn btn-primary">Find Now</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card service-card">
                    <div class="card-body text-center">
                        <h5 class="card-title">Cleaners</h5>
                        <p class="card-text">Home and office cleaning services.</p>
                        <a href="browse.jsp?service=Cleaner" class="btn btn-primary">Find Now</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>