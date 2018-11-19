package com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VipCheckOutResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private VipCheckOut data;

    /**
     * No args constructor for use in serialization
     *
     */
    public VipCheckOutResponse() {
    }

    /**
     *
     * @param message
     * @param status
     * @param data
     */
    public VipCheckOutResponse(String status, String message, VipCheckOut data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VipCheckOut getData() {
        return data;
    }

    public void setData(VipCheckOut data) {
        this.data = data;
    }
}
