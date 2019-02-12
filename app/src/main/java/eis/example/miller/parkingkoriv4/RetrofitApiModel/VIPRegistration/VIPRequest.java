package eis.example.miller.parkingkoriv4.RetrofitApiModel.VIPRegistration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VIPRequest {
    @SerializedName("vipId")
    @Expose
    private String vipId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("car_reg")
    @Expose
    private String carReg;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("requested_by")
    @Expose
    private String requestedBy;
    @SerializedName("status")
    @Expose
    private String status;
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
     */
    public VIPRequest() {
    }

    /**
     * @param id
     * @param updatedAt
     * @param phone
     * @param status
     * @param createdAt
     * @param carReg
     * @param name
     * @param vehicleType
     * @param purpose
     * @param vipId
     * @param clientId
     * @param requestedBy
     */
    public VIPRequest(String vipId, String name, String vehicleType, String clientId, String carReg, String phone, String purpose, String requestedBy, String status, String updatedAt, String createdAt, Integer id) {
        super();
        this.vipId = vipId;
        this.name = name;
        this.vehicleType = vehicleType;
        this.clientId = clientId;
        this.carReg = carReg;
        this.phone = phone;
        this.purpose = purpose;
        this.requestedBy = requestedBy;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.id = id;
    }

    public VIPRequest(String name, String vTy, String client_id, String car_reg, String phone, String purpose, String emp_id, String _token) {
        this.name = name;
        this.vehicleType = vTy;
        this.clientId = client_id;
        this.carReg = car_reg;
        this.phone = phone;
        this.purpose = purpose;
        this.requestedBy = emp_id;
        this._token = _token;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCarReg() {
        return carReg;
    }

    public void setCarReg(String carReg) {
        this.carReg = carReg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
