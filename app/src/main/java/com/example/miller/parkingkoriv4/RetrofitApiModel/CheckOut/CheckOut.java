package com.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOut {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("receipt_id")
    @Expose
    private String receiptId;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("vehicle_reg")
    @Expose
    private String vehicleReg;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("employee")
    @Expose
    private String updatedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("fair")
    @Expose
    private Integer fair;
    @SerializedName("_token")
    @Expose
    private String _token;


    public CheckOut() {
    }

    public CheckOut(String ticketId, String updatedBy, String updatedAt, String _token, String vech_reg) {
        this.ticketId = ticketId;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this._token = _token;
        this.vehicleReg = vech_reg;
    }

    /**
     * @param updatedAt
     * @param id
     * @param ticketId
     * @param createdBy
     * @param createdAt
     * @param vehicleType
     * @param fair
     * @param vehicleReg
     * @param clientId
     * @param updatedBy
     * @param receiptId
     */
    public CheckOut(Integer id, String clientId, String ticketId, String receiptId, String vehicleType, String vehicleReg, String createdBy, String updatedBy, String createdAt, String updatedAt, Integer fair) {
        super();
        this.id = id;
        this.clientId = clientId;
        this.ticketId = ticketId;
        this.receiptId = receiptId;
        this.vehicleType = vehicleType;
        this.vehicleReg = vehicleReg;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.fair = fair;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleReg() {
        return vehicleReg;
    }

    public void setVehicleReg(String vehicleReg) {
        this.vehicleReg = vehicleReg;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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

    public Integer getFair() {
        return fair;
    }

    public void setFair(Integer fair) {
        this.fair = fair;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
