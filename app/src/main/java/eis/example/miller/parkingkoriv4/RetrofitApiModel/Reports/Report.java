package eis.example.miller.parkingkoriv4.RetrofitApiModel.Reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("type_name")
    @Expose
    private String typeName;
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
    @SerializedName("settings")
    @Expose
    private Object settings;
    @SerializedName("check_in")
    @Expose
    private Integer checkIn;
    @SerializedName("_token")
    @Expose
    private String _token;


    /**
     * No args constructor for use in serialization
     *
     * @param get_token
     * @param client_id
     */
    public Report(String get_token, String client_id) {
        this._token = get_token;
        this.clientId = client_id;
    }

    /**
     * @param updatedAt
     * @param typeName
     * @param id
     * @param createdBy
     * @param createdAt
     * @param modifiedBy
     * @param settings
     * @param typeId
     * @param checkIn
     * @param clientId
     */
    public Report(Integer id, String clientId, String typeId, String typeName, Object createdBy, Object modifiedBy, String createdAt, String updatedAt, Object settings, Integer checkIn, String _token) {
        super();
        this.id = id;
        this.clientId = clientId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.settings = settings;
        this.checkIn = checkIn;
        this._token = _token;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public Object getSettings() {
        return settings;
    }

    public void setSettings(Object settings) {
        this.settings = settings;
    }

    public Integer getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Integer checkIn) {
        this.checkIn = checkIn;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
