package com.example.me.pinauthentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class LoginSuccessful extends AppCompatActivity {
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AlertDialog.Builder(LoginSuccessful.this).setTitle("Login successful")
                .setMessage("Password entered correctly!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).show();
        //PatternHandler.toastMessageHandler(LoginSuccessful.this, "Password entered correctly!", Toast.LENGTH_LONG, Gravity.BOTTOM,10, 57);
        setContentView(R.layout.activity_login_successful);
        ImageView randomImage= findViewById(R.id.randomImage);
        Button tryAgain= findViewById(R.id.tryAgain);
        Button tryNewMwthod= findViewById(R.id.newMethod);
        //generate random number
        Random randomNumberGenerator = new Random();
        int number= randomNumberGenerator.nextInt(53);
        Log.d("Image", "The Image is: " + number);
        //Make an array list for images
        final int [] randomImages ={ R.drawable.a1,
                R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,
                R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,
                R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,
                R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,
                R.drawable.a18,R.drawable.a19,R.drawable.a20,R.drawable.a21,
                R.drawable.a22,R.drawable.a23,R.drawable.a24,R.drawable.a25,
                R.drawable.a26,R.drawable.a27,R.drawable.a28,R.drawable.a29,
                R.drawable.a30,R.drawable.a31,R.drawable.a32,R.drawable.a33,
                R.drawable.a34,R.drawable.a35,R.drawable.a36,R.drawable.a37,
                R.drawable.a38,R.drawable.a39,R.drawable.a40,R.drawable.a41,
                R.drawable.a42,R.drawable.a43,R.drawable.a44,R.drawable.a45,
                R.drawable.a46,R.drawable.a47,R.drawable.a48,R.drawable.a49,
                R.drawable.a50,R.drawable.a51,R.drawable.a52,R.drawable.a53
        };
        //assign random images to ImageView

        int i=randomImages[number];
        randomImage.setImageResource(i);
        //randomImage.setScaleType(ImageView.ScaleType.FIT_XY);
        //randomImage.setAdjustViewBounds(true);
        //load the password
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        message = settings.getString("message", "");
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.equals("PIN")) {
                    Intent intent = new Intent(getApplicationContext(), PINActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if(message.equals("Pattern")){
                        Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                        startActivity(intent);
                        finish();

                    }else if(message.equals("SwiPIN")) {
                        Intent intent = new Intent(getApplicationContext(), SwiPINActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            }
        });
        tryNewMwthod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();




            }
        });


    }

    //Enable Hardware Back Button
    @Override
    public void onBackPressed() {
        if (message.equals("PIN")) {
            Intent intent = new Intent(getApplicationContext(), PINActivity.class);
            startActivity(intent);
            finish();
        }else {
            if(message.equals("Pattern")){
                Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                startActivity(intent);
                finish();

            }else if(message.equals("SwiPIN")) {
                Intent intent = new Intent(getApplicationContext(), SwiPINActivity.class);
                startActivity(intent);
                finish();

            }
        }
    }
}
