package com.example.me.pinauthentication;
import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.me.pinauthentication.R;

public class SwipeGestureConfirm extends GestureDetector.SimpleOnGestureListener implements OnTouchListener {
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    private boolean testTraining = true;
    private boolean training = true;
    public Activity activity;
    ConfirmSwiPIN act;
    GestureDetector gDetector;
    static detectedGesture dGesture;
    String field;
    float dim;
    String user;

    String enter = "";
    boolean newEntry = true;

    long time;
    long timed;
    long timeu;

    public enum detectedGesture {
        UP, RIGHT, DOWN, LEFT, DOT;
    }

    public SwipeGestureConfirm(ConfirmSwiPIN act) {
        this(act, null);
    }

    public SwipeGestureConfirm(ConfirmSwiPIN act, GestureDetector gDetector) {
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
                dGesture = detectedGesture.RIGHT;
            } else {

                dGesture = detectedGesture.LEFT;
            }
            findGesture(field, dGesture);
            return true;
        } else if (Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dY / dX) > 1) {

            if (dY > 0) {

                dGesture = detectedGesture.UP;
            } else {

                dGesture = detectedGesture.DOWN;
            }
            findGesture(field, dGesture);
            return true;
        }

        newEntry = false;
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        time = System.currentTimeMillis();
        dGesture = detectedGesture.DOT;
        findGesture(field, dGesture);
        return true;

    }

    @Override
    public boolean onTouch(View v, final MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                timed = System.currentTimeMillis();
                onDown(event);
                break;
            case (MotionEvent.ACTION_UP):
                timeu = System.currentTimeMillis();
                act.changeVisibility(field, true);
                break;
            default:
                return gDetector.onTouchEvent(event);
        }
        return gDetector.onTouchEvent(event);
    }

    private void findGesture(final String field, detectedGesture dG) {
        newEntry = false;
        TextView pwdText = (TextView) this.activity.findViewById(R.id.pwd1);
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
            Log.d("ccges01", "findGesture: "+dGesture);
            Log.d("ccges02", "findGesture: "+image.getTag());
            if (image.getTag().toString().equals(dGesture.toString())) {
                Log.d("ccges03", "findGesture: "+image.getTag());
                if (field.equals("r_")) {
                    switch (i) {
                        case 0:
                            enter += "3";
                            break;
                        case 1:
                            enter += "6";
                            break;
                        case 2:
                            enter += "8";
                            break;
                        case 3:
                            enter += "9";
                            break;
                        case 4:
                            enter += "0";
                            break;

                    }
                } else
                    switch (i) {
                        case 0:
                            enter += "1";
                            break;
                        case 1:
                            enter += "2";
                            break;
                        case 2:
                            enter += "4";
                            break;
                        case 3:
                            enter += "5";
                            break;
                        case 4:
                            enter += "7";
                            break;

                    }
                if (testTraining) {

                    pwdText.setText(enter);
                    if (enter.length() == 4 ){
                        act.pwdEntered(enter);
                        Logger.swipinLog("Creating SwiPIN");
                        //load the UserID
                        user = SwiPINActivity.UserID;
                        Logger.swipinLog("SwiPIN for user " + user + " has been created");
                        Logger.swipinLog("SwiPIN created is : " + enter);
                        Logger.swipinLog("------------------");
                    }
                } else if (training) {
                    pwdText.setText(enter);
                    if (enter.length() == 4) {
                        act.pwdEntered(enter);
                    }
                } else {

                    act.findCorrectGesture(enter, field);
                    pwdText.setText(pwdText.getText().toString() + "*", TextView.BufferType.EDITABLE);
                    if (enter.length() == 4) {
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
        TextView pwdText = (TextView) this.activity.findViewById(R.id.pwd1);
        pwdText.setText(enter);
        newEntry = true;
    }

}