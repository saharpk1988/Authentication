package com.example.me.pinauthentication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.*;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.utils.PatternLockUtils;

public class PatternHandler {

     List<Integer> myList = new ArrayList<>();
    static Toast toast;

    public  List<Integer> patternHandler(List<PatternLockView.Dot> pattern) {
        if(!myList.isEmpty()){
            myList.clear();
        }
        for (int i = 0; i < pattern.size(); i++) {
            if (pattern.get(i).getRow() == 0 && pattern.get(i).getColumn() == 0) {
                myList.add(1);
            } else if (pattern.get(i).getRow() == 0 && pattern.get(i).getColumn() == 1) {
                myList.add(2);
            } else if (pattern.get(i).getRow() == 0 && pattern.get(i).getColumn() == 2) {
                myList.add(3);
            } else if (pattern.get(i).getRow() == 1 && pattern.get(i).getColumn() == 0) {
                myList.add(4);
            } else if (pattern.get(i).getRow() == 1 && pattern.get(i).getColumn() == 1) {
                myList.add(5);
            } else if (pattern.get(i).getRow() == 1 && pattern.get(i).getColumn() == 2) {
                myList.add(6);
            } else if (pattern.get(i).getRow() == 2 && pattern.get(i).getColumn() == 0) {
                myList.add(7);
            } else if (pattern.get(i).getRow() == 2 && pattern.get(i).getColumn() == 1) {
                myList.add(8);
            } else if (pattern.get(i).getRow() == 2 && pattern.get(i).getColumn() == 2) {
                myList.add(9);
            }
        }
        Log.d("list", "onComplete: " + myList);
        return myList;


    }

    public static void toastMessageHandler(Context cls, CharSequence message, int duration, int gravity, int xoffset, int yoffset) {

        toast = Toast.makeText(cls,  message , duration);
        toast.setGravity(gravity, xoffset, yoffset);
        View tView = toast.getView();
        tView.setBackgroundResource(R.drawable.textinputborder);
        toast.setView(tView);
        toast.show();
    }

}