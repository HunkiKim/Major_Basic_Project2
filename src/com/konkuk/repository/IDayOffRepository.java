package com.konkuk.repository;

import com.konkuk.dto.DayOff;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IDayOffRepository {
    public DayOff add(DayOff dayoff) throws IOException;
    public boolean delete(DayOff dayoff);
    public List<DayOff> findByEmployeeId(int employeeId);
    List<DayOff> findByDate(int employeeId, Date start, Date end);
}
