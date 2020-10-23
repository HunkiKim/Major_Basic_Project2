package com.konkuk.dto;

import com.konkuk.asset.Langs;

import java.util.Date;

public class DayOff {
    public int num;
    public int id;
    public int dayOffNumber;
    public float residualDayOff;
    public String name;
    public String reason;
    public String start;
    public String end;
    public Date dateDayOffStart;
    public Date dateDayOffEnd;
    public Date dateCreated;

    public DayOff() {}

    public DayOff(int dayOffNumber, int id, String name, String reason, Date dateDayOffStart, Date dateDayOffEnd, float residualDayOff){
        this.dayOffNumber = dayOffNumber;
        this.id = id;
        this.name = name;
        this.reason = reason;
        this.dateDayOffStart = dateDayOffStart;
        this.dateDayOffEnd = dateDayOffEnd;
        this.residualDayOff = residualDayOff;
    }

    public DayOff(int num, int id, int dayOffNumber, String reason, Date dateDayOffStart, Date dateDayOffEnd, Date dateCreated) {
        this.num = num;
        this.id = id;
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
