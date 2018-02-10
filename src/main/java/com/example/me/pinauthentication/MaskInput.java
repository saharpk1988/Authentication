package com.example.me.pinauthentication;


import android.os.Handler;
import android.widget.EditText;

public final class MaskInput {

    public static String decode = "";

    static Handler handler = new Handler();

    public static void delay(final EditText text, int delay, final int size) {
         handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                text.setText(text.getText().toString().replaceAll("\\d", "*"));
                text.setTextSize(size);
                text.setSelection(text.length());
            }
        }, delay);


    }
}
