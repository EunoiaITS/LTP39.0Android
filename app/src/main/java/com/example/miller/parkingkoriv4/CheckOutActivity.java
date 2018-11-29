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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miller.parkingkoriv4.PrintUtil.BluetoothUtil;
import com.example.miller.parkingkoriv4.PrintUtil.PrintUtil;
import com.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import com.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import com.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut.CheckOut;
import com.example.miller.parkingkoriv4.RetrofitApiModel.CheckOut.CheckOutResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Stats.Stats;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    EditText ticket_id, vech_reg;
    Button checkOutButton;
    TextView toolTitle;
    private DrawerLayout mDrawerLayout;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        progress = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);

        ticket_id = findViewById(R.id.checkout_regnum_input);
        vech_reg = findViewById(R.id.checkout_reg_num);


        navigationFunction();
        onClickCheckout();
        clickCancel();


    }

    public void scanBarcode(View view) {
        Intent intent = new Intent(CheckOutActivity.this, QRReader.class);
        //intent.putExtra(QRReader.AutoFocus, true);
        //intent.putExtra(QRReader.UseFlash, true);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(QRReader.BarcodeObject);
                    //Log.d("Code Return Text ", qrData);
                    ticket_id.setText(barcode.displayValue);
                } else {
                    //ticket_id.setText("No barcode found");
                    Toast.makeText(CheckOutActivity.this, "No barcode found!!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

    public void onClickCheckout() {
        checkOutButton = findViewById(R.id.checkout_confirm_button);

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tID = ticket_id.getText().toString();
                String vechNo = vech_reg.getText().toString();
                SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                String client_id = userData.getString("client_id", "");
                String emp_id = String.valueOf(userData.getInt("emp_id", 0));
                String get_token = userData.getString("token", "");

                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

                progress.setTitle("Please wait.......");
                progress.show();

                checkOutVehicle(tID, emp_id, currentTimestamp.toString(), get_token, vechNo);

                SharedPreferences outTime = getSharedPreferences("outTime", Context.MODE_PRIVATE);

                SharedPreferences.Editor checkOutTime = outTime.edit();
                checkOutTime.putString("out_time", String.valueOf(currentTimestamp));
                checkOutTime.apply();
            }
        });

    }

    public void checkOutVehicle(final String ticketID, String employee, String check_out_at, String _token, String vech_no) {

        CheckOut checkoutVehicle = new CheckOut(ticketID, employee, check_out_at, _token, vech_no);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CheckOutResponse> call = apiInterface.checkedOut(checkoutVehicle);

        call.enqueue(new Callback<CheckOutResponse>() {
            @Override
            public void onResponse(Call<CheckOutResponse> call, Response<CheckOutResponse> response) {
                if (response.isSuccessful()) {
                    progress.hide();

                    CheckOut check_out_data = response.body().getData();

                    SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                    final String client_name = userData.getString("client_name", "");

                    //final String ticID = check_out_data.getTicketId();
                    final String regNo = check_out_data.getVehicleReg();
                    final String inTime = check_out_data.getCreatedAt();
                    SharedPreferences cot = getSharedPreferences("outTime", Context.MODE_PRIVATE);
                    final String outTime = cot.getString("out_time", "");
                    final String vType = check_out_data.getVehicleType();
                    final String fair = String.valueOf(check_out_data.getFair());
                    final String receipt_id = String.valueOf(check_out_data.getReceiptId());

                    //Toast.makeText(CheckOutActivity.this, ticID, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder checkInDialogue = new AlertDialog.Builder(CheckOutActivity.this);
                    View checkOutReceipt = getLayoutInflater().inflate(R.layout.check_out_ticket, null);


                    TextView clientName = checkOutReceipt.findViewById(R.id.ticket_title);
                    clientName.setText(client_name);

                    TextView receiptText = checkOutReceipt.findViewById(R.id.checkout_print_receipt_number_show);
                    receiptText.setText(receipt_id);

                    TextView regText = checkOutReceipt.findViewById(R.id.checkout_print_registration_number_show);
                    regText.setText(regNo);

                    TextView vty = checkOutReceipt.findViewById(R.id.checkout_print_vehicle_type_show);
                    vty.setText(vType);

                    TextView createdAt = checkOutReceipt.findViewById(R.id.checkout_print_entry_at_show);
                    createdAt.setText(inTime);

                    TextView updatedAt = checkOutReceipt.findViewById(R.id.checkout_print_out_time);
                    updatedAt.setText(outTime);

                    TextView totalFair = checkOutReceipt.findViewById(R.id.checkout_print_fair);
                    totalFair.setText(fair);

                    checkInDialogue.setView(checkOutReceipt);
                    final AlertDialog dialogue = checkInDialogue.create();
                    dialogue.show();

                    final Button printCheckOut = checkOutReceipt.findViewById(R.id.print_checkout_ticket);
                    printCheckOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            printCheckOut(client_name, receipt_id, regNo, inTime, vType, outTime, fair);
                            dialogue.dismiss();
                            ticket_id.setText("");
                            vech_reg.setText("");
                        }
                    });
                } else {
                    progress.hide();
                    Toast.makeText(CheckOutActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckOutResponse> call, Throwable t) {
                progress.hide();
                Toast.makeText(CheckOutActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void printCheckOut(String clientName, String receiptID, String regNo, String entryAt, String vType, String outTime, String totalFair) {

        String x = regNo.substring(0, 2) + "-" + regNo.substring(2, regNo.length());
        String BILL = "";

        BILL = "Product of DEXHUB\n\n";
        BluetoothUtil.connectBlueTooth(CheckOutActivity.this);

        BILL = BILL + "\n \n " + clientName + "  \n"
                + "Parking Receipt\n ";
        BILL = BILL
                + "-----------------------------------------------\n";
        BILL = BILL + String.format("%1$-10s %2$10s", "Ticket Number", receiptID);
        BILL = BILL + "\n";
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Registration No.", x);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Vehicle Type", vType);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Entry At", entryAt);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Exit At", outTime);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Total Fare", totalFair);
        BILL = BILL
                + "\n-----------------------------------------------\n\n";

        BILL = BILL + " +8801631448877 \n";
        BILL = BILL + " info@dexhub.com \n";
        BILL = BILL + "\n\n ";
        BILL = BILL + "\n\n ";
        PrintUtil printThis = new PrintUtil();
        printThis.printByBluTooth(BILL);
    }

    public void clickCancel() {
        final Button cancel = findViewById(R.id.cancel_checkout_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckOutActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        CheckOutActivity.this.finish();
    }

    private void infoAlert() {
        AlertDialog.Builder infoDialogue = new AlertDialog.Builder(CheckOutActivity.this);
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

                    AlertDialog.Builder reportDialog = new AlertDialog.Builder(CheckOutActivity.this);
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
                    Toast.makeText(CheckOutActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {
                progress.hide();
                Toast.makeText(CheckOutActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
