package com.konkuk.service;

import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;
import com.konkuk.repository.EmployeeRepository;

public class DayOffService {
    public enum DayOffType {AllDay, HalfDay}

    DayOffRepository dayOffRepository = DayOffRepository.getInstance();
    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
    private int list_num = 1;
    private float fcount = 0;

    public boolean use(int employeeId, DayOffType dayOffType, String reason, String start, String end) {
//        DayOff dayOff = new DayOff();
//        dayOff.employeeId = employeeId;
//        dayOff.residualDayOff =
//        dayOff.reason = reason;
//        dayOff.dateDayOffStart = start;
//        dayOff.dateDayOffEnd = end;
//        dayOff.dateCreated =
//
//
//
//        Employee employee = employeeRepository.findByExactId(employeeId);
//        if(employee == null) return false;
//
//        if(dayOffType == DayOffType.AllDay){
//            employee.residualDayOff--;
//        } else if(dayOffType == DayOffType.HalfDay){
//            employee.residualDayOff = employee.residualDayOff - 0.5f;
//        }
//
//        DayOff dayOff = new DayOff();
//        // 오류나서 변경해놨어여 / 단기
//        DayOff dayOff = null;
//        DayOff dayOff = new DayOff(list_num, id, name, reason, start, end, fcount);
//        dayOffRepository.add(dayOff);
        list_num++;
        return false;
    }

    public boolean add(Employee employee, String reason, float count){
        fcount = employee.getResidualDayOff() + count;
        employee.setResidualDayOff(fcount);
        return false;
    }

    public boolean change(DayOff dayOff, String reason, String start, String end){
//        dayOff.setReason(reason);
//        dayOff.setStart(start);
//        dayOff.setEnd(end);
        dayOffRepository.add(dayOff);
        return false;
    }

    public boolean cancel(DayOff dayOff){
        dayOffRepository.delete(dayOff);
        return false;
    }

    public boolean reduct(Employee employee, String reason, float count){
        fcount = employee.getResidualDayOff() - count;
        employee.setResidualDayOff(fcount);
        return false;
    }
}
