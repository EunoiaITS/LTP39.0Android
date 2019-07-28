package eis.example.miller.parkingkoriv4;

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

import com.example.miller.parkingkoriv4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import eis.example.miller.parkingkoriv4.PrintUtil.BluetoothUtil;
import eis.example.miller.parkingkoriv4.PrintUtil.ESCUtil;
import eis.example.miller.parkingkoriv4.PrintUtil.PrintUtil;
import eis.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import eis.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn.CheckIn;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.CheckIn.CheckInResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Stats.Stats;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInActivity extends AppCompatActivity {

    Spinner typeSpinner;
    Button vehicleCheckIn;
    EditText regNum;
    TextView toolTitle;
    private DrawerLayout mDrawerLayout;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        progress = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);

        navigationFunction();

        onClickCheckIn();

        onCancelCheckIn();

        clickCancel();

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

    public void onCancelCheckIn() {
        Button cancelCheckin = findViewById(R.id.checkin_cancel_button);
        cancelCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodashboard = new Intent(CheckInActivity.this, DashboardActivity.class);
                startActivity(gotodashboard);
            }
        });
    }


    public void onClickCheckIn() {

        regNum = findViewById(R.id.checkin_regnum__input);
        vehicleCheckIn = findViewById(R.id.checkin_confirm_button);

        SharedPreferences vehicleData = getSharedPreferences("vehicleData", Context.MODE_PRIVATE);
        String ids = vehicleData.getString("vehicle_type_id", "");
        final String names = vehicleData.getString(ids, "");

        String[] singleName = names.split(",");
        final String[] singleID = ids.split(",");

        typeSpinner = findViewById(R.id.vehicle_type_spinner);

        final ArrayList<String> namelist = new ArrayList<>();

        for (int i = 0; i < singleName.length; i++) {
            //namelist.add(singleID[i].concat(" ".concat(singleName[i])));
            namelist.add(singleName[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namelist);
        typeSpinner.setAdapter(adapter);


        vehicleCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spinner
                SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                String client_id = userData.getString("client_id", "");
                String emp_id = String.valueOf(userData.getInt("emp_id", 0));
                String get_token = userData.getString("token", "");
                String typeName = String.valueOf(typeSpinner.getSelectedItem());
                String reg_no = regNum.getText().toString();

                int typeNamePosition = typeSpinner.getSelectedItemPosition();

                String typeId = singleID[typeNamePosition];



                progress.setTitle("Please wait.......");
                progress.show();
                checkInVehicle(client_id, reg_no, typeId, emp_id, get_token);
            }
        });
    }

    public void checkInVehicle(String clientID, String reg, final String type, final String created_by, String get_token) {

        CheckIn checkInVehicle = new CheckIn(clientID, reg, type, created_by, get_token);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CheckInResponse> call = apiInterface.checkedIn(checkInVehicle);

        call.enqueue(new Callback<CheckInResponse>() {
            @Override
            public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
                if (response.isSuccessful()) {

                    progress.hide();

                    if (response.body().getStatus().equals("true")) {


                        CheckIn check_in_data = response.body().getData();


                        SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                        final String client_name = userData.getString("client_name", "");
                        final String ticID = check_in_data.getTicketId();
                        final String regNo = check_in_data.getVehicleReg();
                        final String inTime = check_in_data.getCreatedAt();
                        final String vType = check_in_data.getVehicleType();

                        //String vTName;

                        SharedPreferences vehicleData = getSharedPreferences("vehicleData", Context.MODE_PRIVATE);
                        String ids = vehicleData.getString("vehicle_type_id", "");
                        String names = vehicleData.getString(ids, "");

                        String[] singleName = names.split(",");
                        String[] singleID = ids.split(",");

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

                        final int index = Arrays.asList(singleID).indexOf(type);
                        final TextView vty = checkInTicket.findViewById(R.id.checkin_print_vehicle_type_show);
                        final String vtypesName = singleName[index];
                        vty.setText(vtypesName);


                        checkInDialogue.setView(checkInTicket);
                        final AlertDialog dialogue = checkInDialogue.create();
                        dialogue.show();

                        final Button printCheckIn = checkInTicket.findViewById(R.id.print_checkin_ticket);
                        printCheckIn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                printCheckIn(client_name, ticID, regNo, inTime, vtypesName);
                                dialogue.dismiss();
                                CheckInActivity.this.finish();
                            }
                        });
                    } else {
                        Toast.makeText(CheckInActivity.this, "Please fill in data again!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progress.hide();
                    Toast.makeText(CheckInActivity.this, "Please fill in data again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckInResponse> call, Throwable t) {
                progress.hide();
                Toast.makeText(CheckInActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void printCheckIn(String client_name, String ticketNo, String regNo, String entryAt, String vType) {

        String x = regNo.substring(0, 2) + "-" + regNo.substring(2);

        BluetoothUtil.connectBlueTooth(CheckInActivity.this);
        String BILL = "";
        BILL = "\n \n Product of DEXHUB";
        BluetoothUtil.sendData(ESCUtil.getPrintQRCode(ticketNo, 8, 1));

        BILL = BILL + "\n\n" + client_name;
        BILL = BILL + "\n Parking Ticket \n ";
        BILL = BILL
                + "-----------------------------------------------\n";

        BILL = BILL + String.format("%1$-10s %2$10s", "Ticket No.", ticketNo);
        BILL = BILL + "\n";
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Registration No.", x);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Entry At", entryAt);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Vehicle Type", vType);
        BILL = BILL
                + "\n-----------------------------------------------\n";
        BILL = BILL + " +8801631448877 \n";
        BILL = BILL + " info@dexhub.com \n";
        BILL = BILL + "\n\n ";
        BILL = BILL + "\n\n ";

        PrintUtil printThis = new PrintUtil();
        printThis.printByBluTooth(BILL);
    }

    public void clickCancel() {
        final Button cancel = findViewById(R.id.checkin_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInActivity.this.finish();
            }
        });
    }

    private void infoAlert() {
        AlertDialog.Builder infoDialogue = new AlertDialog.Builder(CheckInActivity.this);
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

                    AlertDialog.Builder reportDialog = new AlertDialog.Builder(CheckInActivity.this);
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
                    Toast.makeText(CheckInActivity.this, "Response delay. Please wait.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {
                progress.hide();
                Toast.makeText(CheckInActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
