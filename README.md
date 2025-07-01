# FixMate

FixMate is a Java-based web application designed to connect users with service providers (technicians) for home or personal services. It enables users to request services like plumbing, electrical work, etc., and allows providers to accept or decline incoming service requests.

## 🔧 Tech Stack

- **Backend:** Java Servlets, JSP
- **Frontend:** HTML, CSS (Bootstrap), JSP
- **Database:** MySQL
- **Server:** Apache Tomcat
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA Community Edition

---

## 📁 Project Structure

FixMate2/
├── src/
│ └── main/
│ └── java/
│ ├── model/
│ │ ├── User.java
│ │ └── ServiceRequest.java
│ ├── service/
│ │ ├── UserService.java
│ │ ├── RequestService.java
│ │ └── MatchingService.java
│ └── servlets/
│ ├── LoginServlet.java
│ ├── RegisterServlet.java
│ ├── CreateProfileServlet.java
│ ├── UpdateProfileServlet.java
│ ├── SearchServlet.java
│ ├── CreateRequestServlet.java
│ ├── RequestServicePageServlet.java
│ └── HandleRequestServlet.java
│
├── webapp/
│ ├── WEB-INF/
│ │ └── web.xml
│ ├── login.jsp
│ ├── register.jsp
│ ├── create_profile.jsp
│ ├── edit_profile.jsp
│ ├── view_profile.jsp
│ ├── browse.jsp
│ ├── request-service.jsp
│ ├── request_confirmation.jsp
│ ├── myrequest.jsp
│ ├── provider_dashboard.jsp
│ ├── requests.jsp
│ └── error.jsp
│
├── pom.xml
└── .gitignore


---

## ✨ Key Features

### 🔐 User Authentication
- User registration and login
- Session management

### 👤 Profile Management
- Users and providers (technicians) can create and update profiles
- Differentiation between user roles using a `userType` field (`TECHNICIAN` or `USER`)

### 🔍 Service Matching
- Users can browse service providers
- Matching logic implemented in `MatchingService.java`

### 🛠️ Requesting Services
- Users select a provider and submit a service request with:
    - Date and time
    - Address
    - Problem description
- Submitted requests are stored in the `service_requests` table

### 🧾 Viewing Requests
- Users can see a list of their submitted requests on the dashboard (`myrequest.jsp`)
- Providers can view incoming requests (`provider_dashboard.jsp`)

### ✅ Provider Response
- Providers can **Accept** or **Decline** service requests
- Status updated in the database (e.g., `PENDING`, `ACCEPTED`, `DECLINED`)

---

## 🗃️ Database Schema (MySQL)

### `users` Table
```sql
CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(100),
  userType VARCHAR(20)
);

CREATE TABLE profiles (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  phone VARCHAR(20),
  city VARCHAR(100),
  skills TEXT,
  experience INT,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE service_requests (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  technician_id INT,
  service_type VARCHAR(100),
  request_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  scheduled_date DATETIME,
  status VARCHAR(20) DEFAULT 'PENDING', -- values: PENDING, ACCEPTED, DECLINED
  address TEXT,
  problem_description TEXT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (technician_id) REFERENCES users(id)
);
