package eis.example.miller.parkingkoriv4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miller.parkingkoriv4.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eis.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import eis.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginEmployee;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginResponse;
import eis.example.miller.parkingkoriv4.RetrofitApiModel.User.VehicleType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    public boolean login_success;
    ArrayList<String> listEmails;
    private EditText userPass;
    private AutoCompleteTextView userEmail;
    private ProgressDialog progress;

    public static void saveToPrefs(Context context, ArrayList<String> listEmail) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String list = gson.toJson(listEmail);
        prefsEditor.putString("list", list);
        prefsEditor.commit();
    }


    public static ArrayList<String> getFromPrefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String list = appSharedPrefs.getString("list", "");
        ArrayList<String> listEmail = gson.fromJson(list, ArrayList.class);
        return listEmail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = findViewById(R.id.login_button);

        userEmail = findViewById(R.id.username_input);
        userPass = findViewById(R.id.password_input);
        progress = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String pass = userPass.getText().toString();
                if (email != null && pass != null) {
                    clickLogin(email, pass);
                }
                progress.setTitle("Please wait for server to log you in");
                progress.show();
            }
        });


        if (getFromPrefs(LoginActivity.this) != null) {
            String pattern = "]" + ", " + "\\[";
            String email = getFromPrefs(LoginActivity.this).toString();
            String trim_email = email.substring(2, email.length() - 2);
            String[] splitEmail = trim_email.split(pattern);


            for (int i = 0; i < splitEmail.length; i++) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (this, android.R.layout.select_dialog_item, splitEmail);

                userEmail.setThreshold(1);
                userEmail.setAdapter(adapter);
            }
            Log.i("MainActivity", "onCreate: " + getFromPrefs(this).toString());
        }

    }

    public void clickLogin(String email, String password) {

        final LoginEmployee loginEmployee = new LoginEmployee(email, password);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.loginEmployee(loginEmployee);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {

                    String status = response.body().getStatus();
                    progress.hide();
                    if (status.equals("true")) {
                        SharedPreferences userDetails = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = userDetails.edit();
                        editor.putString("token", response.body().getUser().getApiToken());
                        editor.putString("emp_email", response.body().getUser().getDetails().getEmail());
                        editor.putString("emp_search_id", response.body().getUser().getDetails().getEmployeeId());
                        editor.putInt("emp_id", response.body().getUser().getId());
                        editor.putString("emp_name", response.body().getUser().getName());
                        editor.putString("client_id", response.body().getUser().getDetails().getClientId());
                        editor.putString("client_name", response.body().getUser().getClient().getName());
                        editor.apply();

                        StringBuilder vID = new StringBuilder();
                        StringBuilder vName = new StringBuilder();
                        StringBuilder vCount = new StringBuilder();

                        List<VehicleType> vt = response.body().getUser().getVehicleTypes();
                        for (int i = 0; i < vt.size(); i++) {
                            SharedPreferences vehicleData = getSharedPreferences("vehicleData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor vehicleEditor = vehicleData.edit();

                            vID.append(vt.get(i).getId()).append(",");
                            vName.append(vt.get(i).getTypeName()).append(",");
                            vCount.append(vt.get(i).getParkingSetting().getAmount()).append(",");


                            vehicleEditor.putString("vehicle_type_id", vID.toString());
                            vehicleEditor.putString(vID.toString(), vName.toString());
                            vehicleEditor.putString("vehicle_count", vCount.toString());

                            vehicleEditor.apply();
                        }

                        String emp_email = String.valueOf(Collections.singleton(response.body().getUser().getDetails().getEmail()));
                        listEmails = getFromPrefs(LoginActivity.this);
                        if (listEmails == null) {
                            listEmails = new ArrayList<>();
                            listEmails.add(emp_email);
                        } else {
                            if (!listEmails.contains(emp_email)) {
                                listEmails.add(emp_email);
                            }
                        }
                        saveToPrefs(LoginActivity.this, listEmails);


                        login_success = true;
                        Intent dashboard = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(dashboard);

                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong User Email or Password", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    progress.hide();
                    Toast.makeText(LoginActivity.this, "User Does Not Exist!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progress.hide();
                Toast.makeText(LoginActivity.this, "Network error, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CancelApp(View view) {
        LoginActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        LoginActivity.this.finish();
    }
}
