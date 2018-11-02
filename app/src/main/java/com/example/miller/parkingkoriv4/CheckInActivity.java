package com.example.miller.parkingkoriv4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn.CheckIn;
import com.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn.CheckInResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.User.UserResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.User.VehicleType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ApiInterface apiInterface;
    private boolean exit = false;

    Spinner typeSpinner;

    Button vehicleCheckIn;
    EditText regNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);



        showNavigator();

        onClickCheckIn();

        onCancelCheckIn();

        clickCancel();

    }

    public void showNavigator(){
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
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

                        return true;
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toobar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.user_profile:
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
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void onCancelCheckIn(){
        Button cancelCheckin = findViewById(R.id.checkin_cancel_button);
        cancelCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodashboard = new Intent(CheckInActivity.this, DashboardActivity.class);
                startActivity(gotodashboard);
            }
        });
    }

    public void onClickCheckIn (){

        regNum = findViewById(R.id.checkin_regnum__input);
        vehicleCheckIn = findViewById(R.id.checkin_confirm_button);

        getVehicleType();

        SharedPreferences vehicleData = getSharedPreferences("vehicleData", Context.MODE_PRIVATE);
        String names = vehicleData.getString("vehicle_type_name", "");
        String[] singleName = names.split(",");
        typeSpinner = findViewById(R.id.vehicle_type_spinner);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < singleName.length; i ++){
            list.add(singleName[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        typeSpinner.setAdapter(adapter);


        vehicleCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spinner
                SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
                String client_id = userData.getString("client_id", "");
                String emp_id = userData.getString("emp_id", "");

                //java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

                //String type = typeSpinner.getSelectedItem().toString();
                String typeID = String.valueOf(typeSpinner.getSelectedItemPosition() + 1);
                String reg_no = regNum.getText().toString();

                checkInVehicle(client_id, reg_no, typeID, emp_id);
                }
        });



    }

    public void checkInVehicle (String clientID, String reg, String type, final String created_by){

        CheckIn checkInVehicle = new CheckIn (clientID, reg, type, created_by);
        SharedPreferences authToken = getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = authToken.getString("token", "");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CheckInResponse> call = apiInterface.checkedIn("Bearer " + token, checkInVehicle);

        call.enqueue(new Callback<CheckInResponse>() {
            @Override
            public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
                if (response.isSuccessful()) {
                    CheckIn check_in_data = response.body().getData();

                    SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
                    final String client_name = userData.getString("client_name", "");

                    final String ticID = check_in_data.getTicketId();
                    final String regNo = check_in_data.getVehicleReg();
                    final String inTime = check_in_data.getCreatedAt();
                    final String vType = check_in_data.getVehicleType();

                    Toast.makeText(CheckInActivity.this, ticID, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder checkInDialogue = new AlertDialog.Builder(CheckInActivity.this);
                    View checkInTicket = getLayoutInflater().inflate(R.layout.check_in_ticket, null);

                    TextView clientName = checkInTicket.findViewById(R.id.ticket_title);
                    clientName.setText(client_name);

                    TextView tickText = checkInTicket.findViewById(R.id.checkin_print_ticket_number_show);
                    tickText.setText(ticID);

                    TextView regText = checkInTicket.findViewById(R.id.checkin_print_registration_number_show);
                    regText.setText(regNo);

                    TextView createdAt = checkInTicket.findViewById(R.id.checkin_print_entry_at_show);
                    createdAt.setText(inTime);

                    final TextView vty = checkInTicket.findViewById(R.id.checkin_print_vehicle_type_show);
                    vty.setText(vType);

                    checkInDialogue.setView(checkInTicket);
                    final AlertDialog dialogue = checkInDialogue.create();
                    dialogue.show();

                    final Button printCheckIn = checkInTicket.findViewById(R.id.print_checkin_ticket);
                    printCheckIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            printCheckIn(client_name, ticID, regNo, inTime, vType);
                            dialogue.dismiss();
                        }
                    });

                    Button cencelCheckInPrint = checkInTicket.findViewById(R.id.cancel_print_checkin);
                    cencelCheckInPrint.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogue.cancel();
                        }
                    });
                }else{
                    Toast.makeText(CheckInActivity.this, "Please fill in data again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckInResponse> call, Throwable t) {
                Toast.makeText(CheckInActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getVehicleType () {
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

                    for (int i=0; i<vt.size(); i++) {
                        //Log.d("Type", vt.get(i).getTypeName());

                        SharedPreferences vehicleData = getSharedPreferences("vehicleData", Context.MODE_PRIVATE);

                        SharedPreferences.Editor vehicleEditor = vehicleData.edit();

                        vID.append(vt.get(i).getId()).append(",");
                        vName.append(vt.get(i).getTypeName()).append(",");

                        vehicleEditor.putString("vehicle_type_id", vID.toString());
                        vehicleEditor.putString("vehicle_type_name", vName.toString());

                        vehicleEditor.apply();
                    }

                }else{
                    //Toast.makeText(CheckInActivity.this, "Not login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse>call, Throwable t) {
                Toast.makeText(CheckInActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void printCheckIn(String client_name, String ticketNo, String regNo, String entryAt, String vType){


        BluetoothUtil.connectBlueTooth(CheckInActivity.this);

        BluetoothUtil.sendData(ESCUtil.getPrintQRCode(ticketNo, 8, 1));
        String BILL = "";
        BILL = "\n \n "+ client_name +"\n"
                + "Parking Ticket\n ";
        BILL = BILL
                + "-----------------------------------------------\n";

        BILL = BILL + String.format("%1$-10s %2$10s", "Ticket Number", ticketNo);
        BILL = BILL + "\n";
        //BILL = BILL
        // + "-----------------------------------------------";
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Registration Number", regNo);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Entry At", entryAt);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Vehicle Type", vType);
        BILL = BILL
                + "\n-----------------------------------------------";
        BILL = BILL + "\n\n ";
        BILL = BILL + "\n\n ";

        PrintUtil printThis = new PrintUtil();
        printThis.printByBluTooth(BILL);
    }

    public void clickCancel (){
        final Button cancel = findViewById(R.id.checkin_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        CheckInActivity.this.finish();
    }
}
