package eis.example.miller.parkingkoriv4.RetrofitApiModel.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eis.example.miller.parkingkoriv4.RetrofitApiModel.User.User;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private User user;

    public LoginResponse(User user, String message, String success) {
        this.user = user;
        this.message = message;
        this.status = success;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
