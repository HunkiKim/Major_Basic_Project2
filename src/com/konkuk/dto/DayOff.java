package com.konkuk.dto;

import com.konkuk.asset.Langs;

import java.util.Date;

public class DayOff {
    public int id;
    public int employeeId;
    public float changedDayOffCount;
    public String reason;
    public Date dateDayOffStart;
    public Date dateDayOffEnd;
    public Date dateCreated;

    public static String getHeader(){
        return Langs.DATA_FILE_HEADER_DAYOFF;
    }
}
