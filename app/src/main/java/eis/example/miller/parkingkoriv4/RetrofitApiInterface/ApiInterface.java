package eis.example.miller.parkingkoriv4.RetrofitApiInterface;

import eis.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn.CheckIn;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn.CheckInResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut.CheckOut;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut.CheckOutResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginEmployee;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Reports.Report;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Reports.ReportResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Stats.Stats;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.VIPRegistration.VIPRequest;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.VIPRegistration.VIPRequestResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn.VipCheckIn;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn.VipCheckInResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut.VipCheckOut;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut.VipCheckOutResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login")
    Call<LoginResponse> loginEmployee(@Body LoginEmployee loginEmployee);

/*    @GET("user")
    Call<UserResponse> getData(@Header("Authorization") String authToken);*/


    @POST("check-in")
    Call<CheckInResponse> checkedIn(@Body CheckIn checkInVehicle);

    @POST("check-out")
    Call<CheckOutResponse> checkedOut(@Body CheckOut checkOutVehicle);

    @POST("vip-request")
    Call<VIPRequestResponse> vipRequest(@Body VIPRequest vipRegistrationRequest);

    @POST("vip-check-in")
    Call<VipCheckInResponse> vipcheckedIn(@Body VipCheckIn VipCheckInVehicle);

    @POST("vip-check-out")
    Call<VipCheckOutResponse> vipcheckedOut(@Body VipCheckOut VipCheckOutVehicle);

    @POST("reports")
    Call<ReportResponse> GetEmployeeReport(@Body Report EmployeeReport);

    @POST("stats")
    Call<Stats> getStats(@Body Stats parkingStats);
}
