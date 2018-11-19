package com.example.miller.parkingkoriv4;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.example.miller.parkingkoriv4.RetrofitApiModel.User.UserResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.User.VehicleType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    private DrawerLayout mDrawerLayout;
    private ApiInterface apiInterface;
    TextView toolTitle;

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        int i = 0;

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();

        SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String client_name = userData.getString("client_name", "");
        toolTitle = findViewById(R.id.toolbar_title);
        toolTitle.setText(client_name);

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawerLayout = findViewById(R.id.drawer_layout);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(DashboardActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(DashboardActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

            // MY_PERMISSIONS_REQUEST_CAMERA is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        } else {
            // Permission has already been granted
        }


        navigationFunction();
        buttonsClicked();
        getDataForId();
        getVehicleType();


    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toobar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }*/

    public void navigationFunction() {
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
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    private void infoAlert() {
        AlertDialog.Builder infoDialogue = new AlertDialog.Builder(DashboardActivity.this);
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

    public void buttonsClicked() {
        final Button checkInButton = findViewById(R.id.checkin_button);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkInIntent = new Intent(DashboardActivity.this, CheckInActivity.class);
                startActivity(checkInIntent);
            }
        });

        final Button checkOutButton = findViewById(R.id.checkout_button);
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkOutIntent = new Intent(DashboardActivity.this, CheckOutActivity.class);
                startActivity(checkOutIntent);
            }
        });

        final Button scanbarcodeButton = findViewById(R.id.scanbarcode_button);
        scanbarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanbarcodeIntent = new Intent(DashboardActivity.this, ScanVipBarcode.class);
                startActivity(scanbarcodeIntent);
            }
        });

        final Button vipregistrationButton = findViewById(R.id.vip_button);
        vipregistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vipRegistrationIntent = new Intent(DashboardActivity.this, VipRegistrationActivity.class);
                startActivity(vipRegistrationIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            /*case R.id.user_profile:
                // User chose the "Settings" item, show the app settings UI...

                return true;

            case R.id.logout:

                logoutApp();
                return true;*/

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void getDataForId() {

        SharedPreferences authToken = getSharedPreferences("authToken", Context.MODE_PRIVATE);

        String token = authToken.getString("token", "");

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<UserResponse> call = apiInterface.getData("Bearer " + token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {

                    UserResponse empData = response.body();
                    SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);

                    SharedPreferences.Editor userEditor = userData.edit();
                    userEditor.putString("emp_id", String.valueOf(empData.getUser().getId()));
                    userEditor.putString("emp_name", empData.getUser().getName());
                    userEditor.putString("client_id", empData.getUser().getDetails().getClientId());
                    userEditor.putString("client_name", empData.getUser().getClient().getName());
                    userEditor.apply();

                } else {

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (exit)
            DashboardActivity.this.finish();
        else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // camera related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    public void getVehicleType() {
        SharedPreferences authToken = getSharedPreferences("authToken", Context.MODE_PRIVATE);

        String token = authToken.getString("token", "");

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<UserResponse> call = apiInterface.getData("Bearer " + token);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    List<VehicleType> vt = response.body().getUser().getVehicleTypes();

                    StringBuilder vID = new StringBuilder();
                    StringBuilder vName = new StringBuilder();

                    for (int i = 0; i < vt.size(); i++) {
                        //Log.d("Type", vt.get(i).getTypeName());

                        SharedPreferences vehicleData = getSharedPreferences("vehicleData", Context.MODE_PRIVATE);

                        SharedPreferences.Editor vehicleEditor = vehicleData.edit();

                        vID.append(vt.get(i).getId()).append(",");
                        vName.append(vt.get(i).getTypeName()).append(",");


                        vehicleEditor.putString("vehicle_type_id", vID.toString());
                        vehicleEditor.putString("vehicle_type_name", vName.toString());

                        vehicleEditor.apply();
                    }

                } else {
                    //Toast.makeText(CheckInActivity.this, "Not login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void zxingqr(View view){
        Intent logoutIntent = new Intent(this, QRScanActivity.class);
        startActivity(logoutIntent);
    }


    public void logoutApp() {
        //Remove token
        SharedPreferences authToken = getSharedPreferences("authToken", MODE_PRIVATE);
        SharedPreferences.Editor tokenDataEditor = authToken.edit();
        tokenDataEditor.clear();
        tokenDataEditor.commit();

        //remove user data
        SharedPreferences userData = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor userDataEditor = userData.edit();
        userDataEditor.clear();
        userDataEditor.commit();

        Intent logoutIntent = new Intent(this, LoginActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logoutIntent);
    }

}
