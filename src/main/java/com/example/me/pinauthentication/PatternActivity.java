package com.example.me.pinauthentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class PatternActivity extends AppCompatActivity {
    Button button_create_Pattern;
    Button button_enter_Pattern;
    Date startOrientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        button_create_Pattern=(Button) findViewById(R.id.button_create_Pattern);
        button_enter_Pattern=(Button) findViewById(R.id.button_enter_Pattern);
        button_enter_Pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOrientation = new Date();
                SharedPreferences settings=getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor= settings.edit();
                editor.putLong("startOrientation",startOrientation.getTime());
                editor.apply();
                Log.d("StartOrientationfirst", "onStarted: " + startOrientation);
                // Enter Pattern
                Intent intent= new Intent(getApplicationContext(), EnterPattern.class);
                startActivity(intent);
                finish();

            }
        });
        button_create_Pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Pattern
                Intent intent= new Intent(getApplicationContext(), CreatePattern.class);
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
