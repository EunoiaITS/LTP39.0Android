package com.example.miller.parkingkoriv4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miller.parkingkoriv4.RetrofitApiHelper.ApiClient;
import com.example.miller.parkingkoriv4.RetrofitApiInterface.ApiInterface;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginEmployee;
import com.example.miller.parkingkoriv4.RetrofitApiModel.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ApiInterface apiInterface;

    private EditText userEmail, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEmail = findViewById(R.id.username_input);
                userPass = findViewById(R.id.password_input);

                String email = userEmail.getText().toString();
                String pass = userPass.getText().toString();

                if (email != null && pass != null){
                    clickLogin(email, pass);
                }
            }
        });
    }

    public void clickLogin (String email, String password){
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

                    //getDataForId("Bearer " + response.body().getToken());

                    Intent dashboard = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(dashboard);

                }else{
                    Toast.makeText(LoginActivity.this, "User Does Not Exist!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "No Network!! : " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CancelApp(View view){
        LoginActivity.this.finish();
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
