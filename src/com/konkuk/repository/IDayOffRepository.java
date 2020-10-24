package com.konkuk.repository;

import com.konkuk.dto.DayOff;

import java.util.Date;
import java.util.List;

public interface IDayOffRepository {
    public DayOff add(DayOff dayoff);
    public boolean delete(DayOff dayoff);
    public List<DayOff> findByEmployeeId(int employeeId);
    public List<DayOff> findByDate(Date start, Date end);
    List<DayOff> findByDate(int employeeId, Date start, Date end);
}
