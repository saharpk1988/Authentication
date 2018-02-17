package com.example.me.pinauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Random;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateSwiPIN extends AppCompatActivity {

    private SwipeGestureCreate swipeGesture;

    float dimensions;

    private static String user = "";
    private String pwd = "";

    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_create_swi_pin2);


        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        dimensions = screenSize.x / 2;
        View w = findViewById(R.id.swipe);
        swipeGesture = new SwipeGestureCreate(CreateSwiPIN.this);
        w.setOnTouchListener(swipeGesture);

        changeGestures("l_");
        changeGestures("r_");
    }
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView pwdText = (TextView) findViewById(R.id.pwd);

        int id = item.getItemId();

        if (id == R.id.mybutton) {
            // delete all entries
            swipeGesture.deleteEntry();
            pwdText.setText("");
        }
        return super.onOptionsItemSelected(item);
    }

    void changeGestures(String field) {
        if (field.equals("l_")) {
            Object[][] gestureArray = { { R.drawable.red_circle, SwipeGestureCreate.detectedGesture.DOT },
                    { R.drawable.red_up, SwipeGestureCreate.detectedGesture.UP },
                    { R.drawable.red_right, SwipeGestureCreate.detectedGesture.RIGHT },
                    { R.drawable.red_down, SwipeGestureCreate.detectedGesture.DOWN },
                    { R.drawable.red_left, SwipeGestureCreate.detectedGesture.LEFT } };
            appleGestures(field, gestureArray);

        } else {
            Object[][] gestureArray = { { R.drawable.yellow_circle, SwipeGestureCreate.detectedGesture.DOT },
                    { R.drawable.yellow_up, SwipeGestureCreate.detectedGesture.UP },
                    { R.drawable.yellow_right, SwipeGestureCreate.detectedGesture.RIGHT },
                    { R.drawable.yellow_down, SwipeGestureCreate.detectedGesture.DOWN },
                    { R.drawable.yellow_left, SwipeGestureCreate.detectedGesture.LEFT } };
            appleGestures(field, gestureArray);
        }
    }

    private void appleGestures(String field, Object[][] gestureArray) {
        int[] a = { 0, 1, 2, 3, 4 };
        a = shuffleArray(a);
        ImageView image;

        for (int i = 0; i <= 4; i++) {
            int id = 0;
            try {
                id = R.id.class.getField(field + i).getInt(0);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            image = (ImageView) findViewById(id);
            image.setImageResource((Integer) gestureArray[a[i]][0]);
            if (image == null)
                Log.d(this.getClass().getName(), "Image is null");
            if (gestureArray == null)
                Log.d(this.getClass().getName(), "Array is null");
            image.setTag(gestureArray[a[i]][1]);
            image.setImageAlpha(255);
        }
    }

    // Fisher Yates shuffle
    protected int[] shuffleArray(int[] a) {
        Random rnd = new Random();
        for (int i = a.length - 1; i >= 1; i--) {
            int j = rnd.nextInt(i + 1);
            ;
            int c = a[j];
            a[j] = a[i];
            a[i] = c;
        }
        return a;
    }

    public void changeVisibility(String field, Boolean visible) {
        ImageView image;

        for (int i = 0; i <= 4; i++) {
            int id = 0;
            try {
                id = R.id.class.getField(field + i).getInt(0);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            image = (ImageView) findViewById(id);
            if (visible)
                image.setImageAlpha(255);
            else
                image.setImageAlpha(0);
        }
    }


    public void pwdEntered(String entered1) {
        //save the password
        SharedPreferences settings=getSharedPreferences("swipin",0);
        SharedPreferences.Editor editor= settings.edit();
        editor.putString("passwordSwiPIN",entered1);
        editor.apply();
        editor.commit();
        pwd=entered1;
        Log.d("password0","entered password "+pwd);
        

        new AlertDialog.Builder(this).setTitle("SwiPIN created successfully")
                .setMessage("Please enter your SwiPIN!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), SwiPINActivity.class);
                        startActivity(intent);
                        finish();
                        swipeGesture.deleteEntry();

                    }
                }).setCancelable(false).show();
    }

    public void findCorrectGesture(String entered, String field) {
        int lastEntered = (Integer.valueOf(entered) % 10);
        int password = Integer.valueOf(pwd);
        int helper = 0;
        while (helper < (4 - entered.length())) {
            password = password / 10;
            helper++;
        }
        int trueNumber = password % 10;
        if (trueNumber != lastEntered) {
            int fieldNumber = -1;
            switch (trueNumber) {
                case 1:
                    fieldNumber = 0;
                    break;
                case 2:
                    fieldNumber = 1;
                    break;
                case 3:
                    fieldNumber = 0;
                    break;
                case 4:
                    fieldNumber = 2;
                    break;
                case 5:
                    fieldNumber = 3;
                    break;
                case 6:
                    fieldNumber = 2;
                    break;
                case 7:
                    fieldNumber = 4;
                    break;
                case 8:
                    fieldNumber = 2;
                    break;
                case 9:
                    fieldNumber = 3;
                    break;
                case 0:
                    fieldNumber = 4;
                    break;
            }
            try {
                id = R.id.class.getField(field + fieldNumber).getInt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ImageView image = (ImageView) findViewById(id);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SwiPINActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

