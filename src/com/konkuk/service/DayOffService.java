package com.konkuk.service;

import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;

public class DayOffService {
    DayOffRepository dayOffRepository = DayOffRepository.getInstance();
    private int list_num = 1;


    public boolean use(Employee employee, String reason, String start, String end) {
//        dayOffRepository.findByDate();
//        dayOffRepository.findByEmployeeId();
//        등의 기능을 사용해서 구현
        int id = employee.getId();
        String name = employee.getName();
        int fcount = employee.getResidualDayOff();

        // 오류나서 변경해놨어여 / 단기
        DayOff dayOff = null;
//        DayOff dayOff = new DayOff(list_num, id, name, reason, start, end, fcount);
        dayOffRepository.add(dayOff);
        list_num++;
        return false;
    }

    public boolean add(Employee employee, String reason, int count){
        int fcount = employee.getResidualDayOff() + count;
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

    public boolean reduct(Employee employee, String reason, int count){
        int fcount = employee.getResidualDayOff() - count;
        employee.setResidualDayOff(fcount);
        return false;
    }

}
