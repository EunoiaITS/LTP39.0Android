package eis.example.miller.parkingkoriv4;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miller.parkingkoriv4.R;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import eis.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import eis.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Reports.Report;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Reports.ReportResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Stats.Stats;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    TextView toolTitle;
    private DrawerLayout mDrawerLayout;
    //private boolean exit = false;
    private ProgressDialog progress;
    private TableLayout reportTable;

    private Timer timer;
    private TimerTask timerTask;

    private static final String TAG = "DashboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        progress = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        reportTable = findViewById(R.id.report_table);

        //Create EditText for parking status dynamically


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

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    //Download file here and refresh
                    getReport();
                }
            };
            timer.schedule(timerTask, 100, 1000);
        } catch (IllegalStateException e) {
            android.util.Log.i("Damn", "resume error");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.timer.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
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
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
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

        }
    }

    public void logoutApp() {
        //remove user data
        SharedPreferences userData = getSharedPreferences("userDetails", MODE_PRIVATE);
        SharedPreferences.Editor userDataEditor = userData.edit();
        userDataEditor.clear();
        userDataEditor.commit();


        Intent logoutIntent = new Intent(DashboardActivity.this, LoginActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logoutIntent);

    }


    public void getReport() {
        SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        final String client_id = userData.getString("client_id", "");
        final String get_token = userData.getString("token", "");

        Report getReport = new Report(get_token, client_id);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.GetEmployeeReport(getReport);

        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful()) {

                    List<Report> showReport = response.body().getReport();
                    int childCount = reportTable.getChildCount();
                    // Remove all rows except the first one
                    if (childCount > 0) {
                        reportTable.removeAllViews();
                    }

                    SharedPreferences vehicleData = getSharedPreferences("vehicleData", Context.MODE_PRIVATE);
                    String ids = vehicleData.getString("vehicle_type_id", "");
                    String vCount = vehicleData.getString("vehicle_count", "");

                    String[] splitCount = vCount.split(",");

                    for (int i = 0; i < showReport.size(); i++) {

                        TableRow row = new TableRow(DashboardActivity.this);

                        TableLayout.LayoutParams tableRowParams =
                                new TableLayout.LayoutParams
                                        (TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                        tableRowParams.setMargins(10, 10, 10, 10);
                        row.setLayoutParams(tableRowParams);

                        for (int j = 0; j < 1; j++) {

                            TextView name = new TextView(DashboardActivity.this);
                            TextView stat = new TextView(DashboardActivity.this);

                            name.setTextColor(Color.RED);
                            name.setTextAppearance(DashboardActivity.this, R.style.CustomFont);
                            name.setTextSize(2, 13);

                            stat.setTextColor(Color.BLACK);
                            stat.setTextSize(2, 20);

                            int eachCount = Integer.parseInt(splitCount[i]) - showReport.get(i).getCheckIn();

                            name.setText("Available Parking for " + showReport.get(i).getTypeName());
                            row.addView(name);

                            stat.setText(":  " + eachCount + "/" + splitCount[i]);
                            row.addView(stat);

                        }
                        reportTable.addView(row);
                    }


                } else {
                    //Toast.makeText(DashboardActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                //Toast.makeText(DashboardActivity.this, "Network error, please try again.", Toast.LENGTH_SHORT).show();

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

                    AlertDialog.Builder reportDialog = new AlertDialog.Builder(DashboardActivity.this);
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
                    Toast.makeText(DashboardActivity.this, "Response delay. Please wait.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {
                progress.hide();
                Toast.makeText(DashboardActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
