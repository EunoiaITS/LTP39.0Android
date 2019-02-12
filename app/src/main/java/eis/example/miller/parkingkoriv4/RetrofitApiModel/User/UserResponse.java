package eis.example.miller.parkingkoriv4.RetrofitApiModel.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     */
    public UserResponse() {
    }

    /**
     * @param user
     */
    public UserResponse(User user) {
        super();
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
