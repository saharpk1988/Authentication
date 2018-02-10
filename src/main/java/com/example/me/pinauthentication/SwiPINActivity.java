package com.example.me.pinauthentication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.Date;

public class SwiPINActivity extends AppCompatActivity {

    Button button_create_SwiPIN;
    Button button_enter_SwiPIN;
    public static Date startOrientation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swi_pin);
        button_create_SwiPIN=(Button) findViewById(R.id.button_create_SwiPIN);
        button_enter_SwiPIN=(Button) findViewById(R.id.button_enter_SwiPIN);
        button_enter_SwiPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOrientation = new Date();
                Log.d("FlingstartOrientation1", "onStarted: " + startOrientation.getTime());
                // Enter SwiPIN

                Intent intent= new Intent(getApplicationContext(), EnterSwiPIN.class);
                startActivity(intent);
                finish();


            }
        });
        button_create_SwiPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create SwiPIN
                Intent intent= new Intent(getApplicationContext(), CreateSwiPIN.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //Enable Hardware Back Button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
