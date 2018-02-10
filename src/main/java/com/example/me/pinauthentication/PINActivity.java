package com.example.me.pinauthentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class PINActivity extends AppCompatActivity {

    Button button_create_PIN;
    Button button_enter_PIN;
    Date startOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        button_create_PIN=(Button) findViewById(R.id.button_create_PIN);
        button_enter_PIN=(Button) findViewById(R.id.button_enter_PIN);
        button_enter_PIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enter PIN
                Intent intent= new Intent(getApplicationContext(), EnterPIN.class);
                startActivity(intent);
                finish();
                startOrientation = new Date();
                SharedPreferences settings=getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor= settings.edit();
                editor.putLong("startOrientation",startOrientation.getTime());
                editor.apply();

            }
        });
        button_create_PIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create PIN
                Intent intent= new Intent(getApplicationContext(), CreatePIN.class);
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
