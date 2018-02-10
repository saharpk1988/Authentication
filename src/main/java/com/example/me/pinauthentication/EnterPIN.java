package com.example.me.pinauthentication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
    int j=4;
    int incorrectInput=0;
    int authentionTry=2;
    Date startOrientation;
    ArrayList<String> enteredCharacters = new ArrayList<>();
    ArrayList<String> deletedCharacters = new ArrayList<>();
    ArrayList<String> enteredAndDeleted = new ArrayList<>();



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);

        //load the password
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");
        //load the UserID
        SharedPreferences setting = getSharedPreferences("myUserID", 0);
        UserID = setting.getString("userID", "");
        user=UserID;


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

        for (int i=1;i<6;i++) {
            ok.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          String text = editText.getText().toString();
                                          if (!text.isEmpty()) {
                                              Date endTime = new Date();
                                              if (isCorrected) {
                                                  long total = endTime.getTime() - startTime.getTime();
                                                  //Logger.writeFile("User ID is: " + counter);
                                                  Logger.writeFile("PIN entry start time is: " + startTime);
                                                  Logger.writeFile("PIN entry end time is: " + endTime);
                                                  Logger.writeFile("Total PIN entry time with correction times is: " + total);
                                                  Logger.writeFile("Correction time is: " + durationDeleteKey);
                                                  Logger.writeFile("PIN created is: " + password);
                                                  Logger.writeFile("PIN entered is: " + enteredCharacters);
                                                  Logger.writeFile("Deleted numbers during correction: " + deletedCharacters);
                                                  Log.d("Test3", "deleted " + deletedCharacters);
                                                  //Log Pin CSV File
                                                  Logger.pinCsv("UserID, Orientation Time, Total Time, Correction Time, Pin Created, Pin Entered, Deleted Numbers, Result: " + "\n" + UserID + ", " + FirstOrientation + ", " + total + ", " + durationDeleteKey + ", " + password + ", " + enteredCharacters + ", " + deletedCharacters+", ");


                                              } else {
                                                  long total = endTime.getTime() - startTime.getTime();
                                                  //Logger.writeFile("User ID is: " + counter);
                                                  Logger.writeFile("PIN entry start time is: " + startTime);
                                                  Logger.writeFile("PIN entry end time is: " + endTime);
                                                  Logger.writeFile("Total PIN entry time without correction: " + total);
                                                  Logger.writeFile("PIN created is: " + password);
                                                  Logger.writeFile("PIN entered is: " + enteredCharacters);
                                                  //Log Pin CSV File
                                                  Logger.pinCsv("UserID, Orientation Time, Total Time, Pin Created, Pin Entered, Result: " + "\n" + UserID + ", " + FirstOrientation + ", " + total + ", " + password + ", " + enteredCharacters+", ");


                                              }

                                              Log.d("Test4", "Pass is: " + text);
                                              if (text.equals(password)) {
                                                  //Last Authentication try when entering correct password
                                                  if (j == 0 && incorrectInput != 3) {
                                                      Logger.writeFile("Last Authentication try");

                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Correct PIN")
                                                              .setMessage("Authentication successful!" + "\n" + "PIN entered correctly.")
                                                              .setCancelable(false)
                                                              .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      Intent intent = new Intent(getApplicationContext(), PINActivity.class);
                                                                      startActivity(intent);
                                                                      finish();
                                                                  }
                                                              }).setCancelable(false).show();


                                                  } else {
                                                      Logger.writeFile("Authentication try: " + (j + 1));

                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Correct PIN")
                                                              .setMessage("Authentications to go: " + (j) + "\n" + "See password again?")
                                                              .setCancelable(false)
                                                              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("View Password")
                                                                              .setMessage("Your Password is: " + "\n" + password)
                                                                              .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                                                  public void onClick(DialogInterface dialog, int which) {
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
                                                                                      startFlag=true;
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
                                                                      startFlag = true;
                                                                      editText.addTextChangedListener(textWatcher);
                                                                      deletedCharacters.clear();
                                                                      enteredAndDeleted.clear();
                                                                      enteredCharacters.clear();
                                                                  }
                                                              }).setCancelable(false).show();

                                                      j -= 1;
                                                  }

                                                  result = true;
                                                  Logger.writeFile("Result is true");
                                                  Logger.writeFile("------------------");
                                                  Logger.pinCsv("true");
                                                  Logger.pinCsv("------------------");



                                              } else {

                                                  if (incorrectInput == 2) {
                                                      Logger.writeFile("Authentication try: " + (j + 1));

                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Wrong PIN")
                                                              .setMessage("Remaining tries: " + authentionTry + "\n" + "Incorrect PIN entered three times." + "\n" + "Authentication failed." + "\n" + "Please try again!")
                                                              .setCancelable(false)
                                                              .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      Intent intent = new Intent(getApplicationContext(), PINActivity.class);
                                                                      startActivity(intent);
                                                                      finish();


                                                                  }
                                                              }).setCancelable(false).show();
                                                  } else if(j==0&&incorrectInput!=3){
                                                      Logger.writeFile("Last Authentication try");
                                                      Logger.writeFile("------------------");
                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Wrong PIN")
                                                              .setMessage("Authentication series are finished!"+"\n"+"Authentication successful." )
                                                              .setCancelable(false)
                                                              .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      Intent intent = new Intent(getApplicationContext(), PINActivity.class);
                                                                      startActivity(intent);
                                                                      finish();}
                                                              }).setCancelable(false).show();


                                                  }
                                                  else {
                                                      //Authentication try when entering wrong password
                                                      incorrectInput += 1;
                                                      Logger.writeFile("Authentication try: " + (j + 1));

                                                      //PatternHandler.toastMessageHandler(EnterPIN.this, "Wrong password!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);
                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("Wrong PIN")
                                                              .setMessage("Authentications to go: " + (j) + "\n" + "Remaining tries: " + authentionTry + "\n" + "See password again?")
                                                              .setCancelable(false)
                                                              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                      new AlertDialog.Builder(EnterPIN.this).setTitle("View Password")
                                                                              .setMessage("Your Password is: " + "\n" + password)
                                                                              .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                                                  public void onClick(DialogInterface dialog, int which) {
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
                                                                      isCorrected=false;
                                                                      durationDeleteKey=0;
                                                                      startOrientation = new Date();
                                                                      SharedPreferences settings = getSharedPreferences("PREFS", 0);
                                                                      SharedPreferences.Editor editor = settings.edit();
                                                                      editor.putLong("startOrientation", startOrientation.getTime());
                                                                      editor.apply();
                                                                      editText.removeTextChangedListener(textWatcher);
                                                                      editText.clearFocus();
                                                                      editText.getText().clear();
                                                                      startFlag = true;
                                                                      editText.addTextChangedListener(textWatcher);
                                                                      deletedCharacters.clear();
                                                                      enteredAndDeleted.clear();
                                                                      enteredCharacters.clear();
                                                                  }
                                                              }).setCancelable(false).show();
                                                      authentionTry -= 1;
                                                      Log.d("J", "onClick: " + j);
                                                      j -= 1;
                                                  }

                                                  result = false;
                                                  Logger.writeFile("Result is false");
                                                  Logger.writeFile("------------------");
                                                  Logger.pinCsv("false");
                                                  Logger.pinCsv("------------------");

                                              }

                                          } else {
                                              new AlertDialog.Builder(EnterPIN.this).setTitle("No password entered!")
                                                      .setMessage("Please enter your password!")
                                                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                          public void onClick(DialogInterface dialog, int which) {

                                                          }
                                                      }).setCancelable(false).show();


                                          }

                                      }
                                  }


            );
        }

    }

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
