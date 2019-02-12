package eis.example.miller.parkingkoriv4.RetrofitApiModel.Stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("check_in")
    @Expose
    private Integer checkIn;
    @SerializedName("check_out")
    @Expose
    private Integer checkOut;
    @SerializedName("income")
    @Expose
    private Integer income;
    @SerializedName("_token")
    @Expose
    private String _token;

    /**
     * No args constructor for use in serialization
     *
     * @param get_token
     */
    public Stats(String get_token) {
        this._token = get_token;
    }

    /**
     * @param checkOut
     * @param status
     * @param income
     * @param checkIn
     */
    public Stats(String status, Integer checkIn, Integer checkOut, Integer income, String _token) {
        super();
        this.status = status;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.income = income;
        this._token = _token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Integer checkIn) {
        this.checkIn = checkIn;
    }

    public Integer getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Integer checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
