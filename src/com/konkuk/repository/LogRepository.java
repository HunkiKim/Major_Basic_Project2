package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.Employee;
import com.konkuk.dto.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LogRepository extends Repository {
    private List<Log> logList;

    private LogRepository(String dataFilePath) {
        super(dataFilePath);
        this.debugTitle = "Log";
        if(isDataFileExists()) {
            logList = loadData((parsedData, uniquePolicy) -> {
                int log_number = Integer.parseInt(parsedData.get(0));
                String log_category = parsedData.get(1);
                String log_content = parsedData.get(2);
                String Day = parsedData.get(3);
                if(uniquePolicy.contains(log_number)) {
                    Utils.exit(this.debugTitle + " - " + Langs.VIOLATE_UNIQUE_KEY);
                }
                uniquePolicy.add(log_number);
                return new Log(log_number, log_category, log_content, Day);
            });
        } else {
            createEmptyDataFile(Log.getHeader());
        }
    }

    private static class Instance {
        private static final LogRepository instance = new LogRepository(Settings.DATA_LOG);
    }

    public static LogRepository getInstance() {
        return Instance.instance;
    }

    public List<Log> getLogs() {
        return this.logList;
    }

    private List<String> parseDtoToList(Log log) {
        List<String> result = new ArrayList<>();
        result.add(String.valueOf(log.log_number));
        result.add(log.log_category);
        result.add(log.log_content);
        result.add(log.Day);
        return result;
    }

    public Log add(Log log) throws IOException {
        addDataLine(parseDtoToList(log));
        logList.add(log);
        return log;
    }

    private int maxId = -1;
    public void addLog(String category, String content) {
        if (maxId == -1) {
            logList.forEach((e -> maxId = Math.max(maxId, e.log_number)));
        }

        File file = new File("log.txt");
        FileWriter writer = null;
        Calendar cal = Calendar.getInstance();

        //UTC + 9(KST) 기준으로 날짜 및 시간을 불러와 문자열로 변환해 저장
        int year1 = cal.get(Calendar.YEAR);
        String year = Integer.toString(year1);
        int month1 = cal.get(Calendar.MONTH) + 1;
        String month;
        if (month1 < 10) {
            month = "0" + Integer.toString(month1);
        }
        else {
            month = Integer.toString(month1);
        }
        int day1 = cal.get(Calendar.DAY_OF_MONTH);
        String day;
        if (day1 < 10) {
            day = "0" + Integer.toString(day1);
        }
        else {
            day = Integer.toString(day1);
        }
        int hour1 = cal.get(Calendar.HOUR_OF_DAY);
        String hour;
        if (hour1 < 10) {
            hour = "0" + Integer.toString(hour1);
        }
        else {
            hour = Integer.toString(hour1);
        }
        int min1 = cal.get(Calendar.MINUTE);
        String min;
        if (min1 < 10) {
            min = "0" + Integer.toString(min1);
        }
        else {
            min = Integer.toString(min1);
        }

        try {
            int log_number1 = ++maxId;; // log.txt 파일의 line 수를 불러와 +1 한 값을 로그번호로 지정
            String log_number = Integer.toString(log_number1);
            String Day = year + month + day + " " + hour + ":" + min;
            Log log = new Log(log_number1, category, content, Day);
            add(log);
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

