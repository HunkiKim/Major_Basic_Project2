package com.konkuk.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import com.konkuk.service.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import java.util.ArrayList;

import com.konkuk.dto.Log;

public class LogRepository extends Repository {
    private List<Log> logList;

    private LogRepository() {
        this.debugTitle = "log";
        if(isDataFileExists(Settings.DATA_LOG)) {
            logList = loadData(Settings.DATA_LOG, (parsedData, uniquePolicy) -> {
                int log_number = Integer.parseInt(parsedData.get(0));
                String log_category = parsedData.get(1);
                String log_content = parsedData.get(2);
                String Day = parsedData.get(3);
                if(uniquePolicy.contains(log_number)) {
                    Utils.exit(Langs.VIOLATE_UNIQUE_KEY);
                }
                return new Log(log_number, log_category, log_content, Day);
            });
        }
    }

    private static class Instance {
        private static final LogRepository instance = new LogRepository();
    }

    public static LogRepository getInstance() {
        return Instance.instance;
    }

    public static List<String> get_log () {
        Repository rp = new Repository();
        List<String> result = new ArrayList<>();
        try {
            File file = new File("log.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null) {
                result.add(rp.parseDataLine(line).get(0));
                result.add(rp.parseDataLine(line).get(1));
                result.add(rp.parseDataLine(line).get(2));
                result.add(rp.parseDataLine(line).get(3));
            }
            bufReader.close();


        }catch (FileNotFoundException e) {

        }catch (IOException e) {

        }
        return result;
    }

    public static int CountLine () { // 로그 번호를 저장하기 위해 log.txt 파일의 line 수를 count하여 return한다
        int linecount = 0;
        File file = new File("logs.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));

            while(in.readLine() != null) {
                linecount++;
            }
            in.close();
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
            int log_number1 = CountLine() + 1; // log.txt 파일의 line 수를 불러와 +1 한 값을 로그번호로 지정
            String log_number = Integer.toString(log_number1);
            writer.write("\"" + log_number + "\",");
            writer.write("\"" + category + "\",");
            writer.write("\"" + content + "\",");
            if (month1 < 10) { // 월이 10보다 작으면 앞에 0 을 붙여주기 위해서 따로 분류
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

