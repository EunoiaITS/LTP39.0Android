package com.example.miller.parkingkoriv4.RetrofitApiModel.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("details")
    @Expose
    private UserDetails details;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("vehicle_types")
    @Expose
    private List<VehicleType> vehicleTypes = null;

    /**
     * No args constructor for use in serialization
     */
    public User() {
    }

    /**
     * @param updatedAt
     * @param id
     * @param client
     * @param details
     * @param status
     * @param email
     * @param createdAt
     * @param name
     * @param role
     * @param vehicleTypes
     */
    public User(int id, String name, String email, String role, String status, String createdAt, String updatedAt, UserDetails details, Client client, List<VehicleType> vehicleTypes) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.details = details;
        this.client = client;
        this.vehicleTypes = vehicleTypes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserDetails getDetails() {
        return details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }
}
