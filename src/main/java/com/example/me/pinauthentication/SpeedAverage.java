package com.example.me.pinauthentication;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 12.02.2018.
 */

public final class SpeedAverage {

    public static float average(ArrayList<Float> speed) {
        int sum = 0;
        for (int i = 0; i < speed.size(); i++) {
            sum += speed.get(i);
        }
        if (speed.size() > 0) {
            return sum / speed.size();

        } else {
            Log.d("sensor no speed", "average: ");
            return 0;

        }
    }
}
