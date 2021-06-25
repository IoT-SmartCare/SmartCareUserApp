package com.example.smartcareuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_activity extends AppCompatActivity {

    private static int splash_time_out=2200;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        sharedPreference = SharedPreference.getPreferences(Splash_activity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!sharedPreference.getData().equals("none"))
                {
                    Intent intent=new Intent(Splash_activity.this,UserDetailsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {

                    Intent intent=new Intent(Splash_activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        },splash_time_out);
    }

}