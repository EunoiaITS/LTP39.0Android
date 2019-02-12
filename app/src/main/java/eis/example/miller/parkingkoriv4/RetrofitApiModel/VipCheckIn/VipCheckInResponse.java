package eis.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VipCheckInResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private VipCheckIn data;

    /**
     * No args constructor for use in serialization
     */
    public VipCheckInResponse() {
    }

    /**
     * @param message
     * @param status
     * @param data
     */
    public VipCheckInResponse(String status, String message, VipCheckIn data) {
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

    public VipCheckIn getData() {
        return data;
    }

    public void setData(VipCheckIn data) {
        this.data = data;
    }
}
