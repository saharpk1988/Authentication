package com.example.me.pinauthentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;

public class MainActivity extends AppCompatActivity {

    Button PIN;
    Button Pattern;
    Button SwiPIN;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        PIN = (Button) findViewById(R.id.PIN);
        Pattern = (Button) findViewById(R.id.Pattern);
        SwiPIN = (Button) findViewById(R.id.SwiPIN);
        PIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PINActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SwiPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SwiPINActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start:
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_userid, null);
                mBuilder.setView(mView);
                mBuilder.setCancelable(false);
                final AlertDialog dialog= mBuilder.create();
                dialog.show();
                final EditText userID = (EditText) mView.findViewById(R.id.etUserID);
                final EditText condition = (EditText) mView.findViewById(R.id.etCondition);
                final Button setUserID=(Button) mView.findViewById(R.id.btnUserID);
                setUserID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!userID.getText().toString().isEmpty() &&!condition.getText().toString().isEmpty()){
                            //Log userID to pattern Log File
                            //Logger.patternLog("UserID: " + userID.getText());
                            //Log userID to pin Log File
                           // Logger.writeFile("UserID: " + userID.getText());
                            //Log userID to Swipin Log File
                            //Logger.swipinLog("UserID: " + userID.getText());
                            PatternHandler.toastMessageHandler(MainActivity.this, "User ID and Condition have been set!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);
                            dialog.dismiss();
                            SharedPreferences settings=getSharedPreferences("myUserID",0);
                            SharedPreferences.Editor editorId= settings.edit();
                            editorId.putString("userID",userID.getText().toString());
                            editorId.apply();
                            SharedPreferences settingsCondition=getSharedPreferences("condition",0);
                            SharedPreferences.Editor editorIdC= settingsCondition.edit();
                            editorIdC.putString("condition",condition.getText().toString());
                            editorIdC.apply();

                        }
                        else {
                            PatternHandler.toastMessageHandler(MainActivity.this, "Please set User ID and Condition!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);

                        }

                    }
                });
                break;
        }
        return true;
    }


    //Disable Hardware Back Button
    @Override
    public void onBackPressed() {

    }
}
