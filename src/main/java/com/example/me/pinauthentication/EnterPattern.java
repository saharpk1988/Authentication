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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    int authentionTry=5;
    int remainingTry=3;
    int authenticationNr;
    int nrFailedAuthentication=0;
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
                sm.unregisterListener(sensorListener);
                Log.d("sensor speed detected", "onClick: "+speedDetected);
                float speedAverage=SpeedAverage.average(speedDetected);
                speedDetected.clear();
                Log.d("sensor speedAverage", "onClick: "+speedAverage);
                //List<Integer> myConfirmList = ConfirmPattern.getMyConfirmList();
                TinyDB tinydb = new TinyDB(EnterPattern.this);
                final List<Integer> myConfirmList = tinydb.getListInt("password");
                Logger.patternLog("Pattern entry start time: " + startTime);
                Logger.patternLog("Pattern entry end time: " + endTime);
                Logger.patternLog("Pattern entry time: " + (endTime.getTime() - startTime.getTime()));
                Log.d("Pattern start", "onComplete: "+ startTime);
                Log.d("Pattern end", "onComplete: "+ endTime);
                Log.d("Pattern speed", "onComplete: "+ speedAverage);
                Logger.patternLog("Pattern created: " + myConfirmList);
                Logger.patternLog("Pattern entered: " + myEnteredList);
                //If result is true
                if(myConfirmList.equals(myEnteredList)) {
                    Logger.patternLog("Total Pattern entry time without Error: " + ((endTime.getTime() - startTime.getTime())+FirstOrientation));
                    Logger.patternLog("Result is true");
                    Log.d("Pattern total", "onComplete: "+ ((endTime.getTime() - startTime.getTime())+FirstOrientation));
                    Log.d("Pattern orientation", "onComplete: "+ FirstOrientation);
                    //Its not the last authentication try
                    if (authentionTry!=1) {
                        authentionTry -= 1;
                        remainingTry=3;
                        Logger.patternLog("Authentications to go: " + (authentionTry+1));
                        Logger.patternLog("Average Speed detected is: " + speedAverage);
                        Logger.patternLog("------------------");
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Correct Pattern")
                                .setMessage("Authentications to go: " + authentionTry)
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                                        startOrientation = new Date();
                                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putLong("startOrientation", startOrientation.getTime());
                                        editor.apply();
                                        mPatternLockViewEnter.clearPattern();
                                        myEnteredList.clear();
                                    }
                                }).setCancelable(false).show();
                    }
                        //If result is true and its the last authentication try
                        else if (authentionTry == 1) {
                        authentionTry -= 1;
                        Logger.patternLog("No more authentications to go. Authentication series is finished.");
                        Logger.patternLog("Average Speed detected is: " + speedAverage);
                        Logger.patternLog("Number of failed authentications for this user: "+nrFailedAuthentication);
                        Logger.patternLog("------------------");
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Correct Pattern")
                                .setMessage("Last authentication try. Authentication series finished.")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).setCancelable(false).show();
                    }
                    Result = true;
                    Log.d("Pattern result", "onComplete: "+ Result);
                    //Log Pattern CSV File
                    Logger.patternCsv("UserID, Average Motion Shake, Orientation Time, Entry time, Total Time, Pattern Created, Pattern Entered, Result, Authentication try: " + "\n" + UserID + "- " + speedAverage + "- " + FirstOrientation + "- "+(endTime.getTime() - startTime.getTime())+"-" + ((endTime.getTime() - startTime.getTime())+FirstOrientation) + "- " + myConfirmList + "- " + myEnteredList + "- " + Result + "-"+ (authentionTry+1));
                    //Logger.patternCsv(UserID +", " + Orientation+", "  + (endTime.getTime() - startTime.getTime())+", " + myConfirmList+", " + myEnteredList+", " + Result);
                    Logger.patternCsv("------------------");
                }
                //If result is not true
                else{
                    remainingTry-=1;
                    Logger.patternLog("Total Pattern entry time with Error: " + ((endTime.getTime() - startTime.getTime())+FirstOrientation));
                    Log.d("Pattern total", "onComplete: "+ ((endTime.getTime() - startTime.getTime())+FirstOrientation));
                    Logger.patternLog("Result is false");
                    Result = false;
                    //Log Pattern CSV File
                    Logger.patternCsv("UserID, Average Motion Shake, Orientation Time, Entry time, Total Time, Pattern Created, Pattern Entered, Result, Authentication try: " + "\n" + UserID + "- "+speedAverage+"- " + FirstOrientation + "- "+(endTime.getTime() - startTime.getTime())+"-" + ((endTime.getTime() - startTime.getTime())+FirstOrientation) + "- " + myConfirmList + "- " + myEnteredList + "- " + Result + "-"+ authentionTry);
                    //Logger.patternCsv(UserID +", " + Orientation+", "  + (endTime.getTime() - startTime.getTime())+", " + myConfirmList+", " + myEnteredList+", " + Result);
                    Logger.patternCsv("------------------");
                    Log.d("Pattern Orientation", "onComplete: "+ FirstOrientation);
                    Log.d("Pattern speed", "onComplete: "+ speedAverage);
                    Log.d("Pattern result", "onComplete: "+ Result);
                    //Its not the last remaining try
                    if(remainingTry!=0){
                        Logger.patternLog("Authentication try: "+authentionTry);
                        Logger.patternLog("Remaining try for this authentication: "+ remainingTry);
                        Logger.patternLog("Average Speed detected is: "+speedAverage);
                        Logger.patternLog("------------------");
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Wrong Pattern")
                                .setMessage("Authentications to go: "+authentionTry+"\n"+"Tries for this authentication left: "+remainingTry)
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                                        startOrientation = new Date();
                                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putLong("startOrientation", startOrientation.getTime());
                                        editor.apply();
                                        mPatternLockViewEnter.clearPattern();
                                        myEnteredList.clear();
                                    }
                                }).setCancelable(false).show();
                    }
                    //If its last remaining try for this authentication try and its not the last authentication
                   else if(remainingTry==0&&authentionTry!=1){
                        nrFailedAuthentication+=1;
                        Logger.patternLog("Authentication try: "+authentionTry);
                        Logger.patternLog("Remaining try for this authentication: "+ remainingTry);
                        Logger.patternLog("Authentication for this try failed.");
                        Logger.patternLog("Average Speed detected is: "+speedAverage);
                        Logger.patternLog("------------------");
                        remainingTry=3;
                        authentionTry-=1;
                        authenticationNr=authentionTry+1;
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Wrong Pattern")
                                .setMessage("Authentications to go: "+authentionTry+"\n"+"Previous authentication try failed."+"\n"+ "See password again?")
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

                    }
                    //If its last remaining try for this authentication try and its the last authentication
                    else if(remainingTry==0&&authentionTry==1){
                        nrFailedAuthentication+=1;
                        Logger.patternLog("Last authentication try. No more tries left for this authentication."+"\n"+"This authentication failed."+"\n"+ "Authentication series finished.");
                        Logger.patternLog("Tries for this authentication left: "+ remainingTry);
                        Logger.patternLog("Authentication for this try failed.");
                        Logger.patternLog("Number of failed authentications for this user: "+nrFailedAuthentication);
                        Logger.patternLog("Average Speed detected is: "+speedAverage);
                        Logger.patternLog("------------------");
                        new AlertDialog.Builder(EnterPattern.this).setTitle("Wrong Pattern")
                                .setMessage("No more authentications to go."+"\n"+"This authentication failed."+"\n"+ "Authentication series finished.")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).setCancelable(false).show();


                    }




                }




            }

            @Override
            public void onCleared() {

            }
        });

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
