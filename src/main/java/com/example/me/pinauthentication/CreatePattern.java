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

public final class CreatePattern extends AppCompatActivity {
    static List<Integer> myList= new ArrayList<Integer>();

    public static List<Integer> getMyList(){
        return myList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        final PatternLockView mPatternLockView;
        setContentView(R.layout.activity_create_pattern);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        final Button setPattern = findViewById(R.id.btnSetPattern);
        Button retryButton= findViewById(R.id.btnTry);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                SharedPreferences preferences = getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("password", PatternLockUtils.patternToString(mPatternLockView, pattern));
                editor.apply();


                myList=new PatternHandler().patternHandler(pattern);
                Log.d("test", "Create Pattern page: "+ myList);

            }

            @Override
            public void onCleared() {

            }
        });
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myList.size()!= 0) {
                    mPatternLockView.clearPattern();
                    myList.clear();

                }
            }
        });
        setPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If there has no pattern set
                 if (myList.size() == 0) {
                    mPatternLockView.clearPattern();
                     new AlertDialog.Builder(CreatePattern.this).setTitle("No pattern entered!")
                             .setMessage("Please try again!")
                             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {


                                 }
                             }).setCancelable(false).show();
                    //PatternHandler.toastMessageHandler(CreatePattern.this, "No pattern entered!", Toast.LENGTH_SHORT, Gravity.BOTTOM,10, 57);

                   //If there are less than 6 dots connected.
                } else if (myList.size()<6) {
                     new AlertDialog.Builder(CreatePattern.this).setTitle("Wrong Pattern Input")
                             .setMessage("You need to connect at least 6 dots!")
                             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {


                                 }
                             }).setCancelable(false).show();
                     //PatternHandler.toastMessageHandler(CreatePattern.this, "You need to connect at least 4 dots! Try again.", Toast.LENGTH_LONG, Gravity.BOTTOM,10, 57);
                    mPatternLockView.clearPattern();
                    myList.clear();
                } else{
                    Intent intent = new Intent(getApplicationContext(), ConfirmPattern.class);
                    startActivity(intent);
                    finish();


                }
            }


        });
    }
    //Enable Hardware Back Button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PatternActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }




}
