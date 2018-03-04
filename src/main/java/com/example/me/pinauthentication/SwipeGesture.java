package com.example.me.pinauthentication;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.example.me.pinauthentication.EnterSwiPIN;
import com.example.me.pinauthentication.R;

import java.util.ArrayList;
import java.util.Date;

public class SwipeGesture extends GestureDetector.SimpleOnGestureListener implements OnTouchListener {
    // min distance
    private static final int SWIPE_MIN_DISTANCE = 50;
    // error limit
    private static final int SWIPE_MAX_OFF_PATH = 100;
    // speed
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    private boolean testTraining = true;
    private boolean training = false;
    public Activity activity;
    EnterSwiPIN act;
    GestureDetector gDetector;
    static detectedGesture dGesture;
    String field;
    float dim;
    ArrayList<Integer> enteredNumbers = new ArrayList<>();
    ArrayList<Integer> deletedNumbers = new ArrayList<>();
    String enter = "";
    boolean newEntry = true;
    boolean isDeleted=false;
    Date newEntered;
    Date startTime;
    Date endTime;
    String user;
    String Condition;
    long startOrientation;
    long orientation;
    long time;
    Date timed;
    long timeu;
    Boolean result;
    String created;
    long total;
    long entry;
    Context context;
    SharedPreferences pref;






    public enum detectedGesture {
        UP, RIGHT, DOWN, LEFT, DOT;
    }

    public SwipeGesture(EnterSwiPIN act) {
        this(act, null);
    }

    public SwipeGesture(EnterSwiPIN act, GestureDetector gDetector) {

        if (gDetector == null)
            gDetector = new GestureDetector(act, this);

        this.activity = (Activity) act;
        this.act = act;
        this.gDetector = gDetector;
        dim = act.dimensions;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        int x = getXY(event.getX());
        int y = getXY(event.getY());

        if (event.getX() <= dim)
            field = "l_";
        else
            field = "r_";
        act.changeVisibility(field, false);
        return true;
    }

