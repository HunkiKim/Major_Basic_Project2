package com.konkuk;

import com.konkuk.asset.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

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

    public static boolean isOnlyNumber(String checkString) {
        String pattern = "^[0-9]*$";
        return Pattern.matches(pattern, checkString);
    }

    public enum InputType {NUMERIC, LETTER, MIXED}
    public static InputType getInputType(String target) { //숫자와 문자가 섞여있는지 홧인
        if(target.charAt(0) >= 48 && target.charAt(0) <= 57) { //첫 문자가 숫자일경우
            for (int i = 1; i < target.length(); i++) {
                if (target.charAt(i) < 48 || target.charAt(i) > 58) { // 범위내에 숫자만 있는지 확인
                    return InputType.MIXED;// 숫자사이에 문자가 껴있다면
                }
            }
        }//숫자인지 확인 끝
        else { //문자일경우
            for (int i = 1; i < target.length(); i++) {
                if (target.charAt(i) >= 48 && target.charAt(i) <= 57) { // 문자인지
                    return InputType.MIXED; // 문자사잉에 숫자가 껴있다면
                }
            }
        }
        try {
            int t = Integer.parseInt(target);
            return InputType.NUMERIC;
        } catch (NumberFormatException e) {
            return InputType.LETTER;
        }
        //여기까지 숫자와 문자가 섞여있는지 확인
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
