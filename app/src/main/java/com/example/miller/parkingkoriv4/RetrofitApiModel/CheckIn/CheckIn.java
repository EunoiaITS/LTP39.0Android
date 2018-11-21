package com.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckIn {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("vehicle_reg")
    @Expose
    private String vehicleReg;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * No args constructor for use in serialization
     */
    public CheckIn() {
    }

    /**
     * @param id
     * @param updatedAt
     * @param ticketId
     * @param createdBy
     * @param createdAt
     * @param vehicleType
     * @param vehicleReg
     * @param clientId
     */
    public CheckIn(String ticketId, String clientId, String vehicleReg, String vehicleType, String createdBy, String updatedAt, String createdAt, Integer id) {
        super();
        this.ticketId = ticketId;
        this.clientId = clientId;
        this.vehicleReg = vehicleReg;
        this.vehicleType = vehicleType;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.id = id;
    }

    public CheckIn(String clientId, String vehicleReg, String vehicleType, String createdBy) {
        this.clientId = clientId;
        this.vehicleReg = vehicleReg;
        this.vehicleType = vehicleType;
        this.createdBy = createdBy;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVehicleReg() {
        return vehicleReg;
    }

    public void setVehicleReg(String vehicleReg) {
        this.vehicleReg = vehicleReg;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