    private int getXY(float f) {
        int r;
        if (f < 2 * dim) {
            if (f < dim)
                r = 0;
            else
                r = 1;
        } else {
            if (f < 3 * dim)
                r = 2;
            else
                r = 3;
        }
        return r;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        // http://pcfandroid.wordpress.com/2011/07/17/swipe-with-android-android-tutorial/
        float dX = event2.getX() - event1.getX();
        float dY = event1.getY() - event2.getY();

        // LINKS RECHTS WISCHEN
        if (Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dX / dY) > 1) {

            if (dX > 0) {
                time = System.currentTimeMillis();
                if (!training) {
                    Logger.swipinLog("\nFling was RIGHT in field " + field + " with speed = " + Math.abs(velocityX)
                            + "\n             and length x = " + dX + "\n             and length y = " + dY);
                    Log.d("Fling right", "onFling: "+field);

                    /*act.plusLogd("\nFling was RIGHT in field " + field + " with speed = " + Math.abs(velocityX)
                            + "\n             and length x = " + dX + "\n             and length y = " + dY);
                    act.plusCsvD("\nRIGHT," + event1.getX() + "," + event1.getY() + "," + event2.getX() + ","
                            + event2.getY() + "," + Math.abs(velocityX) + "," + dX + "," + dY + "," + field);*/
                }
                dGesture = detectedGesture.RIGHT;
            } else {
                time = System.currentTimeMillis();
                if (!training) {
                    Logger.swipinLog("\nFling was LEFT in field " + field + " with speed = " + Math.abs(velocityX)
                            + "\n             and length x = " + dX + "\n             and length y = " + dY);
                    Log.d("Fling left", "onFling: "+field);
                    /*act.plusLogd("\nFling was LEFT in field " + field + " with speed = " + Math.abs(velocityX)
                            + "\n             and length x = " + dX + "\n             and length y = " + dY);
                    act.plusCsvD("\nLEFT," + event1.getX() + "," + event1.getY() + "," + event2.getX() + ","
                            + event2.getY() + "," + Math.abs(velocityX) + "," + dX + "," + dY + "," + field);*/
                }
                dGesture = detectedGesture.LEFT;
            }
            findGesture(field, dGesture);
            return true;
        } else if (Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dY / dX) > 1) {

            if (dY > 0) {
                time = System.currentTimeMillis();
                if (!training) {
                    Logger.swipinLog("\nFling was UP in field " + field + " with speed = " + Math.abs(velocityY)
                            + "\n             and length x = " + dX + "\n             and length y = " + dY);
                    Log.d("Fling Up", "onFling: "+field);
                    /*act.plusLogd("\nFling was UP in field " + field + " with speed = " + Math.abs(velocityY)
                            + "\n             and length x = " + dX + "\n             and length y = " + dY);
                    act.plusCsvD("\nUP," + event1.getX() + "," + event1.getY() + "," + event2.getX() + ","
                            + event2.getY() + "," + Math.abs(velocityX) + "," + dX + "," + dY + "," + field);*/
                }
                dGesture = detectedGesture.UP;
            } else {
                time = System.currentTimeMillis();
                if (!training) {
                    Logger.swipinLog("\nFling was DOWN in field " + field + " with speed = " + Math.abs(velocityY)
                            + "\n             and length x = " + dX + "\n             and length y = " + dY);
                    Log.d("Fling down", "onFling: "+field);
                    /*act.plusLogd("\nFling was DOWN in field " + field + " with speed = " + Math.abs(velocityY)
                            + "\n             and length x = " + dX + "\n             and length y = " + dY);
                    act.plusCsvD("\nDOWN," + event1.getX() + "," + event1.getY() + "," + event2.getX() + ","
                            + event2.getY() + "," + Math.abs(velocityX) + "," + dX + "," + dY + "," + field);*/
                }
                dGesture = detectedGesture.DOWN;
            }
            findGesture(field, dGesture);
            return true;
        }

        time = System.currentTimeMillis();
        if (!training) {
            Logger.swipinLog("\nFling was FAILED in field " + field + " with speed = " + Math.abs(velocityY)
                    + "\n             and length x = " + dX + "\n             and length y = " + dY);
            Log.d("Fling failed", "onFling: ");
           /* act.plusLogd("\nFling was FAILED in field " + field + " with speed = " + Math.abs(velocityY)
                    + "\n             and length x = " + dX + "\n             and length y = " + dY);
            act.plusLog("\nFling FAILED at " + time);*/
        }
        newEntry = false;

        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        time = System.currentTimeMillis();
        if (!training)
            Logger.swipinLog("\nTAP," + event.getX() + "," + event.getY() + ",0,0,0,0,0" + "," + field);
            Log.d("Fling Tap", "onFling: ");
            //act.plusCsvD("\nTAP," + event.getX() + "," + event.getY() + ",0,0,0,0,0" + "," + field);

        dGesture = detectedGesture.DOT;
        findGesture(field, dGesture);
        return true;

    }

    @Override
    public boolean onTouch(View v, final MotionEvent event) {
        //load the UserID


       //SharedPreferences settings = getSharedPreferences("PREFS", 0);
        //long startOrientation = settings.getLong("startOrientation", 0L);

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):


                //load the UserID
                user=SwiPINActivity.UserID;
                Condition=SwiPINActivity.Condition;
                Log.d("username1", "onTouch: "+user);
                //load orientation time
                startOrientation=SwiPINActivity.startOrientation.getTime();
                //SharedPreferences settings = mContext.getSharedPreferences("PREFS", 0);
                //startOrientation = settings.getLong("startOrientation", 0L);
                timed = new Date();
                if (!training) {
                 Log.d("Fling action down ","here"+timed.getTime());
                    if (newEntry) {
                        //Clicking the enter SwiPIN button
                        if(newEntered==null){

                            orientation = (timed.getTime() - startOrientation);
                            Log.d("FlingstartOrientation13","here"+startOrientation);
                            Logger.swipinLog("\nUser ID: " + user);
                            Logger.swipinLog("\nCondition: " + Condition);
                            Log.d("Fling userID ","here"+user);
                            Logger.swipinLog("\nTime orientation: " + orientation + "ms");
                            Log.d("Fling orientation0 ","here"+orientation);
                            startTime=timed;
                            Logger.swipinLog("PIN entry start time is: " + startTime);
                            Log.d("Fling startTime ","here"+ startTime);
                        }
                        else{
                            orientation = (timed.getTime() - newEntered.getTime());
                            Logger.swipinLog("\nUser ID: " + user);
                            Logger.swipinLog("\nCondition: " + Condition);
                            Log.d("Fling userID ","here"+user);
                            Logger.swipinLog("\nTime orientation: " + orientation + "ms");
                            Log.d("Fling orientation ","here"+orientation);
                            startTime=timed;
                            Logger.swipinLog("PIN entry start time is: " + startTime);
                            Log.d("Fling startTime ","here"+ startTime);
                    }
                    } else {
                        long timeThought = ((timed.getTime() - timeu));
                        Logger.swipinLog("\nTime thought: " + timeThought + "ms");
                        Log.d("Fling time tought ","here"+timeThought);
                       // act.plusLog("\nTime thought: " + timeThought + "ms");
                        //act.plusCsv("\n0," + timeThought + ",0");
                    }
                    Logger.swipinLog("\nACTION_DOWN at " + timed);
                }
                newEntry = false;
                onDown(event);
                break;
            case (MotionEvent.ACTION_UP):
                timeu = System.currentTimeMillis();
                if (!training) {
                    Logger.swipinLog("\nTime for Gesture: " + (timeu - timed.getTime()) + "ms");
                    Log.d("Fling Gesture time", "onTouch: "+(timeu - timed.getTime()));
                    //act.plusLog("\nTime for Gesture: " + (timeu - timed) + "ms");
                    //act.plusLogd("\nACTION_UP at " + timeu + "\n Time down: " + (timeu - timed) + "ms");
                }
                act.changeVisibility(field, true);
                break;
            default:
                return gDetector.onTouchEvent(event);
        }
        return gDetector.onTouchEvent(event);
    }

    public GestureDetector getDetector() {
        return gDetector;
    }

    private void findGesture(final String field, detectedGesture dG) {
        newEntry = false;
        TextView pwdText = (TextView) this.activity.findViewById(R.id.pwd);
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
            image = (ImageView) this.activity.findViewById(id);
            if (image.getTag().equals(dGesture)) {
                if (!training)
                    //Gesture entered
                    Logger.swipinLog("\nGesture " + dGesture + " found");
                    Logger.swipinLog("***");
                    Log.d("Fling gesture", "onTouch: "+dGesture);

                if (field.equals("r_")) {
                    switch (i) {
                        case 0:
                            enter += "3";
                            enteredNumbers.add(3);
                            deletedNumbers.add(3);
                            Log.d("Fling number", "3");
                            Logger.swipinLog("\nNumber 3 found");
                            break;
                        case 1:
                            enter += "6";
                            enteredNumbers.add(6);
                            deletedNumbers.add(6);
                            Log.d("Fling number", "6");
                            Logger.swipinLog("\nNumber 6 found");
                            break;
                        case 2:
                            enter += "8";
                            enteredNumbers.add(8);
                            deletedNumbers.add(8);
                            Log.d("Fling number", "8");
                            Logger.swipinLog("\nNumber 8 found");
                            break;
                        case 3:
                            enter += "9";
                            enteredNumbers.add(9);
                            deletedNumbers.add(9);
                            Log.d("Fling number", "9");
                            Logger.swipinLog("\nNumber 9 found");
                            break;
                        case 4:
                            enter += "0";
                            enteredNumbers.add(0);
                            deletedNumbers.add(0);
                            Log.d("Fling number", "0");
                            Logger.swipinLog("\nNumber 0 found");
                            break;

                    }
                } else
                    switch (i) {
                        case 0:
                            enter += "1";
                            enteredNumbers.add(1);
                            deletedNumbers.add(1);
                            Log.d("Fling number", "1");
                            Logger.swipinLog("\nNumber 1 found");
                            break;
                        case 1:
                            enter += "2";
                            enteredNumbers.add(2);
                            deletedNumbers.add(2);
                            Log.d("Fling number", "2");
                            Logger.swipinLog("\nNumber 2 found");
                            break;
                        case 2:
                            enter += "4";
                            enteredNumbers.add(4);
                            deletedNumbers.add(4);
                            Log.d("Fling number", "4");
                            Logger.swipinLog("\nNumber 4 found");
                            break;
                        case 3:
                            enter += "5";
                            enteredNumbers.add(5);
                            deletedNumbers.add(5);
                            Log.d("Fling number", "5");
                            Logger.swipinLog("\nNumber 5 found");
                            break;
                        case 4:
                            enter += "7";
                            enteredNumbers.add(7);
                            deletedNumbers.add(7);
                            Log.d("Fling number", "7");
                            Logger.swipinLog("\nNumber 7 found");
                            break;

                    }
                if (testTraining) {

                    pwdText.setText(enter);
                    if (enter.length() >= 4 ) {
                            endTime=new Date();
                            Logger.swipinLog("PIN entry end time is: " + endTime);
                            Log.d("Fling endTime ","here"+ endTime);
                            total=((endTime.getTime()-startTime.getTime())+orientation);
                            entry=(endTime.getTime()-startTime.getTime());

                            Log.d("Fling totalTime ","here"+ total);
                            Logger.swipinLog("PIN entry total time is: " + total);
                            Logger.swipinLog("PIN entry time is: " + entry);
                            Logger.swipinLog("\nSwiPIN entered is: " + enter);


                        Log.d("Fling entered", "onTouch: "+enter);
                        result=EnterSwiPIN.result;
                        created=EnterSwiPIN.created;
                        Log.d("resulti", "findGesture: "+result);
                        Log.d("resulti", "findGesture: "+created);

                        if(isDeleted){
                            Logger.swipinLog("\nClear is clicked");
                            Logger.swipinLog("\nPassword entered and deleted" + deletedNumbers);
                            Log.d("Fling cleared", "Password entered and deleted" + deletedNumbers);
                            Logger.swipinCsv("UserID, Condition, Orientation Time(ms), Entry Time(ms), Total Time(ms), is Cleared(boolean), SwiPIN Entered, SwiPIN created, Authentication Try, Result(boolean), Average Motion Shake(mm/s): " + "\n" + user + "- " + Condition + "- " + orientation + "- "+entry+"-"+ total + "- true"+ "- "+ enter + "- ");


                        }
                        else{
                            Logger.swipinCsv("UserID, Condition, Orientation Time(ms), Entry Time(ms), Total Time(ms), is Cleared(boolean), SwiPIN Entered, SwiPIN created, Authentication Try, Result(boolean), Average Motion Shake(mm/s): " + "\n" + user + "- " + Condition + "- " + orientation + "- "+entry+"-"+ total + "- false"+ "- "+ enter +"- " );

                        }
                        act.pwdEntered(enter);
                        }

                } else if (training) {

                    pwdText.setText(enter);
                    if (enter.length() >= 4) {
                        act.pwdEntered(enter);
                    }
                } else {

                    act.plusLog("" + (Integer.valueOf(enter) % 10));
                    act.findCorrectGesture(enter, field);
                    pwdText.setText(pwdText.getText().toString() + "*", TextView.BufferType.EDITABLE);
                    if (enter.length() >= 4) {
                        act.pwdEntered(enter);
                        newEntry = true;
                    }
                }
            }
        }
        if (field.equals("l_")) {
            act.changeGestures(field);
        } else {
            act.changeGestures(field);
        }
    }

    public void deleteEntry() {
        enter = "";
        isDeleted=true;
        TextView pwdText = (TextView) this.activity.findViewById(R.id.pwd);
        pwdText.setText(enter);

    }
    public void newEntry(){
        isDeleted=false;
        newEntry = true;
        enter = "";
        TextView pwdText = (TextView) this.activity.findViewById(R.id.pwd);
        pwdText.setText(enter);
        enteredNumbers.clear();
        deletedNumbers.clear();
        newEntered=new Date();
        Log.d("Fling new enetered", "newEntry: "+newEntered.getTime());

    }

    public String getEntry() {
        return enter;
    }

    public boolean getTrainingMode() {
        return training;
    }

    public void setTestTraining(boolean testTraining) {
        this.testTraining = testTraining;
    }

    public void setTrainingMode(boolean training) {
        this.training = training;
        enter = "";
        TextView pwdText = (TextView) this.activity.findViewById(R.id.pwd);
        pwdText.setText(enter);
    }

}