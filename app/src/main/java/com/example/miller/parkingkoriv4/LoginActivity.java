package com.example.miller.parkingkoriv4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import com.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginEmployee;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginResponse;
import com.example.miller.parkingkoriv4.RetrofitApiModel.User.UserResponse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    ArrayList<String> userList = new ArrayList<>();
    private ApiInterface apiInterface;
    private EditText userPass;
    private AutoCompleteTextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = findViewById(R.id.login_button);

        userEmail = findViewById(R.id.username_input);
        userPass = findViewById(R.id.password_input);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String pass = userPass.getText().toString();
                if (email != null && pass != null) {
                    clickLogin(email, pass);
                }
            }
        });


        FileInputStream fis = null;
        try {
            fis = openFileInput("save.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
                String[] sep = text.split(",");
                for (int i = 0; i < sep.length; i++) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (this, android.R.layout.select_dialog_item, sep);

                    userEmail.setThreshold(1);
                    userEmail.setAdapter(adapter);
                }
                //Toast.makeText(LoginActivity.this, sep[1], Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void clickLogin(String email, String password) {
        LoginEmployee loginEmployee = new LoginEmployee(email, password);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.loginEmployee(loginEmployee);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    SharedPreferences authToken = getSharedPreferences("authToken", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = authToken.edit();
                    editor.putString("token", response.body().getToken());
                    editor.apply();

                    getDataForId();

                    Intent dashboard = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(dashboard);

                } else {
                    Toast.makeText(LoginActivity.this, "User Does Not Exist!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "No Network!! : " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CancelApp(View view) {
        LoginActivity.this.finish();
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

                    String emp_email = response.body().getUser().getEmail();

                    FileOutputStream fos = null;

                    try {
                        fos = openFileOutput("save.txt", MODE_APPEND);
                        fos.write(emp_email.getBytes());
                        fos.write(',');
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }

}
