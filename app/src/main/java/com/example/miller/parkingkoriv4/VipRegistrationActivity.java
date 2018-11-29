package com.example.miller.parkingkoriv4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miller.parkingkoriv4.PrintUtil.BluetoothUtil;
import com.example.miller.parkingkoriv4.PrintUtil.ESCUtil;
import com.example.miller.parkingkoriv4.PrintUtil.PrintUtil;
import com.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import com.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Stats.Stats;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VIPRegistration.VIPRequest;
import com.example.miller.parkingkoriv4.RetrofitApiModel.VIPRegistration.VIPRequestResponse;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VipRegistrationActivity extends AppCompatActivity {

    EditText username, vreg, phonenumber, vipPurpose, vType;
    Button regApply;
    Spinner typeSpinner;
    TextView toolTitle;
    private DrawerLayout mDrawerLayout;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_registration);
        progress = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);

        navigationFunction();
        formData();
        clickCancel();
    }


    public void formData() {
        username = findViewById(R.id.vip_name_input);
        vreg = findViewById(R.id.vip_vehicle_num_input);
        phonenumber = findViewById(R.id.vip_phone_input);
        vipPurpose = findViewById(R.id.vip_vehicle_purpose_input);

        //navigationFunction();
        typeSpinner = findViewById(R.id.vipvType_spinner);

        SharedPreferences vehicleData = getSharedPreferences("vehicleData", Context.MODE_PRIVATE);
        String ids = vehicleData.getString("vehicle_type_id", "");
        String names = vehicleData.getString("vehicle_type_name", "");

        String[] singleName = names.split(",");
        String[] singleID = ids.split(",");

        ArrayList<String> namelist = new ArrayList<>();

        for (int i = 0; i < singleName.length; i++) {
            namelist.add(singleID[i].concat(" ".concat(singleName[i])));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namelist);
        typeSpinner.setAdapter(adapter);
        regApply = findViewById(R.id.checkin_confirm_button);
        regApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegistrationClick();
            }
        });
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


    public void onRegistrationClick() {


        //vType = findViewById(R.id.vip_vehicle_type_input);

        String vipName = username.getText().toString();
        String vregNum = vreg.getText().toString();
        String phn = phonenumber.getText().toString();
        String purp = vipPurpose.getText().toString();
        //String vT = vType.getText().toString();
        String typeName = String.valueOf(typeSpinner.getSelectedItem());

        progress.setTitle("Please wait.......");
        progress.show();
        RegisterVIP(vipName, vregNum, phn, purp, typeName);
    }


    public void RegisterVIP(String name, String car_reg, String phone, final String purpose, String vTy) {

        String vtypesID = vTy.split(" ")[0];
        //final String vtypesName = vTy.split(" ")[1];

        final SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        final String client_id = userData.getString("client_id", "");
        String emp_id = String.valueOf(userData.getInt("emp_id", 0));
        String get_token = userData.getString("token", "");

        VIPRequest newRequest = new VIPRequest(name, vtypesID, client_id, car_reg, phone, purpose, emp_id, get_token);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<VIPRequestResponse> call = apiInterface.vipRequest(newRequest);

        call.enqueue(new Callback<VIPRequestResponse>() {
            @Override
            public void onResponse(Call<VIPRequestResponse> call, Response<VIPRequestResponse> response) {
                if (response.isSuccessful()) {
                    progress.hide();

                    final VIPRequest vipData = response.body().getData();


                    AlertDialog.Builder vipRegisterDialog = new AlertDialog.Builder(VipRegistrationActivity.this);
                    View vipRegisterTicket = getLayoutInflater().inflate(R.layout.vip_reg_recipt, null);

                    final TextView clientName = vipRegisterTicket.findViewById(R.id.vip_ticket_title);
                    clientName.setText(userData.getString("client_name", ""));
                    final String cn = userData.getString("client_name", "");

                    final TextView vipID = vipRegisterTicket.findViewById(R.id.vip_id_input);
                    vipID.setText(response.body().getData().getVipId());
                    final String vi = response.body().getData().getVipId();

                    final TextView vipName = vipRegisterTicket.findViewById(R.id.vip_name_title_input);
                    vipName.setText(response.body().getData().getName());
                    final String vn = response.body().getData().getName();

                    final TextView vipPhone = vipRegisterTicket.findViewById(R.id.vip_phone_input);
                    vipPhone.setText(response.body().getData().getPhone());
                    final String vp = response.body().getData().getPhone();

                    final TextView vipCarNo = vipRegisterTicket.findViewById(R.id.vip_regNo_input);
                    vipCarNo.setText(response.body().getData().getCarReg());
                    final String vcar = response.body().getData().getCarReg();

                    final TextView vipPurpose = vipRegisterTicket.findViewById(R.id.vip_purpose_input);
                    vipPurpose.setText(response.body().getData().getPurpose());
                    final String vpur = response.body().getData().getPurpose();

                    final TextView dateApply = vipRegisterTicket.findViewById(R.id.vip_application_date_input);
                    dateApply.setText(response.body().getData().getCreatedAt());
                    final String da = response.body().getData().getCreatedAt();

                    vipRegisterDialog.setView(vipRegisterTicket);
                    final AlertDialog dialogue = vipRegisterDialog.create();
                    dialogue.show();

                    final Button printCheckOut = vipRegisterTicket.findViewById(R.id.print_checkout_ticket);
                    printCheckOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            printCheckOut(cn, vp, vcar, vpur, vi, vn, da);
                            dialogue.dismiss();
                            username.setText("");
                            vreg.setText("");
                            phonenumber.setText("");
                            vipPurpose.setText("");
                            VipRegistrationActivity.this.finish();

                        }
                    });

                } else {
                    progress.hide();
                    Toast.makeText(VipRegistrationActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VIPRequestResponse> call, Throwable t) {
                progress.hide();
                Toast.makeText(VipRegistrationActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void printCheckOut(String clientName, String phone, String regNo, String purpose, String vipID, String vipName, String dateApply) {

        String x = regNo.substring(0, 2) + "-" + regNo.substring(2, regNo.length());
        BluetoothUtil.connectBlueTooth(VipRegistrationActivity.this);
        BluetoothUtil.sendData(ESCUtil.getPrintQRCode(vipID, 8, 1));
        String BILL = "";

        BILL = "Product of DEXHUB\n\n";
        BILL = BILL + "\n \n " + clientName + "  \n"
                + "Parking Ticket\n ";
        BILL = BILL
                + "-----------------------------------------------\n";
        BILL = BILL + String.format("%1$-10s %2$10s", "Ticket Number ", vipID);
        BILL = BILL + "\n";
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Name ", vipName);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Phone ", phone);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Car Registration No. ", x);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Purpose  ", purpose);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Entry At ", dateApply);
        BILL = BILL
                + "\n-----------------------------------------------";
        BILL = BILL + "\n\n ";
        BILL = BILL + "\n\n ";
        PrintUtil printThis = new PrintUtil();
        printThis.printByBluTooth(BILL);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toobar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

/*            case R.id.user_profile:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.logout:
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
                return true;*/

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void clickCancel() {
        final Button cancel = findViewById(R.id.cancel_vipReg_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VipRegistrationActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        VipRegistrationActivity.this.finish();
    }

/*    public void navigationFunction() {
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

                        return true;
                    }
                });
    }*/

    private void infoAlert() {
        AlertDialog.Builder infoDialogue = new AlertDialog.Builder(VipRegistrationActivity.this);
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

                    AlertDialog.Builder reportDialog = new AlertDialog.Builder(VipRegistrationActivity.this);
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
                    Toast.makeText(VipRegistrationActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {
                progress.hide();
                Toast.makeText(VipRegistrationActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
