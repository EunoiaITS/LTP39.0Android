package com.example.miller.parkingkoriv4.RetrofitApiInterface;

import com.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn.CheckIn;
import com.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn.CheckInResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut.CheckOut;
import com.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut.CheckOutResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginEmployee;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.User.UserResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VIPRegistration.VIPRequest;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VIPRegistration.VIPRequestResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn.VipCheckIn;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn.VipCheckInResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut.VipCheckOut;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut.VipCheckOutResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login")
    Call<LoginResponse> loginEmployee(@Body LoginEmployee loginEmployee);

    @GET("user")
    Call <UserResponse> getData(@Header("Authorization") String authToken);


    @POST("check-in")
    Call<CheckInResponse> checkedIn (@Header("Authorization") String authToken, @Body CheckIn checkInVehicle);

    @POST("check-out")
    Call<CheckOutResponse> checkedOut (@Header("Authorization") String authToken, @Body CheckOut checkOutVehicle );

    @POST("vip-request")
    Call<VIPRequestResponse> vipRequest (@Header("Authorization") String authToken, @Body VIPRequest vipRegistrationRequest );

    @POST("vip-check-in")
    Call<VipCheckInResponse> vipcheckedIn (@Header("Authorization") String authToken, @Body VipCheckIn VipCheckInVehicle);

    @POST("vip-check-out")
    Call<VipCheckOutResponse> vipcheckedOut (@Header("Authorization") String authToken, @Body VipCheckOut VipCheckOutVehicle);

}
