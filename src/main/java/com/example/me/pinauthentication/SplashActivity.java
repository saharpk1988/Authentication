package com.example.me.pinauthentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    //String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*load the password
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password= settings.getString("password",""); */

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*if (password.equals("")) {
                    // if there is no password, load main page after 2000 ms
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();

                } else{
                // if there is a password
                Intent intent=new Intent(getApplicationContext(),EnterPin.class);
                startActivity(intent);
                finish();
            }*/
                // Everytime the Application starts,load main page after 2000 ms
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
