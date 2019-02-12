package eis.example.miller.parkingkoriv4.RetrofitApiModel.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public UserDetails() {
    }

    /**
     * @param updatedAt
     * @param employeeId
     * @param id
     * @param phone
     * @param createdBy
     * @param status
     * @param email
     * @param createdAt
     * @param modifiedBy
     * @param name
     * @param password
     * @param clientId
     */
    public UserDetails(int id, String employeeId, String clientId, String password, String name, String email, String phone, String createdBy, String modifiedBy, String status, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.employeeId = employeeId;
        this.clientId = clientId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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
}
