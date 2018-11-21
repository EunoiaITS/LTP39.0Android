package com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VipCheckOut {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vip_id")
    @Expose
    private String vipId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("receipt_id")
    @Expose
    private String receiptId;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("employee")
    @Expose
    private String updatedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     *
     * @param vip_id
     * @param employee
     * @param s
     * @param client_id
     */
    public VipCheckOut(String vip_id, String employee, String s, String client_id) {
        this.vipId = vip_id;
        this.clientId = client_id;
        this.updatedAt = s;
        this.updatedBy = employee;
    }

    /**
     * @param updatedAt
     * @param id
     * @param ticketId
     * @param createdBy
     * @param createdAt
     * @param vipId
     * @param clientId
     * @param updatedBy
     * @param receiptId
     */
    public VipCheckOut(Integer id, String vipId, String clientId, String ticketId, String receiptId, String createdBy, String updatedBy, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.vipId = vipId;
        this.clientId = clientId;
        this.ticketId = ticketId;
        this.receiptId = receiptId;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
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
}
