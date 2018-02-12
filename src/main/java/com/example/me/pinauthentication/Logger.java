package com.example.me.pinauthentication;

import android.os.Environment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public final class Logger {

   private Logger() {}


    public static void writeFile(String text) {

        File targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File logFile = new File(targetPath.getAbsolutePath() + "/", "pin_log.txt");
        if(!logFile.exists()) {
            try{
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(logFile, true));
            buffer.append(text);
            buffer.newLine();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void pinCsv(String text) {

        File targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File logFile = new File(targetPath.getAbsolutePath() + "/", "Pin_Csv.csv");
        if(!logFile.exists()) {
            try{
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(logFile, true));
            buffer.append(text);
            buffer.newLine();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void patternLog(String text) {

        File targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File logFile = new File(targetPath.getAbsolutePath() + "/", "pattern_log.txt");
        if(!logFile.exists()) {
            try{
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(logFile, true));
            buffer.append(text);
            buffer.newLine();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void patternCsv(String text) {

        File targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File logFile = new File(targetPath.getAbsolutePath() + "/", "Pattern_Csv.csv");
        if(!logFile.exists()) {
            try{
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(logFile, true));
            buffer.append(text);
            buffer.newLine();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void swipinLog(String text) {

        File targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File logFile = new File(targetPath.getAbsolutePath() + "/", "swipin_log.txt");
        if(!logFile.exists()) {
            try{
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(logFile, true));
            buffer.append(text);
            buffer.newLine();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void swipinCsv(String text) {

        File targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File logFile = new File(targetPath.getAbsolutePath() + "/", "swipin_Csv.csv");
        if(!logFile.exists()) {
            try{
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(logFile, true));
            buffer.append(text);
            buffer.newLine();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
