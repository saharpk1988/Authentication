package com.example.me.pinauthentication;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Date;

public class EnterPIN extends AppCompatActivity {
    EditText editText;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button b0;
    Button del;
    Button ok;
    String password;
    boolean result;
    long durationDeleteKey;
    Date startTime;
    Date delete;
    Date now;
    long FirstOrientation;
    boolean startFlag = true;
    boolean isCorrected = false;
    String UserID;
    public static String user;
    int authentionTry=5;
    int remainingTry=3;
    int authenticationNr;
    int nrFailedAuthentication=0;
    Date startOrientation;
    ArrayList<String> enteredCharacters = new ArrayList<>();
    ArrayList<String> deletedCharacters = new ArrayList<>();
    ArrayList<String> enteredAndDeleted = new ArrayList<>();
    ArrayList<Float> speedDetected = new ArrayList<>();
    private SensorManager sm;
    private float shake; // Acceleration Value differ from Gravity
    Long lastUpdate=0L;
    float last_x, last_y,last_z;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);


        //load the password
        SharedPreferences settings=getSharedPreferences("pass",0);
        password = settings.getString("pinpassword", "");
        //load the UserID
        SharedPreferences setting = getSharedPreferences("myUserID", 0);
        UserID = setting.getString("userID", "");
        user=UserID;
        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        //Gravity_Earth=9.80665f (Objects, when dropped, accelerate at the rate of 9.8 m/s^2 on earth)
        shake=SensorManager.GRAVITY_EARTH;




        editText = (EditText) findViewById(R.id.editText);
        //Hide Android Softkeyboard


        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }

                return true;
            }

        });

        b1 = (Button) findViewById(R.id.n1);
        b2 = (Button) findViewById(R.id.n2);
        b3 = (Button) findViewById(R.id.n3);
        b4 = (Button) findViewById(R.id.n4);
        b5 = (Button) findViewById(R.id.n5);
        b6 = (Button) findViewById(R.id.n6);
        b7 = (Button) findViewById(R.id.n7);
        b8 = (Button) findViewById(R.id.n8);
        b9 = (Button) findViewById(R.id.n9);
        b0 = (Button) findViewById(R.id.n0);
        del = (Button) findViewById(R.id.del);
        ok = (Button) findViewById(R.id.ok);

        //Logging the timestamps
        final TextWatcher textWatcher=new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!startFlag&&count == 0) {

                    delete = new Date();
                    Log.d("120", "Entering the key: Delete"+delete.getTime());
                    durationDeleteKey += delete.getTime() - now.getTime();
                    Log.d("121", "Entering the key: Delete"+durationDeleteKey);
                    isCorrected = true;

                    Log.d("122", "Entering the key: Delete");
                    Logger.writeFile("Delete pressed");

                } else {
                    // is only executed if the EditText was directly changed by the user

                    if (startFlag) {
                        startTime = new Date();
                        startFlag = false;
                        Logger.writeFile("UserID: " + UserID);
                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                        long startOrientation = settings.getLong("startOrientation", 0L);
                        long startEnter = startTime.getTime();
                        FirstOrientation = startEnter-startOrientation;
                        Logger.writeFile("PIN entry orientation time is: " + FirstOrientation);


                    }
                    now = new Date();
                    Log.d("123", "Entering the key: Delete"+now.getTime());


                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        };



        editText.addTextChangedListener(textWatcher);


        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b0);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b3);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b4);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b5);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b6);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b7);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b8);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEvent(editText, b9);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length() > 1) {
                    del.setEnabled(true);
                    editText.setText(editText.getText().delete(editText.getText().length() - 1, editText.getText().length()));
                    editText.setSelection(editText.length());

                    //enter deleted characters in the list
                    deletedCharacters.add(enteredAndDeleted.get(enteredAndDeleted.size() - 1));
                    enteredAndDeleted.remove(enteredAndDeleted.size() - 1);

                    Log.d("Test1", "deleted " + deletedCharacters);


                } else if (editText.getText().length() == 1) {

                    editText.setText(editText.getText().delete(editText.getText().length() - 1, editText.getText().length()));
                    editText.setSelection(editText.length());
                    del.setEnabled(false);
                    //enter deleted characters in the list
                    deletedCharacters.add(enteredAndDeleted.get(enteredAndDeleted.size() - 1));
                    enteredAndDeleted.remove(enteredAndDeleted.size() - 1);
                    Log.d("Test13", "length " + editText.length());
                    Log.d("Test2", "deleted " + deletedCharacters);


                }

            }
        });
        if (editText.getText().length() == 0) {
            del.setEnabled(false);
        }

        ok.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                          sm.unregisterListener(sensorListener);
                                          Log.d("sensor speedtectedde", "onClick: "+speedDetected);
                                          float speedAverage=SpeedAverage.average(speedDetected);
                                          speedDetected.clear();
                                          Log.d("sensor speedAverage", "onClick: "+speedAverage);
                                          String text = editText.getText().toString();
                                          if (!text.isEmpty()) {
                                              Date endTime = new Date();
                                              if (isCorrected) {
                                                  long total = ((endTime.getTime() - startTime.getTime())+FirstOrientation);
                                                  //Logger.writeFile("User ID is: " + counter);
                                                  Logger.writeFile("PIN entry start time is: " + startTime);
                                                  Logger.writeFile("PIN entry end time is: " + endTime);
                                                  Logger.writeFile("PIN entry time: " + (endTime.getTime() - startTime.getTime()));
                                                  Logger.writeFile("Total PIN entry time with correction times is: " + total);
                                                  Logger.writeFile("Correction time is: " + durationDeleteKey);
                                                  Logger.writeFile("PIN created is: " + password);
                                                  Logger.writeFile("PIN entered is: " + enteredCharacters);
                                                  Logger.writeFile("Deleted numbers during correction: " + deletedCharacters);
                                                  Log.d("PIN Deleted", "deleted " + deletedCharacters);
                                                  Log.d("PIN Orientation", "onComplete: "+ FirstOrientation);
                                                  Log.d("PIN Total", "onComplete: "+ total);
                                                  //Log Pin CSV File
                                                  Logger.pinCsv("UserID, Average Motion Shake, Orientation Time, Entry time, Total Time, Authentication try, Correction Time, Pin Created, Pin Entered, Deleted Numbers, Result: " + "\n" + UserID + "- " + speedAverage + "- " + FirstOrientation + "-" + (endTime.getTime() - startTime.getTime()) + "- " + total + "- " + authentionTry + "-" + durationDeleteKey + "- " + password + "- " + enteredCharacters + "- " + deletedCharacters + "- ");


                                              } else {
                                                  long total = ((endTime.getTime() - startTime.getTime()) + FirstOrientation);
                                                  //Logger.writeFile("User ID is: " + counter);
                                                  Logger.writeFile("PIN entry start time is: " + startTime);
                                                  Logger.writeFile("PIN entry end time is: " + endTime);
                                                  Logger.writeFile("PIN entry time: " + (endTime.getTime() - startTime.getTime()));
                                                  Logger.writeFile("Total PIN entry time without correction: " + total);
                                                  Logger.writeFile("PIN created is: " + password);
                                                  Logger.writeFile("PIN entered is: " + enteredCharacters);
                                                  Log.d("Pattern Orientation", "onComplete: "+ FirstOrientation);
                                                  Log.d("Pattern Total", "onComplete: "+ total);
                                                  //Log Pin CSV File
                                                  Logger.pinCsv("UserID, Average Motion Shake, Orientation Time, Entry time, Total Time, Authentication try, Correction Time, Pin Created, Pin Entered, Deleted Numbers, Result: " + "\n" + UserID + "- " + speedAverage + "- " + FirstOrientation + "-" + (endTime.getTime() - startTime.getTime()) + "- " + total + "-" + authentionTry + "-" + "0" + "- " + password + "- " + enteredCharacters + "- " + "[]" + "- ");


                                              }

                                              Log.d("Test4", "Pass is: " + text);
                                              if (text.equals(password)) {
                                                  //Its not the last authentication try
                                                  if (authentionTry != 1) {
                                                      authentionTry -= 1;
                                                      remainingTry = 3;
                                                      Logger.writeFile("Authentications to go: " + (authentionTry + 1));
                                                      Logger.writeFile("Average Speed detected is: " + speedAverage);
                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Correct PIN")
                                                              .setMessage("Authentications to go: " + authentionTry)
                                                              .setCancelable(false)
                                                              .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                                                                      isCorrected = false;
                                                                      durationDeleteKey = 0;
                                                                      startOrientation = new Date();
                                                                      SharedPreferences settings = getSharedPreferences("PREFS", 0);
                                                                      SharedPreferences.Editor editor = settings.edit();
                                                                      editor.putLong("startOrientation", startOrientation.getTime());
                                                                      editor.apply();
                                                                      editText.removeTextChangedListener(textWatcher);
                                                                      editText.clearFocus();
                                                                      editText.setText("");
                                                                      startFlag = true;
                                                                      editText.addTextChangedListener(textWatcher);
                                                                      deletedCharacters.clear();
                                                                      enteredAndDeleted.clear();
                                                                      enteredCharacters.clear();
                                                                  }
                                                              }).setCancelable(false).show();
                                                  }
                                                  //If result is true and its the last authentication try
                                                  else if (authentionTry == 1) {
                                                      authentionTry -= 1;
                                                      Logger.writeFile("No more authentications to go. Authentication series is finished.");
                                                      Logger.writeFile("Average Speed detected is: " + speedAverage);
                                                      Logger.writeFile("Number of failed authentications for this user: " + nrFailedAuthentication);
                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Correct PIN")
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
                                                  Logger.writeFile("Result is true");
                                                  Logger.writeFile("------------------");
                                                  Logger.pinCsv("true"+"\n");
                                                  Logger.pinCsv("------------------"+"\n");

                                              }

                                                  //If result is not true
                                                  else{
                                                  remainingTry-=1;
                                                  //Its not the last remaining try
                                                  if(remainingTry!=0){
                                                      Logger.writeFile("Authentication try: "+authentionTry);
                                                      Logger.writeFile("Remaining try for this authentication: "+ remainingTry);
                                                      Logger.writeFile("Average Speed detected is: "+speedAverage);
                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Wrong PIN")
                                                              .setMessage("Authentications to go: "+authentionTry+"\n"+"Tries for this authentication left: "+remainingTry)
                                                              .setCancelable(false)
                                                              .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                                                      isCorrected=false;
                                                                      durationDeleteKey=0;
                                                                      startOrientation = new Date();
                                                                      SharedPreferences settings = getSharedPreferences("PREFS", 0);
                                                                      SharedPreferences.Editor editor = settings.edit();
                                                                      editor.putLong("startOrientation", startOrientation.getTime());
                                                                      editor.apply();
                                                                      editText.removeTextChangedListener(textWatcher);
                                                                      editText.clearFocus();
                                                                      editText.setText("");
                                                                      editText.clearFocus();
                                                                      startFlag = true;
                                                                      editText.addTextChangedListener(textWatcher);
                                                                      deletedCharacters.clear();
                                                                      enteredAndDeleted.clear();
                                                                      enteredCharacters.clear();
                                                                  }
                                                              }).setCancelable(false).show();
                                                  }
                                                  //If its last remaining try for this authentication try and its not the last authentication
                                                  else if(remainingTry==0&&authentionTry!=1){
                                                      nrFailedAuthentication+=1;
                                                      Logger.writeFile("Authentication try: "+authentionTry);
                                                      Logger.writeFile("Remaining try for this authentication: "+ remainingTry);
                                                      Logger.writeFile("Authentication for this try failed.");
                                                      Logger.writeFile("Average Speed detected is: "+speedAverage);
                                                      remainingTry=3;
                                                      authentionTry-=1;
                                                      authenticationNr=authentionTry+1;
                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Wrong PIN")
                                                              .setMessage("Authentications to go: "+authentionTry+"\n"+"Previous authentication try failed."+"\n"+ "See password again?")
                                                              .setCancelable(false)
                                                              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("View Password")
                                                                              .setMessage("Your Password is: " + "\n" + password)
                                                                              .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                                      sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                                                                      isCorrected=false;
                                                                                      durationDeleteKey=0;
                                                                                      startOrientation = new Date();
                                                                                      SharedPreferences settings = getSharedPreferences("PREFS", 0);
                                                                                      SharedPreferences.Editor editor = settings.edit();
                                                                                      editor.putLong("startOrientation", startOrientation.getTime());
                                                                                      editor.apply();
                                                                                      editText.removeTextChangedListener(textWatcher);
                                                                                      editText.clearFocus();
                                                                                      editText.setText("");
                                                                                      editText.clearFocus();
                                                                                      startFlag = true;
                                                                                      editText.addTextChangedListener(textWatcher);
                                                                                      deletedCharacters.clear();
                                                                                      enteredAndDeleted.clear();
                                                                                      enteredCharacters.clear();
                                                                                  }
                                                                              }).setCancelable(false).show();



                                                                  }
                                                              })
                                                              .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                                                      isCorrected=false;
                                                                      durationDeleteKey=0;
                                                                      startOrientation = new Date();
                                                                      SharedPreferences settings = getSharedPreferences("PREFS", 0);
                                                                      SharedPreferences.Editor editor = settings.edit();
                                                                      editor.putLong("startOrientation", startOrientation.getTime());
                                                                      editor.apply();
                                                                      editText.removeTextChangedListener(textWatcher);
                                                                      editText.clearFocus();
                                                                      editText.setText("");
                                                                      editText.clearFocus();
                                                                      startFlag = true;
                                                                      editText.addTextChangedListener(textWatcher);
                                                                      deletedCharacters.clear();
                                                                      enteredAndDeleted.clear();
                                                                      enteredCharacters.clear();
                                                                  }
                                                              }).setCancelable(false).show();

                                                  }
                                                  //If its last remaining try for this authentication try and its the last authentication
                                                  else if(remainingTry==0&&authentionTry==1){
                                                      nrFailedAuthentication+=1;
                                                      Logger.writeFile("Last authentication try. No more tries left for this authentication."+"\n"+"This authentication failed."+"\n"+ "Authentication series finished.");
                                                      Logger.writeFile("Tries for this authentication left: "+ remainingTry);
                                                      Logger.writeFile("Authentication for this try failed.");
                                                      Logger.writeFile("Number of failed authentications for this user: "+nrFailedAuthentication);
                                                      Logger.writeFile("Average Speed detected is: "+speedAverage);
                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Wrong PIN")
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
                                                  result = false;
                                                  Logger.writeFile("Result is false");
                                                  Logger.writeFile("------------------");
                                                  Logger.pinCsv("false"+"\n");
                                                  Logger.pinCsv("------------------"+"\n");
                                              }

                                              }
                                          else {
                                              new AlertDialog.Builder(EnterPIN.this).setTitle("No password entered!")
                                                      .setMessage("Please enter your PIN!")
                                                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                                                          }
                                                      }).setCancelable(false).show();


                                          }




                                      }
                                  }


            );


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

    public void handleEvent(EditText editText,Button button) {


        editText.setText(editText.getText().insert(editText.getText().length(), button.getText().toString()));

            Logger.writeFile("Button "+ button.getText().toString()+" pressed");
            enteredCharacters.add(button.getText().toString());
            enteredAndDeleted.add(button.getText().toString());
            Log.d("Test5", "entered: " + enteredCharacters);
            //Set Cursor Position
            editText.setSelection(editText.length());
           //Enable delete button after each click
            del.setEnabled(true);
            //Mask every Input after 700 ms

    }
    //Enable Hardware Back Button
    @Override
    public void onBackPressed() {

    }


}
