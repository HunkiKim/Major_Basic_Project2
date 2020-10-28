package com.konkuk.repository;

import com.konkuk.dto.DayOff;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IDayOffRepository {
    DayOff add(DayOff dayoff) throws IOException;
    void delete(int dayOffId);
    List<DayOff> findByEmployeeId(int employeeId);
    void update(int dayOffId, DayOff dayOff);
    DayOff findByExactId(int id);
    DayOff findByDate(int employeeId, Date start);
    List<DayOff> findByDate(int employeeId, Date start, Date end, int option);
}
