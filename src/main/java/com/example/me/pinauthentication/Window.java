package com.example.me.pinauthentication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class Window extends View {
    final static int DIMENSION = 80;

    Rect rect;

    Paint paintLines;
    float[] frame_red1;
    float[] frame_red2;
    float[] frame_red3;
    float[] frame_red4;
    float[] frame_yellow1;
    float[] frame_yellow2;
    float[] frame_yellow3;
    float[] frame_yellow4;

    private float width, height;

    public Window(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintLines = new Paint();

        rect = new Rect(0, 0, DIMENSION * 3, DIMENSION * 4);

        initLines();
    }

    private void initLines() {
        frame_red1 = new float[4];
        frame_red2 = new float[4];
        frame_red3 = new float[4];
        frame_red4 = new float[4];
        frame_yellow1 = new float[4];
        frame_yellow2 = new float[4];
        frame_yellow3 = new float[4];
        frame_yellow4 = new float[4];
    }

    @Override
    public void onDraw(Canvas canvas) {
        rect.set(0, 0, getWidth(), getHeight());
        drawLines(canvas);
    }

    private void drawLines(Canvas canvas) {
        width = this.getWidth() / 2;
        height = this.getHeight();
        float d = width / 7;

        // Rahmen rot oben
        frame_red1[0] = 0;
        frame_red1[1] = 0;
        frame_red1[2] = width;
        frame_red1[3] = 0;

        // Rahmen rot links
        frame_red2[0] = width - d / 4;
        frame_red2[1] = 0;
        frame_red2[2] = width - d / 4;
        frame_red2[3] = height;

        // Rahmen rot unten
        frame_red3[0] = 0;
        frame_red3[1] = height;
        frame_red3[2] = width;
        frame_red3[3] = height;

        // Rahmen rot vertikal 1
        frame_red4[0] = 0;
        frame_red4[1] = 0;
        frame_red4[2] = 0;
        frame_red4[3] = height;

        // Rahmen gelb oben
        frame_yellow1[0] = width;
        frame_yellow1[1] = 0;
        frame_yellow1[2] = width * 2;
        frame_yellow1[3] = 0;

        // Rahmen gelb rechts
        frame_yellow2[0] = width * 2;
        frame_yellow2[1] = 0;
        frame_yellow2[2] = width * 2;
        frame_yellow2[3] = height;

        // Rahmen gelb unten
        frame_yellow3[0] = width * 2;
        frame_yellow3[1] = height;
        frame_yellow3[2] = width;
        frame_yellow3[3] = height;

        // Rahmen gelb vertikal 1
        frame_yellow4[0] = width + d / 4;
        frame_yellow4[1] = height;
        frame_yellow4[2] = width + d / 4;
        frame_yellow4[3] = 0;

        // roter rahmen zeichnen
        paintLines.setStrokeWidth(d);
        paintLines.setColor(Color.RED);
        canvas.drawLines(frame_red1, paintLines);
        canvas.drawLines(frame_red3, paintLines);
        canvas.drawLines(frame_red4, paintLines);
        paintLines.setStrokeWidth(d / 2);
        canvas.drawLines(frame_red2, paintLines);

        // gelber rahmen zeichnen
        paintLines.setStrokeWidth(d);
        paintLines.setColor(Color.YELLOW);
        canvas.drawLines(frame_yellow1, paintLines);
        canvas.drawLines(frame_yellow2, paintLines);
        canvas.drawLines(frame_yellow3, paintLines);
        paintLines.setStrokeWidth(d / 2);
        canvas.drawLines(frame_yellow4, paintLines);
    }

    public float getWid() {
        return width;
    }
}
