package com.example.me.pinauthentication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Intent;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class EnterSwiPIN extends AppCompatActivity {



    private static final int SEQUENCES = 1;
    private static final int INPUTS = 5;
    private SwipeGesture swipeGesture;

    float dimensions;

    private static String user = "";
    private String pwd = "";
    public static String created="";
    private int pinTraining;
    private int completedSequences = 0;
    private int correctPwdCount = 0;
    private int wrongPwdCount = 0;
    private int wrongCount = 0;
    private int fatal = 0;
    private long thinktime;
    public static Boolean result;
    private String log = "";
    private String logd = "";
    private String csvLog = "Orientation,Input_Time,Total_Time";
    private String csvLogd = "Action,startX,startY,endX,endY,speed,lengthX,lengthY,field";
    ArrayList<Float> speedDetected = new ArrayList<>();
    private SensorManager sm;
    private float shake; // Acceleration Value differ from Gravity
    Long lastUpdate=0L;
    float last_x, last_y,last_z;


    int id;
    int j=4;
    int incorrectInput=0;
    int authentionTry=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_enter_swi_pin);
        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        //Gravity_Earth=9.80665f (Objects, when dropped, accelerate at the rate of 9.8 m/s^2 on earth)
        shake=SensorManager.GRAVITY_EARTH;


        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        dimensions = screenSize.x / 2;
        View w = findViewById(R.id.swipe);
        swipeGesture = new SwipeGesture(EnterSwiPIN.this);
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
            Object[][] gestureArray = { { R.drawable.red_circle, SwipeGesture.detectedGesture.DOT },
                    { R.drawable.red_up, SwipeGesture.detectedGesture.UP },
                    { R.drawable.red_right, SwipeGesture.detectedGesture.RIGHT },
                    { R.drawable.red_down, SwipeGesture.detectedGesture.DOWN },
                    { R.drawable.red_left, SwipeGesture.detectedGesture.LEFT } };
            appleGestures(field, gestureArray);

        } else {
            Object[][] gestureArray = { { R.drawable.yellow_circle, SwipeGesture.detectedGesture.DOT },
                    { R.drawable.yellow_up, SwipeGesture.detectedGesture.UP },
                    { R.drawable.yellow_right, SwipeGesture.detectedGesture.RIGHT },
                    { R.drawable.yellow_down, SwipeGesture.detectedGesture.DOWN },
                    { R.drawable.yellow_left, SwipeGesture.detectedGesture.LEFT } };
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

    public void pwdEntered(String entered) {
        sm.unregisterListener(sensorListener);
        Log.d("sensor speed detected", "onClick: "+speedDetected);
        float speedAverage=SpeedAverage.average(speedDetected);
        speedDetected.clear();
        Log.d("sensor speedAverage", "onClick: "+speedAverage);
        Log.d("Tag1", "pwdEntered: "+incorrectInput);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        pwd = settings.getString("password", "");
        created=settings.getString("password", "");
        Log.d("password1", "entered password " + entered);
        Log.d("password2", "entered password " + pwd);
        Logger.swipinLog("SwiPIN created is: " + pwd);

            if (!pwd.equals(entered)) {
                Logger.swipinCsv(pwd+", false, "+speedAverage);
                Logger.swipinCsv("------------------");
                Logger.swipinLog("Result is false");
                result = false;
                final String p = entered;
                Log.d("Tag11", "pwdEntered: "+incorrectInput);
                if(incorrectInput== 2){
                    Logger.swipinLog("Authentication try: "+(j+1));
                    Logger.swipinLog("Average Speed detected is: "+speedAverage);
                    Logger.swipinLog("------------------");
                    new AlertDialog.Builder(EnterSwiPIN.this).setTitle("Wrong SwiPIN")
                            .setMessage("Remaining tries: "+authentionTry+"\n"+"Incorrect SwiPIN entered three times." +"\n" + "Authentication failed."+"\n"+"Please try again!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), SwiPINActivity.class);
                                    startActivity(intent);
                                    finish();


                                }
                            }).setCancelable(false).show();
                }
                else if(j==0&&incorrectInput!=3){
                    Logger.swipinLog("Last Authentication try");
                    Logger.swipinLog("Average Speed detected is: "+speedAverage);
                    Logger.swipinLog("------------------");
                    new AlertDialog.Builder(EnterSwiPIN.this).setTitle("Wrong SwiPIN")
                            .setMessage("Authentication series are finished!"+"\n"+"Authentication successful." )
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), SwiPINActivity.class);
                                    startActivity(intent);
                                    finish();}
                            }).setCancelable(false).show();


                }
                else {

                    incorrectInput += 1;
                    Logger.swipinLog("Authentication try: "+(j+1));
                    Logger.swipinLog("Average Speed detected is: "+speedAverage);
                    Logger.swipinLog("------------------");
                    //PatternHandler.toastMessageHandler(EnterPIN.this, "Wrong password!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);
                    new AlertDialog.Builder(EnterSwiPIN.this).setTitle("Wrong SwiPIN")
                            .setMessage("Authentications to go: " + (j) + "\n" + "Remaining tries: " + authentionTry + "\n" + "See password again?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new AlertDialog.Builder(EnterSwiPIN.this).setTitle("View Password")
                                            .setMessage("Your Password is: " + "\n" + pwd)
                                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                                    swipeGesture.newEntry();
                                                    TextView pwdText = (TextView) findViewById(R.id.pwd);
                                                }
                                            }).setCancelable(false).show();


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                    swipeGesture.newEntry();
                                    TextView pwdText = (TextView) findViewById(R.id.pwd);
                                }
                            }).setCancelable(false).show();
                    authentionTry -= 1;
                    Log.d("J", "onClick: " + j);
                    j -= 1;

                }

            } else {
                Logger.swipinCsv(pwd+", true, "+speedAverage);
                Logger.swipinCsv("------------------");
                Logger.swipinLog("Result is true");
                result = true;
                Log.d("Tag1", "pwdEntered: "+j);
                if (j == 0 && incorrectInput != 3) {
                    Logger.swipinLog("Last Authentication try");
                    Logger.swipinLog("Average Speed detected is: "+speedAverage);
                    Logger.swipinLog("------------------");
                    new AlertDialog.Builder(EnterSwiPIN.this).setTitle("Correct SwiPIN")
                            .setMessage("Authentication successful!" + "\n" + "SwiPIN entered correctly.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), SwiPINActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).setCancelable(false).show();
                } else {
                    Logger.swipinLog("Authentication try: "+(j+1));
                    Logger.swipinLog("Average Speed detected is: "+speedAverage);
                    Logger.swipinLog("------------------");
                    new AlertDialog.Builder(EnterSwiPIN.this).setTitle("Correct SwiPIN")
                            .setMessage("Authentications to go: " + (j) + "\n" + "See password again?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new AlertDialog.Builder(EnterSwiPIN.this).setTitle("View Password")
                                            .setMessage("Your Password is: " + "\n" + pwd)
                                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                                    swipeGesture.newEntry();
                                                    TextView pwdText = (TextView) findViewById(R.id.pwd);
                                                    pwdText.setText("");
                                                }
                                            }).setCancelable(false).show();


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                    swipeGesture.newEntry();
                                    TextView pwdText = (TextView) findViewById(R.id.pwd);
                                    pwdText.setText("");
                                }
                            }).setCancelable(false).show();
                    Log.d("J", "onClick: " + j);
                    j -= 1;


                }

            }
        }

    protected void close() {
        new AlertDialog.Builder(this).setTitle("Authentication").setMessage("Authentications complete.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //log += "\nWrong inputs: " + wrongPwdCount + "\nFatal inputs: " + fatal + "\nCorrect inputs: "
                        //	+ correctPwdCount + "\nClose at " + System.currentTimeMillis();

                        writeLogF();
                        writeCSV();
                        finish();
                    }
                }).setCancelable(false).show();
    }

    private void writeCSV() {
        FileWriter writer;
        //File root = Environment.getExternalStorageDirectory(); //TODO show to ejgham
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File csvFile = new File(root.getAbsolutePath() + "/myLog", "csv__swipinoutside_" + user + ".csv");
        File csvFiled = new File(root.getAbsolutePath() + "/myLog", "csv__swipinoutside_" + user + "_d.csv");
        try {
            writer = new FileWriter(csvFile);
            writer.write(csvLog);
            writer.flush();
            writer.close();

            writer = new FileWriter(csvFiled);
            writer.write(csvLogd);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(csvFile));
        sendBroadcast(intent);

        intent.setData(Uri.fromFile(csvFiled));
        sendBroadcast(intent);
    }

    private void writeLogF() {
        String fileName = "logcat__swipinoutside_" + user + ".txt";
        String fileNamed = "logcat__swipinoutside_" + user + "_d.txt";

        //File save = Environment.getExternalStorageDirectory(); //TODO show to ejgham
        File save = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File dir = new File(save.getAbsolutePath() + "/myLog");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);
        File filed = new File(dir, fileNamed);

        try {
            // to write logcat in text file
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(log);
            bw.newLine();
            bw.newLine();
            bw.flush();
            bw.close();

            FileOutputStream fOutd = new FileOutputStream(filed);
            OutputStreamWriter oswd = new OutputStreamWriter(fOutd);
            BufferedWriter bwd = new BufferedWriter(oswd);
            bwd.write(logd);
            bwd.newLine();
            bwd.newLine();
            bwd.flush();
            bwd.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);

        intent.setData(Uri.fromFile(filed));
        sendBroadcast(intent);
    }





    public void plusLog(String s) {
        log += s;
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
            plusLog("\nWrong Gesture entered. Correct Gesture for " + trueNumber + " is " + image.getTag() + ".");
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

    @Override
    public void onBackPressed() {

    }
}
