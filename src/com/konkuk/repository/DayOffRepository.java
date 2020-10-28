package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;

import java.io.IOException;
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
            dayOffList = loadData((parsedData) -> {
                int id = Integer.parseInt(parsedData.get(0));
                int employeeId = Integer.parseInt(parsedData.get(1));
                float changedDayOffCount = Float.parseFloat(parsedData.get(2));
                if(uniquePolicy.contains(id)) {
                    Utils.exit(this.debugTitle + " - " + Langs.VIOLATE_UNIQUE_KEY);
                }
                uniquePolicy.add(id);
                String reason = parsedData.get(3);
                Date dateDayOffStart = Utils.stringToDate(parsedData.get(4));
                Date dateDayOffEnd = Utils.stringToDate(parsedData.get(5));
                Date dateCreated = Utils.stringToDate(parsedData.get(6));
                DayOff dayOff = new DayOff();
                dayOff.id = id;
                dayOff.employeeId = employeeId;
                dayOff.changedDayOffCount = changedDayOffCount;
                dayOff.reason = reason;
                dayOff.dateDayOffStart = dateDayOffStart;
                dayOff.dateDayOffEnd = dateDayOffEnd;
                dayOff.dateCreated = dateCreated;
                return dayOff;
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


    private int maxId = -1;
    @Override
    public DayOff add(DayOff dayoff) throws IOException {
        if (maxId == -1) {
            dayOffList.forEach((e -> maxId = Math.max(maxId, e.id)));
        }
        dayoff.id = ++maxId;
        addDataLine(parseDtoToList(dayoff));
        dayOffList.add(dayoff);
        return dayoff;
    }

    @Override
    public DayOff update(int dayOffId, DayOff dayOff) throws IOException {
        deleteDataLine(dayOffId);
        dayOff.id = dayOffId;
        addDataLine(parseDtoToList(dayOff));
        for(int i = 0; i < dayOffList.size(); i++) {
            if(dayOffList.get(i).id == dayOffId) {
                dayOffList.set(i, dayOff);
                break;
            }
        }
        return dayOff;
    }

    @Override
    public DayOff findByExactId(int id) {
        DayOff result = null;
        for (DayOff e : dayOffList) {
            if (e.id == id) {
                result = e;
                break;
            }
        }
        return result;
    }

    @Override
    public void delete(int dayOffId) throws IOException {
        deleteDataLine(dayOffId);
        for(int i = 0; i < dayOffList.size(); i++) {
            if(dayOffList.get(i).id == dayOffId) {
                dayOffList.remove(i);
                break;
            }
        }
    }

    @Override
    public List<DayOff> findByEmployeeId(int employeeId) {
        List<DayOff> results = new ArrayList<>();
        dayOffList.forEach((dayOff -> {
           if(dayOff.employeeId == employeeId) {
                results.add(dayOff);
            };
        }));
        return results;
    }

    @Override
    public DayOff findByDate(int employeeId, Date start){
        DayOff result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DATE);
        for(DayOff e : dayOffList) {
            if(e.dateDayOffStart == null) continue;
            calendar.setTime(e.dateDayOffStart);
            if(e.employeeId == employeeId &&
                    calendar.get(Calendar.YEAR) == y &&
                    calendar.get(Calendar.MONTH) == m &&
                    calendar.get(Calendar.DATE) == d
            ) {
                result = e;
                break;
            }
        }
        return result;
    }

    @Override
    public List<DayOff> findByDate(int employeeId, Date start, Date end, int option){
        // 해당 사번의 연차 시작 연도부터 끝나는 연도까지의 데이터 불러오기
        List<DayOff> results = new ArrayList<>();
        int startYear;
        int endYear;

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");

        startYear = Integer.parseInt(simpleDate.format(start));
        endYear = Integer.parseInt(simpleDate.format(end));

        if(option==1){
            simpleDate = new SimpleDateFormat("yyyy");
            startYear = Integer.parseInt(simpleDate.format(start)+"0000");
            endYear = Integer.parseInt(simpleDate.format(end)+"0000");
        }

        Calendar calendar = Calendar.getInstance();
        int finalStartYear = startYear;
        int finalEndYear;
        if(option==1)
            finalEndYear = endYear+10000;
        else
            finalEndYear = endYear;
        dayOffList.forEach((dayOff -> {
            if(dayOff.dateDayOffStart == null) return;
            calendar.setTime(dayOff.dateDayOffStart);
            int tempYear = calendar.get(Calendar.YEAR)*10000+(calendar.get(Calendar.MONTH)+1)*100+calendar.get(Calendar.DAY_OF_MONTH);
            if(dayOff.employeeId == employeeId && tempYear>= finalStartYear &&tempYear<= finalEndYear) {
                results.add(dayOff);
            }
        }));
        return results;
    }

    private List<String> parseDtoToList(DayOff dayOff) {
        List<String> result = new ArrayList<>();
        result.add(String.valueOf(dayOff.id));
        result.add(String.valueOf(dayOff.employeeId));
        result.add(String.valueOf(dayOff.changedDayOffCount));
        result.add(dayOff.reason);
        result.add(Utils.dateToString(dayOff.dateDayOffStart));
        result.add(Utils.dateToString(dayOff.dateDayOffEnd));
        result.add(Utils.dateToString(dayOff.dateCreated));
        return result;
    }
}
