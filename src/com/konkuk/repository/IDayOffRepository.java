package com.konkuk.repository;

import com.konkuk.dto.DayOff;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IDayOffRepository {
    public DayOff add(DayOff dayoff) throws IOException;
    public void delete(DayOff dayoff) throws IOException;
    public List<DayOff> findByEmployeeId(int employeeId);
    DayOff findByExactId(int id);
    List<DayOff> findByDate(int employeeId, Date start, Date end);
}
