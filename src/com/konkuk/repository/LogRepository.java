package com.konkuk.repository;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogRepository {

    public static int CountLine () { // 로그 번호를 저장하기 위해 log.txt 파일의 line 수를 count하여 return한다
        int linecount = 0;
        File file = new File("logs.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));

            while(in.readLine() != null) {
                linecount++;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return linecount;
    }

    public static void Log_Storing(String category, String content) {

        File file = new File("log.txt");
        FileWriter writer = null;
        Calendar cal = Calendar.getInstance();

        //UTC + 9(KST) 기준으로 날짜 및 시간을 불러와 문자열로 변환해 저장
        int year1 = cal.get(Calendar.YEAR);
        String year = Integer.toString(year1);
        int month1 = cal.get(Calendar.MONTH) + 1;
        String month = Integer.toString(month1);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);
        String day = Integer.toString(day1);
        int hour1 = cal.get(Calendar.HOUR_OF_DAY);
        String hour = Integer.toString(hour1);
        int min1 = cal.get(Calendar.MINUTE);
        String min = Integer.toString(min1);
        int sec1 = cal.get(Calendar.SECOND);
        String sec = Integer.toString(sec1);

        try {
            writer = new FileWriter(file, true);
            int log_number1 = CountLine() + 1; // log.txt 파일의 line 수를 불러와 +1 한 값을 로그번호로 지
            String log_number = Integer.toString(log_number1);
            writer.write("\"" + log_number + "\",");
            writer.write("\"" + category + "\",");
            writer.write("\"" + content + "\",");
            if (month1 < 10) {
                writer.write("\"" + year + "0" + month + day + " " + hour + ":" + min + "\",");
                writer.flush();
            }
            else {
                writer.write("\"" + year + month + day + " " + hour + ":" + min + "\"\n");
                writer.flush();
            }

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}

