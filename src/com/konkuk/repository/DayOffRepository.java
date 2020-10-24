package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayOffRepository extends Repository implements IDayOffRepository {

    private List<DayOff> dayOffList;

    private DayOffRepository(String dataFilePath) {
        super(dataFilePath);
        this.debugTitle = "DayOff";
        if (isDataFileExists()) {
            dayOffList = loadData((parsedData, uniquePolicy) -> {
                int id = Integer.parseInt(parsedData.get(0));
                int employeeId = Integer.parseInt(parsedData.get(1));
                int changedDayOffCount = Integer.parseInt(parsedData.get(2));
                if(uniquePolicy.contains(id)) {
                    Utils.exit(Langs.VIOLATE_UNIQUE_KEY);
                }
                String reason = parsedData.get(4);
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
                try{
                    Date dateDayOffStart = transFormat.parse(parsedData.get(4));
                    Date dateDayOffEnd = transFormat.parse(parsedData.get(5));
                    Date dateCreated = transFormat.parse(parsedData.get(6));
                    DayOff dayOff = new DayOff();
                    dayOff.id = id;
                    dayOff.employeeId = employeeId;
                    dayOff.changedDayOffCount = changedDayOffCount;
                    dayOff.reason = reason;
                    dayOff.dateDayOffStart = dateDayOffStart;
                    dayOff.dateDayOffEnd = dateDayOffEnd;
                    dayOff.dateCreated = dateCreated;
                    return dayOff;
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
        private static final DayOffRepository instance = new DayOffRepository(Settings.DATA_DAYOFF);
    }

    public static DayOffRepository getInstance() {
        return Instance.instance;
    }


    @Override
    public DayOff add(DayOff dayoff) {
        return dayoff;
    }

    @Override
    public boolean delete(DayOff dayoff) { return true; }

    @Override
    public List<DayOff> findByEmployeeId(int employeeId) {
        List<DayOff> results = new ArrayList<>();
//        results.add(new DayOff(
//                0,
//                1,
//                3,
//                "휴가사유",
//                new Date(0),
//                new Date(0),
//                new Date(0)));
        return results;
    }

    @Override
    public List<DayOff> findByDate(Date start, Date end) {
        List<DayOff> results = new ArrayList<>();
//        results.add(new DayOff(
//                0,
//                1,
//                3,
//                "휴가사유",
//                new Date(0),
//                new Date(0),
//                new Date(0)));
        return results;
    }

    @Override
    public List<DayOff> findByDate(int employeeId, Date start, Date end){
        // 해당 사번의 연차 시작 연도부터 끝나는 연도까지의 데이터 불러오기
        List<DayOff> results = new ArrayList<>();;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy");
        int startYear = Integer.parseInt(simpleDate.format(start));
        int endYear = Integer.parseInt(simpleDate.format(end));

        Calendar calendar = Calendar.getInstance();
        dayOffList.forEach((dayOff -> {
            calendar.setTime(dayOff.dateDayOffStart);
            int tempYear = calendar.get(Calendar.YEAR);
            if(dayOff.employeeId == employeeId && tempYear>=startYear&&tempYear<=endYear) {
                results.add(dayOff);
            };
        }));
        return results;
    }
}
