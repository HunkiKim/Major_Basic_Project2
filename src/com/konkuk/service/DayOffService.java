package com.konkuk.service;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.Utils;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;
import com.konkuk.repository.EmployeeRepository;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DayOffService {
    public enum DayOffType {AllDay, HalfDay}

    DayOffRepository dayOffRepository = DayOffRepository.getInstance();
    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();

    private int list_num = 1;

    public List<String> getList(int employeeId) {
        List<DayOff> dayOffList = dayOffRepository.findByEmployeeId(employeeId);
        Employee employee = employeeRepository.findByExactId(employeeId);
        List<String> results = new ArrayList<>();   //결과
        dayOffList.forEach((dayOff -> {
            results.add(dayOff.id + "  " + dayOff.employeeId + "  " + employee.name + "  " + dayOff.reason + "  " +
                    dayOff.dateDayOffStart + "  " + dayOff.dateDayOffEnd);
        }));
        return results;
    }

    public boolean reasonCheck(String reason){

        if(reason.getBytes().length<1 || reason.getBytes().length>512) {    //1~512바이트
            UI.print(Langs.REASON_ERROR);
            return false;
        }

        for (int i = 0; i < reason.length(); i++) { //알파벳이 아닐시, 이상한 문자일 경우 예외처리
            if ((reason.charAt(i)>=65 && reason.charAt(i)<=90) ||
                    (reason.charAt(i)>=97 && reason.charAt(i)<=122) || (reason.charAt(i)>='가' && reason.charAt(i)<='힣')) {
                return true;
            } else {
                UI.print(Langs.LETTER_ERROR);
                return false;
            }
        }
        return true;
    }

    public boolean countCheck(float count){
        if(count>-30 && count<30){
            return true;
        }
        else {
            UI.print(Langs.COUNT_ERROR);
            return false;
        }
    }

    public Employee use(int employeeId, DayOffType dayOffType, String reason, String start, String end) {
        DayOff dayOff = new DayOff();
        dayOff.employeeId = employeeId;
        dayOff.reason = reason;
        dayOff.dateDayOffStart = Utils.stringToDate(start);
        dayOff.dateDayOffEnd = Utils.stringToDate(end);
        if(dayOff.dateDayOffStart == null || dayOff.dateDayOffEnd == null)
            return null;
        dayOff.dateCreated = new Date();
        dayOff.changedDayOffCount = 0;

        Employee employee = employeeRepository.findByExactId(employeeId);
        if(employee == null) return null;

        if(dayOffType == DayOffType.AllDay){
            dayOff.changedDayOffCount = 1;
            employee.residualDayOff--;
        } else if(dayOffType == DayOffType.HalfDay){
            dayOff.changedDayOffCount = 0.5f;
            employee.residualDayOff = employee.residualDayOff - 0.5f;
        }
        try{
            dayOffRepository.add(dayOff);
            employeeRepository.update(employeeId, employee);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return employee;
    }

    public Employee add(int employeeId, String reason, float count) {
        Employee employee = employeeRepository.findByExactId(employeeId);
        float fcount = employee.getResidualDayOff() + count;
        if(fcount>365 || fcount<-365){
            UI.print(Langs.FCOUNT_ERROR);
            return null;
        }
        employee.residualDayOff = fcount;
        try {
            DayOff dayOff = new DayOff();
            dayOff.reason = reason;
            dayOff.employeeId = employee.id;
            dayOff.changedDayOffCount = count;
            dayOffRepository.add(dayOff);
            employeeRepository.update(employeeId, employee);
        } catch (IOException e) {
            return null;
        }
        return employee;
    }

    public boolean change(DayOff dayOff, String reason, String start, String end){
//        dayOff.setReason(reason);
//        dayOff.setStart(start);
//        dayOff.setEnd(end);
//        dayOffRepository.add(dayOff);
        return false;
    }

    public boolean cancel(DayOff dayOff){
        try {
            dayOffRepository.delete(dayOff);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
