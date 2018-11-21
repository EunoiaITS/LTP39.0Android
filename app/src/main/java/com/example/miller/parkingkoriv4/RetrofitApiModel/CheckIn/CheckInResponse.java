package com.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckInResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private CheckIn data;

    /**
     * No args constructor for use in serialization
     */
    public CheckInResponse() {
    }

    /**
     * @param message
     * @param status
     * @param data
     */
    public CheckInResponse(String status, String message, CheckIn data) {
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

    public CheckIn getData() {
        return data;
    }

    public void setData(CheckIn data) {
        this.data = data;
    }
}
