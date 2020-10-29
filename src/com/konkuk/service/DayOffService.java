package com.konkuk.service;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.Utils;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;
import com.konkuk.repository.EmployeeRepository;
import com.konkuk.repository.LogRepository;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DayOffService {
    public enum DayOffType {AllDay, HalfDay}

    DayOffRepository dayOffRepository = DayOffRepository.getInstance();
    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
    LogRepository logRepository = LogRepository.getInstance();

    private int list_num = 1;

    public DayOff getDayOff(int dayOffId) {
        return dayOffRepository.findByExactId(dayOffId);
    }

    public List<String> getUsedDayOffList(int employeeId) {
        List<DayOff> dayOffList = dayOffRepository.findByEmployeeId(employeeId);
        Employee employee = employeeRepository.findByExactId(employeeId);
        List<String> results = new ArrayList<>();   //결과
        dayOffList.forEach((dayOff -> {
            if(dayOff.dateDayOffStart != null) {
                results.add(dayOff.id + "  " + dayOff.employeeId + "  " + employee.name + "  " + dayOff.reason + "  " +
                        Utils.dateToString(dayOff.dateDayOffStart) + "  " + Utils.dateToString(dayOff.dateDayOffEnd));
            }
        }));
        return results;
    }

    public boolean reasonCheck(String reason){

        if(reason.getBytes().length<1 || reason.getBytes().length>512) {    //1~512바이트
            UI.print(Langs.REASON_ERROR);
            return false;
        }

        /*for (int i = 0; i < reason.length(); i++) { //알파벳이 아닐시, 이상한 문자일 경우 예외처리
            if (!(reason.charAt(i)>=65 && reason.charAt(i)<=90) &&
                    !(reason.charAt(i)>=97 && reason.charAt(i)<=122) && !(reason.charAt(i)>='가' && reason.charAt(i)<='힣')) {
                UI.print(Langs.LETTER_ERROR);
                return false;
            }
        }*/
        return true;
    }

    public boolean countCheck(float count){
        if(count>=-30 && count<=30){
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
            dayOff.changedDayOffCount = -1;
            employee.residualDayOff--;
        } else if(dayOffType == DayOffType.HalfDay){
            dayOff.changedDayOffCount = -0.5f;
            employee.residualDayOff = employee.residualDayOff - 0.5f;
        }
        try{
            dayOffRepository.add(dayOff);
            employeeRepository.update(employeeId, employee);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        [연차사용] 사원번호 : 1 사원이름 : 홍길동 잔여연차 : 26.0 연차시작일 : 2020/10/05 연차종료일 : 2020/10/08 연차기간 : 4
        logRepository.addLog("[연차사용] " ,
                "사원번호 : "+employee.getId()+
                        " 사원이름 : "+employee.getName()+
                        " 잔여연차 : "+employee.getResidualDayOff()+
                        " 연차시작일 : "+Utils.dateToString(dayOff.dateDayOffStart, "yyyyMMdd")+
                        " 연차종료일 : "+Utils.dateToString(dayOff.dateDayOffEnd, "yyyyMMdd")+
                        " 연차기간 : "+ Math.abs(dayOff.changedDayOffCount));
        return employee;
    }

    public Employee add(int employeeId, String reason, float count) {
        Employee employee = employeeRepository.findByExactId(employeeId);
        float originalResidualDayOff = employee.residualDayOff;
        float fcount = employee.residualDayOff + count;
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
            dayOff.dateCreated = new Date();
            dayOffRepository.add(dayOff);
            employeeRepository.update(employeeId, employee);
        } catch (IOException e) {
            return null;
        }
        logRepository.addLog("[연차추가] " ,
            "사원번호 : "+employee.getId()+
                    " 사원이름 : "+employee.getName()+
                    " 추가전잔여연차 : "+originalResidualDayOff+
                    " 추가후잔여연차 : "+employee.getResidualDayOff());
        return employee;
    }

    public DayOff change(int employeeId, int dayOffId, String reason, String start, String end){
        Employee employee = employeeRepository.findByExactId(employeeId);
        DayOff dayOff = dayOffRepository.findByExactId(dayOffId);
        Date originStartDate = dayOff.dateDayOffStart;
        Date originEndDate = dayOff.dateDayOffEnd;
        dayOff.reason = reason;
        dayOff.dateDayOffStart = Utils.stringToDate(start);
        dayOff.dateDayOffEnd = Utils.stringToDate(end);
        dayOffRepository.update(dayOffId, dayOff);

        float dayOffCounts = Math.abs(dayOff.changedDayOffCount);
        logRepository.addLog("[연차수정] " ,
                "사원번호 : "+employee.getId()+
                " 사원이름 : "+employee.getName()+
                " 잔여연차 : "+employee.getResidualDayOff()+
                " 연차시작일 : "+Utils.dateToString(originStartDate, "yyyyMMdd")+
                " 연차종료일 : "+Utils.dateToString(originEndDate, "yyyyMMdd")+
                " 연차기간 : "+dayOffCounts+
                " -> "+
                " 연차시작일 : "+Utils.dateToString(dayOff.dateDayOffStart, "yyyyMMdd")+
                " 연차종료일 : "+Utils.dateToString(dayOff.dateDayOffEnd, "yyyyMMdd")+
                " 연차기간 : "+ dayOffCounts);
        return dayOff;
    }

    public boolean cancel(int dayOffId) {
        dayOffRepository.delete(dayOffId);
        return true;
    }

    public Employee reduct(int employeeId, String reason, float count) {
        Employee employee = employeeRepository.findByExactId(employeeId);
        float originalResidualDayOff = employee.residualDayOff;
        float fcount = employee.residualDayOff - count;
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
        logRepository.addLog("[연차삭제] " ,
        "사원번호 : "+employee.getId()+
                " 사원이름 : "+employee.getName()+
                " 삭제전잔여연차 : "+originalResidualDayOff+
                " 삭제후잔여연차 : "+employee.getResidualDayOff());
        return employee;
    }
}
