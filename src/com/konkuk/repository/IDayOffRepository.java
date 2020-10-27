package com.konkuk.repository;

import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IDayOffRepository {
    public DayOff add(DayOff dayoff) throws IOException;
    public void delete(DayOff dayoff) throws IOException;
    public List<DayOff> findByEmployeeId(int employeeId);
    public DayOff update(int dayOffId, DayOff dayOff) throws IOException;
    DayOff findByExactId(int id);
    DayOff findByDate(int employeeId, Date start);
    List<DayOff> findByDate(int employeeId, Date start, Date end, int option);
}
