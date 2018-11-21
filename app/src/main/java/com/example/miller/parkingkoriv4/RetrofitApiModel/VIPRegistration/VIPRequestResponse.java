package com.example.miller.parkingkoriv4.RetrofitApiModel.VIPRegistration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VIPRequestResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private VIPRequest data;

    /**
     * No args constructor for use in serialization
     */
    public VIPRequestResponse() {
    }

    /**
     * @param message
     * @param status
     * @param data
     */
    public VIPRequestResponse(String status, String message, VIPRequest data) {
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

    public VIPRequest getData() {
        return data;
    }

    public void setData(VIPRequest data) {
        this.data = data;
    }
}
