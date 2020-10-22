package com.konkuk.repository;

import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayOffHistoryRepository extends Repository implements IDayOffHistoryRepository {

    private List<DayOff> dayOffList;

    private static class Instance {
        private static final DayOffHistoryRepository instance = new DayOffHistoryRepository();
    }
    public static DayOffHistoryRepository getInstance() {
        return DayOffHistoryRepository.Instance.instance;
    }

    public int getSize(){
        return this.dayOffList.size();
    }

    @Override
    public List<DayOff> findByEmployeeId(int employeeId){
        // 해당 사번의 이번 연도 연차 내역 데이터만 불러오기
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
    public List<DayOff> findByDate(int employeeId, Date start, Date end){
        // 해당 사번의 연차 시작 연도부터 끝나는 연도까지의 데이터 불러오기
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
