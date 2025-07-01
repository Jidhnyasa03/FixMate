package service;

import model.ServiceRequest;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestService {

    private static final Logger LOGGER = Logger.getLogger(RequestService.class.getName());

    public boolean createRequest(ServiceRequest request) {
        String sql = "INSERT INTO requests (customer_id, provider_id, service_type, " +
                "status, problem_description, preferred_datetime, request_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, request.getCustomerId());
            stmt.setInt(2, request.getProviderId());
            stmt.setString(3, request.getServiceType());
            stmt.setString(4, request.getStatus());
            stmt.setString(5, request.getProblemDescription());
            stmt.setTimestamp(6, request.getPreferredDatetime());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.warning("Creating request failed, no rows affected");
                return false;
            }

            // Set the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    request.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating service request", e);
            return false;
        }
    }

    public List<ServiceRequest> getRequestsByCustomer(int customerId) {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT r.*, u.name as provider_name FROM requests r " +
                "JOIN users u ON r.provider_id = u.id " +
                "WHERE r.customer_id = ? " +
                "ORDER BY r.preferred_datetime DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requests.add(mapResultSetToRequest(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching customer requests", e);
        }
        return requests;
    }

    public List<ServiceRequest> getRequestsByProvider(int providerId) {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT r.* FROM requests r WHERE r.provider_id = ? ORDER BY r.request_time DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, providerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requests.add(mapResultSetToRequest(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching provider requests", e);
        }
        return requests;
    }
    public List<ServiceRequest> getRequestsForProviderWithCustomerInfo(int providerId) {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT r.*, u.name as customer_name, u.phone as customer_phone " +
                "FROM requests r " +
                "JOIN users u ON r.customer_id = u.id " +
                "WHERE r.provider_id = ? " +
                "ORDER BY r.request_time DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, providerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ServiceRequest request = mapResultSetToRequest(rs);
                request.setCustomerName(rs.getString("customer_name"));
//                request.setCustomerPhone(rs.getString("customer_phone"));
                requests.add(request);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching provider requests with customer info", e);
        }
        return requests;
    }

    private ServiceRequest mapResultSetToRequest(ResultSet rs) throws SQLException {
        ServiceRequest request = new ServiceRequest();
        request.setId(rs.getInt("id"));
        request.setCustomerId(rs.getInt("customer_id"));
        request.setProviderId(rs.getInt("provider_id"));
        request.setServiceType(rs.getString("service_type"));
        request.setStatus(rs.getString("status"));
        request.setProblemDescription(rs.getString("problem_description"));
        request.setPreferredDatetime(rs.getTimestamp("preferred_datetime"));
        request.setRequestTime(rs.getTimestamp("request_time"));

        try {
            request.setProviderName(rs.getString("provider_name"));
        } catch (SQLException e) {
            // Column not present in all queries
        }

        return request;
    }
}