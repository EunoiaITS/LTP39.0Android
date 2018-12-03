package com.example.miller.parkingkoriv4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import com.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Stats.Stats;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn.VipCheckIn;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn.VipCheckInResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut.VipCheckOut;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut.VipCheckOutResponse;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanVipBarcode extends AppCompatActivity {


    TextView toolTitle;
    private DrawerLayout mDrawerLayout;
    private ApiInterface apiInterface;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_vip_barcode);

        navigationFunction();
        progress = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);

    }


    public void navigationFunction() {

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawerLayout = findViewById(R.id.drawer_layout);


        SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String client_name = userData.getString("client_name", "");
        String emp_name = userData.getString("emp_name", "");
        toolTitle = findViewById(R.id.toolbar_title);
        toolTitle.setText(client_name);


        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        TextView navHeader = hView.findViewById(R.id.company_name);
        navHeader.setText(client_name);

        TextView empHeader = hView.findViewById(R.id.emp_name);
        empHeader.setText(emp_name);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {

                            case R.id.report:
                                getStats();
                                break;
                            case R.id.info:
                                infoAlert();
                                break;
                            case R.id.nav_logout:
                                logoutApp();
                                break;
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void infoAlert() {
        AlertDialog.Builder infoDialogue = new AlertDialog.Builder(ScanVipBarcode.this);
        View infoAlert = getLayoutInflater().inflate(R.layout.info_dialog, null);

        infoDialogue.setView(infoAlert);
        final AlertDialog dialogue = infoDialogue.create();
        dialogue.show();


        final Button end = infoAlert.findViewById(R.id.end_info_dialog);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogue.dismiss();
            }
        });
    }

    public void logoutApp() {
        //remove user data
        SharedPreferences userData = getSharedPreferences("userDetails", MODE_PRIVATE);
        SharedPreferences.Editor userDataEditor = userData.edit();
        userDataEditor.clear();
        userDataEditor.commit();

        Intent logoutIntent = new Intent(this, LoginActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logoutIntent);
    }

    //vipcheckin
    public void scanBarcode(View view) {
        Intent intent = new Intent(ScanVipBarcode.this, QRReader.class);
        startActivityForResult(intent, 0);
    }

    public void scanVipCheckOut(View view) {
        Intent intent = new Intent(ScanVipBarcode.this, QRReader.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(QRReader.BarcodeObject);
                    //Log.d("Code Return Text ", qrData);
                    progress.setTitle("Please wait.......");
                    progress.show();
                    checkInVip(String.valueOf(barcode.displayValue));


                }
            }
        } else if (requestCode == 1) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(QRReader.BarcodeObject);
                    //Log.d("Code Return Text ", qrData);
                    progress.setTitle("Please wait.......");
                    progress.show();
                    checkOutVIP(barcode.displayValue);
                } else {
                    Toast.makeText(ScanVipBarcode.this, "No Check Out barcode found!!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void checkInVip(String vipID) {

        SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String client_id = userData.getString("client_id", "");
        String emp_id = String.valueOf(userData.getInt("emp_id", 0));
        String get_token = userData.getString("token", "");

        VipCheckIn checkInVehicle = new VipCheckIn(vipID, client_id, emp_id, get_token);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<VipCheckInResponse> call = apiInterface.vipcheckedIn(checkInVehicle);

        call.enqueue(new Callback<VipCheckInResponse>() {
            @Override
            public void onResponse(Call<VipCheckInResponse> call, Response<VipCheckInResponse> response) {
                if (response.isSuccessful()) {
                    progress.hide();

                    VipCheckIn check_in_data = response.body().getData();

                    AlertDialog.Builder checkInDialogue = new AlertDialog.Builder(ScanVipBarcode.this);
                    View checkOutReceipt = getLayoutInflater().inflate(R.layout.vip_alert, null);

                    TextView clientName = checkOutReceipt.findViewById(R.id.vip_alert_title);
                    clientName.setText(check_in_data.getVipId());

                    TextView receiptText = checkOutReceipt.findViewById(R.id.textView15);
                    receiptText.setText(response.message());

                    checkInDialogue.setView(checkOutReceipt);
                    final AlertDialog dialogue = checkInDialogue.create();
                    dialogue.show();

                    final Button printCheckOut = checkOutReceipt.findViewById(R.id.dismiss_dialog);
                    printCheckOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogue.dismiss();
                            ScanVipBarcode.this.finish();
                        }
                    });
                } else {
                    progress.hide();
                    Toast.makeText(ScanVipBarcode.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipCheckInResponse> call, Throwable t) {
                progress.hide();
                Toast.makeText(ScanVipBarcode.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkOutVIP(String vip_id) {


        SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String client_id = userData.getString("client_id", "");
        String emp_id = String.valueOf(userData.getInt("emp_id", 0));
        String get_token = userData.getString("token", "");
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        VipCheckOut checkoutVehicle = new VipCheckOut(vip_id, emp_id, currentTimestamp.toString(), client_id, get_token);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<VipCheckOutResponse> call = apiInterface.vipcheckedOut(checkoutVehicle);

        call.enqueue(new Callback<VipCheckOutResponse>() {
            @Override
            public void onResponse(Call<VipCheckOutResponse> call, Response<VipCheckOutResponse> response) {
                if (response.isSuccessful()) {
                    progress.hide();

                    VipCheckOut check_out_data = response.body().getData();
                    String respon = response.body().getMessage();

                    AlertDialog.Builder checkInDialogue = new AlertDialog.Builder(ScanVipBarcode.this);
                    View checkOutReceipt = getLayoutInflater().inflate(R.layout.vip_alert, null);

                    TextView clientName = checkOutReceipt.findViewById(R.id.vip_alert_title);
                    clientName.setText(check_out_data.getVipId());

                    TextView receiptText = checkOutReceipt.findViewById(R.id.textView15);
                    receiptText.setText(respon);


                    checkInDialogue.setView(checkOutReceipt);
                    final AlertDialog dialogue = checkInDialogue.create();
                    dialogue.show();

                    final Button printCheckOut = checkOutReceipt.findViewById(R.id.dismiss_dialog);
                    printCheckOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogue.dismiss();
                        }
                    });
                } else {
                    progress.hide();
                    Toast.makeText(ScanVipBarcode.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipCheckOutResponse> call, Throwable t) {
                progress.hide();
                Toast.makeText(ScanVipBarcode.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getStats() {

        SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String get_token = userData.getString("token", "");

        Stats getStatus = new Stats(get_token);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Stats> call = apiInterface.getStats(getStatus);

        call.enqueue(new Callback<Stats>() {
            @Override
            public void onResponse(Call<Stats> call, Response<Stats> response) {
                if (response.isSuccessful()) {
                    progress.hide();

                    SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                    final String emp_name = userData.getString("emp_name", "");

                    AlertDialog.Builder reportDialog = new AlertDialog.Builder(ScanVipBarcode.this);
                    View reportData = getLayoutInflater().inflate(R.layout.employee_report, null);

                    TextView eName = reportData.findViewById(R.id.emp_name);
                    eName.setText(emp_name);

                    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                    TextView dateTime = reportData.findViewById(R.id.date_and_time);
                    dateTime.setText(String.valueOf(currentTimestamp));

                    TextView checkIN = reportData.findViewById(R.id.checkIn_count);
                    checkIN.setText(String.valueOf(response.body().getCheckIn()));

                    TextView checkOut = reportData.findViewById(R.id.checkOut_count);
                    checkOut.setText(String.valueOf(response.body().getCheckOut()));

                    TextView earning = reportData.findViewById(R.id.earning_count);
                    earning.setText(String.valueOf(response.body().getIncome()));

                    reportDialog.setView(reportData);
                    final AlertDialog dialogue = reportDialog.create();
                    dialogue.show();

                    final Button end = reportData.findViewById(R.id.end_report_dialog);
                    end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogue.dismiss();
                        }
                    });
                } else {
                    progress.hide();
                    Toast.makeText(ScanVipBarcode.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {
                progress.hide();
                Toast.makeText(ScanVipBarcode.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
