package com.konkuk.repository;

import com.konkuk.dto.DayOff;
import java.util.Date;
import java.util.List;
public interface IDayOffHistoryRepository {
    List<DayOff> findByDate(int employeeId, Date start, Date end);
}
