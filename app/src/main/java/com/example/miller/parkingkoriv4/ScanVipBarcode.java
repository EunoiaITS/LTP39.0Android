package com.example.miller.parkingkoriv4;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import com.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn.VipCheckIn;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckIn.VipCheckInResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut.VipCheckOut;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VipCheckOut.VipCheckOutResponse;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanVipBarcode extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_vip_barcode);


        showNavigator();
        //clickCancel();

    }


    public void showNavigator() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String client_name = userData.getString("client_name", "");
        final TextView toolTitle = findViewById(R.id.toolbar_title);
        toolTitle.setText(client_name);


        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
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
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {
                            case R.id.end_shift:
                                Log.d("clicked", "end shift clicked");
                                break;
                            case R.id.report:
                                break;
                            case R.id.info:
                                infoAlert();
                                break;
                            case R.id.nav_logout:
                                logoutApp();
                                break;
                        }
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
        Intent intent = new Intent(this, QRScanActivity.class);
        startActivityForResult(intent, 0);
    }

    public void scanVipCheckOut(View view) {
        Intent intent = new Intent(this, QRScanActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    //Barcode barcodeData = data.getParcelableExtra("barcode");
                    //checkInVip(barcodeData.displayValue);
                    //Toast.makeText(CheckInActivity.this, String.valueOf(barcodeData.displayValue), Toast.LENGTH_SHORT).show();

                    String qrData = data.getExtras().getString("barcode");
                    Log.d("Code Return Text ", qrData);
                    checkInVip(qrData);


                }
            }
        } else if (requestCode == 1) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String qrData = data.getExtras().getString("barcode");
                    Log.d("Code Return Text ", qrData);
                    checkOutVIP(qrData);
                    //Barcode barcodeData = data.getParcelableExtra("barcode");
                    //ticket_id.setText(barcodeData.displayValue);

                    //checkOutVIP(barcodeData.displayValue);
                    //Toast.makeText(CheckOutActivity.this, barcodeData.displayValue, Toast.LENGTH_SHORT).show();
                } else {
                    //ticket_id.setText("No barcode found");
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

        VipCheckIn checkInVehicle = new VipCheckIn(vipID, client_id, emp_id,get_token);

        SharedPreferences authToken = getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = authToken.getString("token", "");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<VipCheckInResponse> call = apiInterface.vipcheckedIn(checkInVehicle);

        call.enqueue(new Callback<VipCheckInResponse>() {
            @Override
            public void onResponse(Call<VipCheckInResponse> call, Response<VipCheckInResponse> response) {
                if (response.isSuccessful()) {
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
                    //Toast.makeText(CheckInActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(CheckInActivity.this, "Please fill in data again!!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ScanVipBarcode.this, "Wrong Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipCheckInResponse> call, Throwable t) {
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
        SharedPreferences authToken = getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = authToken.getString("token", "");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<VipCheckOutResponse> call = apiInterface.vipcheckedOut(checkoutVehicle);

        call.enqueue(new Callback<VipCheckOutResponse>() {
            @Override
            public void onResponse(Call<VipCheckOutResponse> call, Response<VipCheckOutResponse> response) {
                if (response.isSuccessful()) {
                    VipCheckOut check_out_data = response.body().getData();
                    String respon = response.body().getMessage();

                    //Toast.makeText(CheckOutActivity.this, ticID, Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(ScanVipBarcode.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipCheckOutResponse> call, Throwable t) {
                Toast.makeText(ScanVipBarcode.this, "Not Connected" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

/*    public void clickCancel (){
        final Button cancel = findViewById(R.id.checkin_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanVipBarcode.this.finish();
            }
        });
    }*/
}
