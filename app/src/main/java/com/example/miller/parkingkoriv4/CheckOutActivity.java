package com.example.miller.parkingkoriv4;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
import com.example.miller.parkingkoriv4.RetrofitApiModel.User.UserResponse;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ApiInterface apiInterface;

    EditText ticket_id;
    Button checkOutButton, scanBarcode;
    SurfaceView cameraReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        ticket_id = findViewById(R.id.checkout_regnum_input);


        showNavigator();
        onClickCheckout();
        clickCancel();


    }

    public void scanBarcode (View view){
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 0){
            if (resultCode == CommonStatusCodes.SUCCESS){
                if (data != null){
                    Barcode barcodeData = data.getParcelableExtra("barcode");
                    ticket_id.setText(barcodeData.displayValue);
                }else {
                    //ticket_id.setText("No barcode found");
                    Toast.makeText(CheckOutActivity.this, "No barcode found!!", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    public void showNavigator() {
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

    public void onClickCheckout() {
        checkOutButton = findViewById(R.id.checkout_confirm_button);

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tID = ticket_id.getText().toString();
                SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);

                String emp_id = userData.getString("emp_id", "");

                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

                checkOutVehicle(tID, emp_id, currentTimestamp.toString());

                SharedPreferences outTime = getSharedPreferences("outTime", Context.MODE_PRIVATE);

                SharedPreferences.Editor checkOutTime = outTime.edit();
                checkOutTime.putString("out_time", String.valueOf(currentTimestamp));
                checkOutTime.apply();
            }
        });

    }

    public void checkOutVehicle(String ticketID, String employee, String check_out_at) {

        CheckOut checkoutVehicle = new CheckOut(ticketID, employee, check_out_at);
        SharedPreferences authToken = getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = authToken.getString("token", "");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CheckOutResponse> call = apiInterface.checkedOut("Bearer " + token, checkoutVehicle);

        call.enqueue(new Callback<CheckOutResponse>() {
            @Override
            public void onResponse(Call<CheckOutResponse> call, Response<CheckOutResponse> response) {
                if (response.isSuccessful()) {
                    CheckOut check_out_data = response.body().getData();

                    SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
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
                        }
                    });

                    Button cancelCheckOutPrint = checkOutReceipt.findViewById(R.id.cancel_print_checkout);
                    cancelCheckOutPrint.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogue.cancel();
                        }
                    });
                } else {
                    Toast.makeText(CheckOutActivity.this, "Not login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckOutResponse> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "Not Connected" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void printCheckOut(String clientName, String receiptID, String regNo, String entryAt, String vType, String outTime, String totalFair) {


        BluetoothUtil.connectBlueTooth(CheckOutActivity.this);

        //BluetoothUtil.sendData(ESCUtil.getPrintQRCode(ticketNo, 8, 1));
        String BILL = "";
        BILL = "\n \n "+clientName+"  \n"
                + "Parking Ticket\n ";
        BILL = BILL
                + "-----------------------------------------------\n";

        BILL = BILL + String.format("%1$-10s %2$10s", "Ticket Number", receiptID);
        BILL = BILL + "\n";
        //BILL = BILL
        // + "-----------------------------------------------";
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Registration Number", regNo);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Vehicle Type", vType);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Entry At", entryAt);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Exit At", outTime);
        BILL = BILL + "\n " + String.format("%1$-1s %2$1s", "Total Fair", totalFair);
        BILL = BILL
                + "\n-----------------------------------------------";
        BILL = BILL + "\n\n ";
        BILL = BILL + "\n\n ";

        PrintUtil printThis = new PrintUtil();
        printThis.printByBluTooth(BILL);
    }

    public void clickCancel (){
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

}
