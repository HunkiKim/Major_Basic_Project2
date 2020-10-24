package com.konkuk.service;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;
import com.konkuk.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class DayOffService {
    DayOffRepository dayOffRepository = DayOffRepository.getInstance();
    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();

    private int list_num = 1;
    private float fcount = 0;

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
            if (reason.charAt(i) < 65 || reason.charAt(i) > 122 || (reason.charAt(i)>90 && reason.charAt(i)<97)) {
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

    public boolean use(Employee employee, int type ,String reason, String start, String end) {
//        dayOffRepository.findByDate();
//        dayOffRepository.findByEmployeeId();
//        등의 기능을 사용해서 구현
        int id = employee.getId();
        String name = employee.getName();
        if(type==0){    //연차
            fcount = employee.getResidualDayOff() - 1.0f;
        } else if(type==1){     //반차
            fcount = employee.getResidualDayOff() - 0.5f;
        }

        // 오류나서 변경해놨어여 / 단기
        DayOff dayOff = null;
//        DayOff dayOff = new DayOff(list_num, id, name, reason, start, end, fcount);
        dayOffRepository.add(dayOff);
        list_num++;
        return false;
    }

    public boolean add(Employee employee, String reason, float count){
        fcount = employee.getResidualDayOff() + count;
        if(fcount>365 || fcount<-365){
            UI.print(Langs.FCOUNT_ERROR);
            return true;
        }
        employee.setResidualDayOff(fcount);
        return false;
    }

    public boolean change(DayOff dayOff, String reason, String start, String end){
        dayOff.setReason(reason);
        dayOff.setStart(start);
        dayOff.setEnd(end);
        dayOffRepository.add(dayOff);
        return false;
    }

    public boolean cancel(DayOff dayOff){
        dayOffRepository.delete(dayOff);
        return false;
    }

    public boolean reduct(Employee employee, String reason, float count){
        fcount = employee.getResidualDayOff() - count;
        if(fcount>365 || fcount<-365){
            UI.print(Langs.FCOUNT_ERROR);
            return true;
        }
        employee.setResidualDayOff(fcount);
        return false;
    }

}
