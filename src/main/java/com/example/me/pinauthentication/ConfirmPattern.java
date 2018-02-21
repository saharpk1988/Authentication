package com.example.me.pinauthentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import java.util.ArrayList;
import java.util.List;

public final class ConfirmPattern extends AppCompatActivity {
    List<Integer> myEntryList = CreatePattern.getMyList();   private static List<Integer> myConfirmList= new ArrayList<Integer>();
    private SharedPreferences prefs;
    private SharedPreferences.Editor editorId;
    private int totalCount;
    String UserID;
    public static List<Integer> getMyConfirmList(){

        return myConfirmList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pattern);

        final PatternLockView mPatternLockViewConfirm;
        final String password;

        Log.d("test9", "Create Pattern getter: " + CreatePattern.getMyList());
        Button confirmPattern= findViewById(R.id.btnConfirmPattern);
        Button cancelPattern= findViewById(R.id.cancel);
        mPatternLockViewConfirm = (PatternLockView) findViewById(R.id.pattern_lock_view_confirm);

        mPatternLockViewConfirm.addPatternLockListener(new PatternLockViewListener() {

            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> patterns) {
                PatternLockUtils.patternToString(mPatternLockViewConfirm, patterns);

                myConfirmList=new PatternHandler().patternHandler(patterns);
                Log.d("test2", "onComplete: "+ myConfirmList.toString());
                Log.d("test3", "Create Pattern get: " + myEntryList);

            }

            @Override
            public void onCleared() {

            }
        });

        confirmPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test3", "Create Pattern get: " + myEntryList);
                Log.d("test4", "Confirm Pattern page: " + myConfirmList);
                TinyDB tinydb = new TinyDB(ConfirmPattern.this);
                tinydb.putListInt("password",(ArrayList)myConfirmList);

                 if(myEntryList.equals(myConfirmList)){
                     //load the UserID
                     SharedPreferences settings=getSharedPreferences("myUserID",0);
                     UserID = settings.getString("userID", "");
                     Logger.patternLog("Creating Pattern");
                     Logger.patternLog("Pattern for user " + UserID + " has been created");
                     Logger.patternLog("Pattern created is: " + myConfirmList);
                     Logger.patternLog("------------------");
                     //enter the Application
                     new AlertDialog.Builder(ConfirmPattern.this).setTitle("Pattern created successfully")
                             .setMessage("Please enter your Pattern!")
                             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                                     startActivity(intent);
                                     finish();


                                 }
                             }).setCancelable(false).show();


                }else {

                     mPatternLockViewConfirm.clearPattern();
                     new AlertDialog.Builder(ConfirmPattern.this).setTitle("Patterns do not match")
                             .setMessage("Please try again!")
                             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {


                                 }
                             }).setCancelable(false).show();
                     //PatternHandler.toastMessageHandler(ConfirmPattern.this, "Patterns do not match!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);



                 }

            }
        });


            cancelPattern.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                    startActivity(intent);
                    finish();
                    mPatternLockViewConfirm.clearPattern();
                    myConfirmList.clear();

                }
            });



    }
    //Enable Hardware Back Button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CreatePattern.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
