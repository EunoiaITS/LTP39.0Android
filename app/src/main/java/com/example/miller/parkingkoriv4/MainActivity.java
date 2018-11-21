package com.example.miller.parkingkoriv4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button gotoLogin = findViewById(R.id.goto_login);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activityIntent;


                SharedPreferences authToken = getSharedPreferences("authToken", Context.MODE_PRIVATE);
                String token = authToken.getString("token", "");


                if (token != "") {
                    activityIntent = new Intent(MainActivity.this, DashboardActivity.class);
                } else {
                    activityIntent = new Intent(MainActivity.this, LoginActivity.class);
                }


                startActivity(activityIntent);
                finish();
            }
        });
    }
}
