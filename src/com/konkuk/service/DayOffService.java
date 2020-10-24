package com.konkuk.service;

import com.konkuk.Utils;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;
import com.konkuk.repository.EmployeeRepository;

import java.io.IOException;
import java.util.Date;

public class DayOffService {
    public enum DayOffType {AllDay, HalfDay}

    DayOffRepository dayOffRepository = DayOffRepository.getInstance();
    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
    private int list_num = 1;
    private float fcount = 0;

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

    public boolean add(Employee employee, String reason, float count){
        fcount = employee.getResidualDayOff() + count;
        employee.setResidualDayOff(fcount);
        return false;
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

    public boolean reduct(Employee employee, String reason, float count){
        fcount = employee.getResidualDayOff() - count;
        employee.setResidualDayOff(fcount);
        return false;
    }
}
