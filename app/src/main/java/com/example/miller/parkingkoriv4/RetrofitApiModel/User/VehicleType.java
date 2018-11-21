package com.example.miller.parkingkoriv4.RetrofitApiModel.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleType {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("type_name")
    @Expose
    private String typeName;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public VehicleType() {
    }

    /**
     * @param updatedAt
     * @param typeName
     * @param id
     * @param createdBy
     * @param createdAt
     * @param modifiedBy
     * @param typeId
     * @param clientId
     */
    public VehicleType(Integer id, Integer clientId, String typeId, String typeName, String createdBy, String modifiedBy, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.clientId = clientId;
        this.typeId = typeId;
        this.typeName = typeName;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
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
