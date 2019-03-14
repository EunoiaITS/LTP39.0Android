package eis.example.miller.parkingkoriv4.RetrofitApiModel.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingRate {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("parking_rate_id")
    @Expose
    private String parkingRateId;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("base_hour")
    @Expose
    private String baseHour;
    @SerializedName("base_rate")
    @Expose
    private String baseRate;
    @SerializedName("sub_rate")
    @Expose
    private String subRate;
    @SerializedName("exmin_id")
    @Expose
    private Object exminId;
    @SerializedName("exhr_id")
    @Expose
    private Object exhrId;
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
    public ParkingRate() {
    }

    /**
     * @param vehicleId
     * @param modifiedBy
     * @param clientId
     * @param exminId
     * @param updatedAt
     * @param id
     * @param baseHour
     * @param createdBy
     * @param createdAt
     * @param baseRate
     * @param subRate
     * @param exhrId
     * @param parkingRateId
     */
    public ParkingRate(Integer id, String clientId, String parkingRateId, String vehicleId, String baseHour, String baseRate, String subRate, Object exminId, Object exhrId, Object createdBy, Object modifiedBy, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.clientId = clientId;
        this.parkingRateId = parkingRateId;
        this.vehicleId = vehicleId;
        this.baseHour = baseHour;
        this.baseRate = baseRate;
        this.subRate = subRate;
        this.exminId = exminId;
        this.exhrId = exhrId;
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

    public String getParkingRateId() {
        return parkingRateId;
    }

    public void setParkingRateId(String parkingRateId) {
        this.parkingRateId = parkingRateId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBaseHour() {
        return baseHour;
    }

    public void setBaseHour(String baseHour) {
        this.baseHour = baseHour;
    }

    public String getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(String baseRate) {
        this.baseRate = baseRate;
    }

    public String getSubRate() {
        return subRate;
    }

    public void setSubRate(String subRate) {
        this.subRate = subRate;
    }

    public Object getExminId() {
        return exminId;
    }

    public void setExminId(Object exminId) {
        this.exminId = exminId;
    }

    public Object getExhrId() {
        return exhrId;
    }

    public void setExhrId(Object exhrId) {
        this.exhrId = exhrId;
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
