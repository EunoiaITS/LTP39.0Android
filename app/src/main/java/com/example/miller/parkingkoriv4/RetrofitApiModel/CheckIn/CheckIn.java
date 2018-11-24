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
    @SerializedName("_token")
    @Expose
    private String _token;

    /**
     * No args constructor for use in serialization
     *
     */
    public CheckIn() {
    }

    /**
     *
     * @param createdBy
     * @param vehicleType
     * @param vehicleReg
     * @param clientId
     */
    public CheckIn(String ticketId, String clientId, String vehicleReg, String vehicleType, String createdBy, String updatedAt, String createdAt, Integer id, String _token) {
        super();
        this.ticketId = ticketId;
        this.clientId = clientId;
        this.vehicleReg = vehicleReg;
        this.vehicleType = vehicleType;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.id = id;
        this._token = _token;
    }


    public CheckIn(String clientId, String vehicleReg, String vehicleType, String createdBy, String _token) {
        super();

        this.clientId = clientId;
        this.vehicleReg = vehicleReg;
        this.vehicleType = vehicleType;
        this.createdBy = createdBy;
        this._token = _token;
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

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
