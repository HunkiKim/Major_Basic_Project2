package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DayOffHistoryRepository extends Repository implements IDayOffHistoryRepository {

    List<DayOff> dayOffs = new ArrayList<>();

    private DayOffHistoryRepository(String dataFilePath) {
        super(dataFilePath);
        this.debugTitle = "DayOffHistory";
        if (isDataFileExists()) {
            dayOffs = loadData((parsedData, uniquePolicy) -> {
                int id = Integer.parseInt(parsedData.get(0));
                int employeeId = Integer.parseInt(parsedData.get(1));
                int dayOffNumber = Integer.parseInt(parsedData.get(2));
                String reason = parsedData.get(4);
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
                try{
                    Date dateDayOffStart = transFormat.parse(parsedData.get(4));
                    Date dateDayOffEnd = transFormat.parse(parsedData.get(5));
                    Date dateCreated = transFormat.parse(parsedData.get(6));
                    return new DayOff(id, employeeId, dayOffNumber, reason, dateDayOffStart, dateDayOffEnd, dateCreated);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            });
        } else {
            createEmptyDataFile(Employee.getHeader());
        }
    }

    private static class Instance {
        private static final DayOffHistoryRepository instance = new DayOffHistoryRepository(Settings.DATA_DAYOFF);
    }
    public static DayOffHistoryRepository getInstance() {
        return DayOffHistoryRepository.Instance.instance;
    }

    @Override
    public List<DayOff> findByDate(int employeeId, Date start, Date end){
        // 해당 사번의 연차 시작 연도부터 끝나는 연도까지의 데이터 불러오기
        List<DayOff> results = new ArrayList<>();;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy");
        int startYear = Integer.parseInt(simpleDate.format(start));
        int endYear = Integer.parseInt(simpleDate.format(end));

        Calendar calendar = Calendar.getInstance();
        dayOffs.forEach((dayOff -> {
            calendar.setTime(dayOff.dateDayOffStart);
            int tempYear = calendar.get(Calendar.YEAR);
            if(dayOff.employeeId == employeeId && tempYear>=startYear&&tempYear<=endYear) {
                results.add(dayOff);
            };
        }));
        return results;
    }
}
