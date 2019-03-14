package eis.example.miller.parkingkoriv4.RetrofitApiModel.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingSetting {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("assign_parking_id")
    @Expose
    private String assignParkingId;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("created_by")
    @Expose
    private Object createdBy;
    @SerializedName("modified_by")
    @Expose
    private Object modifiedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public ParkingSetting() {
    }

    /**
     * @param updatedAt
     * @param amount
     * @param id
     * @param createdBy
     * @param vehicleId
     * @param createdAt
     * @param modifiedBy
     * @param assignParkingId
     * @param clientId
     */
    public ParkingSetting(Integer id, String clientId, String assignParkingId, String vehicleId, String amount, Object createdBy, Object modifiedBy, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.clientId = clientId;
        this.assignParkingId = assignParkingId;
        this.vehicleId = vehicleId;
        this.amount = amount;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getAssignParkingId() {
        return assignParkingId;
    }

    public void setAssignParkingId(String assignParkingId) {
        this.assignParkingId = assignParkingId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Object modifiedBy) {
        this.modifiedBy = modifiedBy;
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
