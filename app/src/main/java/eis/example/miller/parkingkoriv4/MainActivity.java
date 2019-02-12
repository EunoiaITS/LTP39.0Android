package eis.example.miller.parkingkoriv4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.miller.parkingkoriv4.R;

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

                SharedPreferences userData = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                String get_token = userData.getString("token", "");

                if (get_token != "") {
                    activityIntent = new Intent(MainActivity.this, DashboardActivity.class);
                } else {
                    activityIntent = new Intent(MainActivity.this, LoginActivity.class);
                }
                startActivity(activityIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
