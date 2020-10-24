package com.konkuk;

import com.konkuk.asset.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static void debug(String msg) {
        if(Settings.DEBUG) System.out.println("Debug: " + msg);
    }

    public static void exit(String msg) {
        debug("강제 종료");
        debug(msg);
        System.exit(0);
    }

    public static Date stringToDate(String string) {
        return stringToDate(string, "yyyyMMdd HH:mm");
    }

    public static Date stringToDate(String string, String format) {
        Date result = null;
        SimpleDateFormat transFormat = new SimpleDateFormat(format);
        try {
            result = transFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String dateToString(Date date) {
        return dateToString(date, "yyyyMMdd HH:mm");
    }
    public static String dateToString(Date date, String format) {
        SimpleDateFormat transFormat = new SimpleDateFormat(format);
        return transFormat.format(date);
    }

    public static boolean isValidationDate(String checkDate){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

            dateFormat.setLenient(false);
            dateFormat.parse(checkDate);
            return true;
        }catch (ParseException e){
            return false;
        }
    }
}
