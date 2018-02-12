package com.example.me.pinauthentication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EnterPattern extends AppCompatActivity {
    List<Integer> myEnteredList= new ArrayList<Integer>();
    Date startTime;
    Date delete;
    Date now;
    Date endTime;
    String UserID;
    long FirstOrientation;
    Boolean Result;
    int j=4;
    int incorrectInput=0;
    int authentionTry=2;
    Date startOrientation;
    ArrayList<Float> speedDetected = new ArrayList<>();
    private SensorManager sm;
    private float shake; // Acceleration Value differ from Gravity
    Long lastUpdate=0L;
    float last_x, last_y,last_z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load the UserID
        SharedPreferences settings = getSharedPreferences("myUserID", 0);
        UserID = settings.getString("userID", "");
        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        //Gravity_Earth=9.80665f (Objects, when dropped, accelerate at the rate of 9.8 m/s^2 on earth)
        shake=SensorManager.GRAVITY_EARTH;
        setContentView(R.layout.activity_enter_pattern);
        final PatternLockView mPatternLockViewEnter;
        Button enterPattern = findViewById(R.id.btnEnterPattern);
        mPatternLockViewEnter = (PatternLockView) findViewById(R.id.pattern_lock_view_enter);
        mPatternLockViewEnter.addPatternLockListener(new PatternLockViewListener() {

            @Override
            public void onStarted() {
                startTime = new Date();
                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                long startOrientation = settings.getLong("startOrientation", 0L);
                Log.d("Orienttime", "onStarted: "+startOrientation);
                long startEnter = new Date().getTime();
                Log.d("Orienttime0", "onStarted: "+startEnter);
                Logger.patternLog("UserID: " + UserID);
                FirstOrientation = Math.abs(startEnter - startOrientation);
                Logger.patternLog("Pattern entry orientation time: " + FirstOrientation);
                Log.d("Orienttime1", "onStarted: "+FirstOrientation);


            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> patternConfirm) {
                PatternLockUtils.patternToString(mPatternLockViewEnter, patternConfirm);

                myEnteredList = new PatternHandler().patternHandler(patternConfirm);
                Log.d("test5", "Enter Pattern Page: " + myEnteredList);
                endTime = new Date();
            }

            @Override
            public void onCleared() {

            }
        });

        for (int i=1;i<6;i++) {
        enterPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.unregisterListener(sensorListener);
                Log.d("sensor speed detected", "onClick: "+speedDetected);
                float speedAverage=SpeedAverage.average(speedDetected);
                speedDetected.clear();
                Log.d("sensor speedAverage", "onClick: "+speedAverage);
                //List<Integer> myConfirmList = ConfirmPattern.getMyConfirmList();
                TinyDB tinydb = new TinyDB(EnterPattern.this);
                final List<Integer> myConfirmList = tinydb.getListInt("password");
                if (myConfirmList.equals(myEnteredList)) {

                    Logger.patternLog("Pattern entry start time: " + startTime);
                    Logger.patternLog("Pattern entry end time: " + endTime);
                    Logger.patternLog("Total Pattern entry time without Error: " + ((endTime.getTime() - startTime.getTime())+FirstOrientation));
                    Logger.patternLog("Pattern created: " + myConfirmList);
                    Logger.patternLog("Pattern entered: " + myEnteredList);
                    Logger.patternLog("Result is true");
                    //Last Authentication try when entering correct password
                    if(j==0&& incorrectInput!=3){
                        Logger.patternLog("Last Authentication try");
                        Logger.patternLog("Average Speed detected is: "+speedAverage);
                        Logger.patternLog("------------------");
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Correct Pattern")
                                .setMessage("Authentication successful!"+"\n"+"Pattern entered correctly." )
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                                        startActivity(intent);
                                        finish();}
                                }).setCancelable(false).show();


                    } else{
                        Logger.patternLog("Authentication try: "+(j+1));
                        Logger.patternLog("Average Speed detected is: "+speedAverage);
                        Logger.patternLog("------------------");
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Correct Pattern")
                                .setMessage("Authentications to go: " + (j) + "\n" + "See password again?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        new AlertDialog.Builder(EnterPattern.this).setTitle("View Password")
                                                .setMessage("Your Password is: "+"\n"+myConfirmList+"\n"+"\n"+ Arrays.deepToString(ShowPattern.showPat(myConfirmList)).replace("], ", "\n").replace("[, ", "").replace("[[", "").replace("]]", "").replace(",", "").replace("[", ""))
                                                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                                        startOrientation = new Date();
                                                        SharedPreferences settings=getSharedPreferences("PREFS",0);
                                                        SharedPreferences.Editor editor= settings.edit();
                                                        editor.putLong("startOrientation",startOrientation.getTime());
                                                        editor.apply();
                                                        mPatternLockViewEnter.clearPattern();
                                                        myEnteredList.clear();
                                                    }
                                                }).setCancelable(false).show();


                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                        startOrientation = new Date();
                                        SharedPreferences settings=getSharedPreferences("PREFS",0);
                                        SharedPreferences.Editor editor= settings.edit();
                                        editor.putLong("startOrientation",startOrientation.getTime());
                                        editor.apply();
                                        mPatternLockViewEnter.clearPattern();
                                        myEnteredList.clear();
                                    }
                                }).setCancelable(false).show();
                        Log.d("J", "onClick: " + j);
                        j -= 1;
                    Result = true;
                    //Log Pattern CSV File
                    Logger.patternCsv("UserID, Average Motion Shake, Orientation Time, Total Time, Pattern Created, Pattern Entered, Result: " + "\n" + UserID + ", "+speedAverage+", " + FirstOrientation + ", " + (endTime.getTime() - startTime.getTime()) + ", " + myConfirmList + ", " + myEnteredList + ", " + Result);
                    //Logger.patternCsv(UserID +", " + Orientation+", "  + (endTime.getTime() - startTime.getTime())+", " + myConfirmList+", " + myEnteredList+", " + Result);
                    Logger.patternCsv("------------------");


             }
                }else {
                    //Logger.patternLog("UserID: " + counter);
                    Logger.patternLog("Pattern entry start time: " + startTime);
                    Logger.patternLog("Pattern entry end time: " + endTime);
                    Logger.patternLog("Total Pattern entry time with Error: " + ((endTime.getTime() - startTime.getTime())+FirstOrientation));
                    Logger.patternLog("Pattern created: " + myConfirmList);
                    Logger.patternLog("Pattern entered: " + myEnteredList);
                    Logger.patternLog("Result is false");
                     Result = false;
                    //Log Pattern CSV File
                    Logger.patternCsv("UserID, Average Motion Shake, Orientation Time, Total Time, Pattern Created, Pattern Entered, Result: " + "\n" + UserID + ", "+speedAverage+", " + FirstOrientation + ", " + (endTime.getTime() - startTime.getTime()) + ", " + myConfirmList + ", " + myEnteredList + ", " + Result);
                    //Logger.patternCsv(UserID +", " + Orientation+", "  + (endTime.getTime() - startTime.getTime())+", " + myConfirmList+", " + myEnteredList+", " + Result);
                    Logger.patternCsv("------------------");
                    //Wrong password entered three times
                    if(incorrectInput== 2){
                        Logger.patternLog("Authentication try: "+(j+1));
                        Logger.patternLog("Average Speed detected is: "+speedAverage);
                        Logger.patternLog("------------------");
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Wrong Pattern")
                                .setMessage("Remaining tries: "+authentionTry+"\n"+"Incorrect pattern entered three times." +"\n" + "Authentication failed."+"\n"+"Please try again!")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                                        startActivity(intent);
                                        finish();


                                    }
                                }).setCancelable(false).show();
                    }
                    else if(j==0&&incorrectInput!=3){
                        Logger.patternLog("Last Authentication try");
                        Logger.patternLog("Average Speed detected is: "+speedAverage);
                        Logger.patternLog("------------------");
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Wrong Pattern")
                                .setMessage("Authentication series are finished!"+"\n"+"Authentication successful." )
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                                        startActivity(intent);
                                        finish();}
                                }).setCancelable(false).show();


                    }
                    else  {
                        //Authentication try when entering wrong password
                        incorrectInput += 1;
                        Logger.patternLog("Authentication try: "+(j+1));
                        Logger.patternLog("Average Speed detected is: "+speedAverage);
                        Logger.patternLog("------------------");
                        //PatternHandler.toastMessageHandler(EnterPIN.this, "Wrong password!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Wrong Pattern")
                                .setMessage("Authentications to go: " + (j) + "\n" +"Remaining tries: "+authentionTry+ "\n"+ "See password again?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        new AlertDialog.Builder(EnterPattern.this).setTitle("View Password")
                                                .setMessage("Your Password is: "+"\n"+myConfirmList+"\n"+"\n"+ Arrays.deepToString(ShowPattern.showPat(myConfirmList)).replace("], ", "\n").replace("[, ", "").replace("[[", "").replace("]]", "").replace(",", "").replace("[", ""))
                                                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                                        startOrientation = new Date();
                                                        SharedPreferences settings=getSharedPreferences("PREFS",0);
                                                        SharedPreferences.Editor editor= settings.edit();
                                                        editor.putLong("startOrientation",startOrientation.getTime());
                                                        editor.apply();
                                                        mPatternLockViewEnter.clearPattern();
                                                        myEnteredList.clear();
                                                    }
                                                }).setCancelable(false).show();


                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                        startOrientation = new Date();
                                        SharedPreferences settings=getSharedPreferences("PREFS",0);
                                        SharedPreferences.Editor editor= settings.edit();
                                        editor.putLong("startOrientation",startOrientation.getTime());
                                        editor.apply();
                                        mPatternLockViewEnter.clearPattern();
                                        myEnteredList.clear();
                                    }
                                }).setCancelable(false).show();
                        authentionTry-=1;
                        Log.d("J", "onClick: " + j);
                        j -= 1;}



                }

            }
        });
    }






    }
    private final SensorEventListener sensorListener= new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            long curTime = System.currentTimeMillis();

            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                //speed= distance/time distance=(xNew-xLast)
                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > shake) {

                    Log.d("sensor", "shake detected w/ speed: " + speed);
                    speedDetected.add(speed);


                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    //Enable Hardware Back Button
    @Override
    public void onBackPressed() {

    }

}
