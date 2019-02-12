package eis.example.miller.parkingkoriv4.RetrofitApiModel.Reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("report")
    @Expose
    private List<Report> report;

    /**
     * No args constructor for use in serialization
     */
    public ReportResponse() {
    }

    /**
     * @param status
     * @param report
     */
    public ReportResponse(String status, List<Report> report) {
        super();
        this.status = status;
        this.report = report;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Report> getReport() {
        return report;
    }

    public void setReport(List<Report> report) {
        this.report = report;
    }
}
