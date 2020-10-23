package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;
import com.konkuk.asset.Settings;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayOffRepository extends Repository implements IDayOffRepository {

    private List<DayOff> dayOffList;

    private DayOffRepository(String dataFilePath) {
        super(dataFilePath);
        this.debugTitle = "DayOff";
        if (isDataFileExists()) {
//            dayOffList
        } else {
            createEmptyDataFile(DayOff.getHeader());
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
        results.add(new DayOff(
                0,
                1,
                3,
                "휴가사유",
                new Date(0),
                new Date(0),
                new Date(0)));
        return results;
    }

    @Override
    public List<DayOff> findByDate(Date start, Date end) {
        List<DayOff> results = new ArrayList<>();
        results.add(new DayOff(
                0,
                1,
                3,
                "휴가사유",
                new Date(0),
                new Date(0),
                new Date(0)));
        return results;
    }
}
