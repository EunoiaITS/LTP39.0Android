package eis.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut;

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
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("fair")
    @Expose
    private Integer fair;
    @SerializedName("vehicle_name")
    @Expose
    private String vehicleName;
    @SerializedName("vehicle_base_rate")
    @Expose
    private String vehicleBaseRate;
    @SerializedName("vehicle_sub_rate")
    @Expose
    private String vehicleSubRate;
    @SerializedName("total_hour")
    @Expose
    private Integer totalHour;
    @SerializedName("total_minute")
    @Expose
    private Integer totalMinute;
    @SerializedName("_token")
    @Expose
    private String _token;

    /**
     * No args constructor for use in serialization
     */
    public CheckOut() {
    }

    /**
     * @param vehicleSubRate
     * @param totalHour
     * @param totalMinute
     * @param fair
     * @param clientId
     * @param vehicleReg
     * @param updatedBy
     * @param id
     * @param updatedAt
     * @param ticketId
     * @param createdBy
     * @param vehicleBaseRate
     * @param createdAt
     * @param vehicleName
     * @param vehicleType
     * @param receiptId
     */
    public CheckOut(Integer id, String clientId, String ticketId, String receiptId, String vehicleType, String vehicleReg, String createdBy, String updatedBy, String createdAt, String updatedAt, Integer fair, String vehicleName, String vehicleBaseRate, String vehicleSubRate, Integer totalHour, Integer totalMinute, String _token) {
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
        this.vehicleName = vehicleName;
        this.vehicleBaseRate = vehicleBaseRate;
        this.vehicleSubRate = vehicleSubRate;
        this.totalHour = totalHour;
        this.totalMinute = totalMinute;
        this._token = _token;
    }

    public CheckOut(String ticketID, String employee, String check_out_at, String token, String vech_no) {
        this.ticketId = ticketID;
        this.updatedBy = employee;
        this.updatedAt = check_out_at;
        this._token = token;
        this.vehicleReg = vech_no;


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

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleBaseRate() {
        return vehicleBaseRate;
    }

    public void setVehicleBaseRate(String vehicleBaseRate) {
        this.vehicleBaseRate = vehicleBaseRate;
    }

    public String getVehicleSubRate() {
        return vehicleSubRate;
    }

    public void setVehicleSubRate(String vehicleSubRate) {
        this.vehicleSubRate = vehicleSubRate;
    }

    public Integer getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(Integer totalHour) {
        this.totalHour = totalHour;
    }

    public Integer getTotalMinute() {
        return totalMinute;
    }

    public void setTotalMinute(Integer totalMinute) {
        this.totalMinute = totalMinute;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
