package model;

import java.sql.Timestamp;

public class ServiceRequest {
    private int id;
    private int customerId;
    private int providerId;
    private String serviceType;
    private String status;
    private String problemDescription;
    private Timestamp preferredDatetime;
    private Timestamp requestTime;
    private String customerName;
    private String providerName;

    // Getters and Setters for all fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getProviderId() { return providerId; }
    public void setProviderId(int providerId) { this.providerId = providerId; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getProblemDescription() { return problemDescription; }
    public void setProblemDescription(String problemDescription) { this.problemDescription = problemDescription; }

    public Timestamp getPreferredDatetime() { return preferredDatetime; }
    public void setPreferredDatetime(Timestamp preferredDatetime) { this.preferredDatetime = preferredDatetime; }

    public Timestamp getRequestTime() { return requestTime; }
    public void setRequestTime(Timestamp requestTime) { this.requestTime = requestTime; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

}