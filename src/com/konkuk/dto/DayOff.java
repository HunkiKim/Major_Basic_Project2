package com.konkuk.dto;

import com.konkuk.asset.Langs;

public class DayOff {
    public int num;
    public int id;
    public int dayOffNumber;
    public String reason;
    public String startDayOffNumber;
    public String endDayOffNumber;
    public String dateCreated;

    public DayOff(int num, int id, int dayOffNumber, String reason, String startDayOffNumber, String endDayOffNumber, String dateCreated) {
        this.num = num;
        this.id = id;
        this.dayOffNumber = dayOffNumber;
        this.reason = reason;
        this.startDayOffNumber = startDayOffNumber;
        this.endDayOffNumber = endDayOffNumber;
        this.dateCreated = dateCreated;
    }
    public static String getHeader(){
        return Langs.DATA_FILE_HEADER_DAYOFF;
    }
}
