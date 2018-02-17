package com.example.me.pinauthentication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Date;



public class CreatePIN extends AppCompatActivity {
    EditText editText1, editText2;
    Button button;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editorId;
    String UserID;
    Date startOrientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);

        editText1= findViewById(R.id.editText1);
        editText2= findViewById(R.id.editText2);
        button= findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1=editText1.getText().toString();
                String text2=editText2.getText().toString();

                if(text1.equals("")&& text2.equals("")){
                    //there is no password entered
                    new AlertDialog.Builder(CreatePIN.this).setTitle("No password entered")
                            .setMessage("Please try again!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            }).setCancelable(false).show();
                   // PatternHandler.toastMessageHandler(CreatePIN.this, "No password entered!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);

                }

                else {


                    if(text1.length()<4){
                        // The entered passwords are less than 4
                        new AlertDialog.Builder(CreatePIN.this).setTitle("Wrong PIN Input")
                                .setMessage("Password must have 4 numbers!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        editText2.getText().clear();
                                        editText1.getText().clear();
                                        editText1.requestFocus();

                                    }
                                }).setCancelable(false).show();
                        //PatternHandler.toastMessageHandler(CreatePIN.this, "Password must have 4 numbers!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);

                    }
                    else{
                        if(text1.equals(text2)){
                            //load the UserID
                            SharedPreferences setting=getSharedPreferences("myUserID",0);
                            UserID = setting.getString("userID", "");
                            //save the password
                            SharedPreferences settings=getSharedPreferences("pass",0);
                            SharedPreferences.Editor editor= settings.edit();
                            editor.putString("pinpassword",text1);
                            editor.apply();
                            editor.commit();
                            startOrientation = new Date();
                            SharedPreferences set=getSharedPreferences("PREFS",0);
                            SharedPreferences.Editor editorNew= set.edit();
                            editorNew.putLong("startOrientation",startOrientation.getTime());
                            editorNew.apply();
                            Logger.writeFile("Creating Password");
                            //Logger.writeFile("UserID: " + UserID);
                            Logger.writeFile("Password for user " + UserID + " has been created");
                            Logger.writeFile("PIN created is : " + text1);
                            Logger.writeFile("------------------");
                            //enter the Application
                            new AlertDialog.Builder(CreatePIN.this).setTitle("PIN created successfully")
                                    .setMessage("Please enter your PIN!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getApplicationContext(), PINActivity.class);
                                            startActivity(intent);
                                            finish();


                                        }
                                    }).setCancelable(false).show();
                            }
                        else{
                            // The entered passwords do not match
                            new AlertDialog.Builder(CreatePIN.this).setTitle("Passwords do not match")
                                    .setMessage("Please try again!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            editText2.getText().clear();
                                            editText1.getText().clear();
                                            editText1.requestFocus();

                                        }
                                    }).setCancelable(false).show();
                            //PatternHandler.toastMessageHandler(CreatePIN.this, "Passwords do not match!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);

                        }
                    }

                }
            }
        });



    }
    //Enable Hardware Back Button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PINActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
