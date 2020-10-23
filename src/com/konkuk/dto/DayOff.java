package com.konkuk.dto;

import com.konkuk.asset.Langs;

import java.util.Date;

public class DayOff {
    public int id;
    public int employeeId;
    public int dayOffNumber;
    public int residualDayOff;
    public String name;
    public String reason;
    public String start;
    public String end;
    public Date dateDayOffStart;
    public Date dateDayOffEnd;
    public Date dateCreated;

    public DayOff() {}

    public DayOff(int id, int employeeId, String name, String reason, Date dateDayOffStart, Date dateDayOffEnd, int residualDayOff){
        this.id = id;
        this.employeeId = employeeId;
        this.name = name;
        this.reason = reason;
        this.dateDayOffStart = dateDayOffStart;
        this.dateDayOffEnd = dateDayOffEnd;
        this.residualDayOff = residualDayOff;
    }

    public DayOff(int id, int employeeId, int dayOffNumber, String reason, Date dateDayOffStart, Date dateDayOffEnd, Date dateCreated) {
        this.id = id;
        this.employeeId = employeeId;
        this.dayOffNumber = dayOffNumber;
        this.reason = reason;
        this.dateDayOffStart = dateDayOffStart;
        this.dateDayOffEnd = dateDayOffEnd;
        this.dateCreated = dateCreated;
    }
    public static String getHeader(){
        return Langs.DATA_FILE_HEADER_DAYOFF;
    }

    public String getReason() { return reason; }
    public String getStart() { return start; }
    public String getEnd() { return end; }
    public void setReason(String reason) { this.reason = reason; }
    public void setStart(String start) { this.start = start; }
    public void setEnd(String end) { this.end = end; }

}
