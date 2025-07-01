<%@ page session="true" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm mb-3">
  <div class="container">
    <a class="navbar-brand fw-bold text-success" href="index.jsp">FixMate</a>

    <div class="d-flex align-items-center">

      <% if (user != null) { %>
        <%-- If provider, show profile link --%>
        <% if ("provider".equals(user.getUserType())) { %>
            <a href="create_profile.jsp" class="btn btn-outline-primary me-2">Profile</a>
        <% } %>

        <span class="me-3 text-secondary">Hello, <%= user.getName() %></span>
        <a href="logout.jsp" class="btn btn-danger">Logout</a>

      <% } else { %>
        <a href="register.jsp" class="btn btn-outline-success me-2">Register</a>
        <a href="login.jsp" class="btn btn-success">Login</a>
      <% } %>

    </div>
  </div>
</nav>
