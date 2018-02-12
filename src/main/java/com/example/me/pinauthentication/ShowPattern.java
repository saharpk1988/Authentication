package com.example.me.pinauthentication;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ⚪⚫⚪
 * Created by Me on 05.02.2018.
 */

public class ShowPattern {


    public static  String[][] showPat( List<Integer> arr ){
           String[][]newArr= new String[3][3];
            //Iterate through the arraylist
            if(arr.contains(1)){
                newArr[0][0]="1 "+"\t";
                }
            if(arr.contains(2)){
                newArr[0][1]="2 "+"\t";

                }
            if(arr.contains(3)){
                newArr[0][2]="3 "+"\t";

                 }
            if(arr.contains(4)){
                newArr[1][0]="4 "+"\t";

                }
            if(arr.contains(5)){
                newArr[1][1]="5 "+"\t";

                }
             if(arr.contains(6)){
                newArr[1][2]="6 "+"\t";

                }
             if(arr.contains(7)){
                 newArr[2][0]="7 "+"\t";

                }
             if(arr.contains(8)){
                 newArr[2][1]="8 "+"\t";

                }
            if(arr.contains(9)){
                 newArr[2][2]="9 "+"\t";

                }
        for (int row = 0; row < newArr.length; row++) {
            for (int col = 0; col < newArr[row].length; col++) {
                if (newArr[row][col] == null) {
                    newArr[row][col] = "•"+"\t"+"\t";

                }
            }
        }
        return  newArr;


    }
}
