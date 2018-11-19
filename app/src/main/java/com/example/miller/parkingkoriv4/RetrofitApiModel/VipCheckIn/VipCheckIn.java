package com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VipCheckIn {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("vip_id")
    @Expose
    private String vipId;
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
     *
     */
    public VipCheckIn() {
    }

    /**
     *
     * @param id
     * @param updatedAt
     * @param ticketId
     * @param createdBy
     * @param createdAt
     * @param vipId
     * @param clientId
     */
    public VipCheckIn(String ticketId, String clientId, String vipId, String createdBy, String updatedAt, String createdAt, Integer id) {
        super();
        this.ticketId = ticketId;
        this.clientId = clientId;
        this.vipId = vipId;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.id = id;
    }

    public VipCheckIn(String vipId, String client_id, String emp_id) {
        this.clientId = client_id;
        this.vipId = vipId;
        this.createdBy = emp_id;
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

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
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
