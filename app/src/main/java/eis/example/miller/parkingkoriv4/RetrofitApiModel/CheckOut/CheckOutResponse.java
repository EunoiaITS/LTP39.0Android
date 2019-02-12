package eis.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOutResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private CheckOut data;

    /**
     * No args constructor for use in serialization
     */
    public CheckOutResponse() {
    }

    /**
     * @param message
     * @param status
     * @param data
     */
    public CheckOutResponse(String status, String message, CheckOut data) {
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

    public CheckOut getData() {
        return data;
    }

    public void setData(CheckOut data) {
        this.data = data;
    }
}
