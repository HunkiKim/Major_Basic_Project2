package com.konkuk.dto;

import com.konkuk.asset.Langs;

import java.util.Date;

public class DayOff {
    public int num;
    public int id;
    public int dayOffNumber;
    public int count;
    public String name;
    public String reason;
    public Date dateDayOffStart;
    public Date dateDayOffEnd;
    public Date dateCreated;

    public DayOff(int id, String name, String reason, Date dateDayOffStart, Date dateDayOffEnd, int count){
        this.id = id;
        this.name = name;
        this.reason = reason;
        this.dateDayOffStart = dateDayOffStart;
        this.dateDayOffEnd = dateDayOffEnd;
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
}
