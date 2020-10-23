package com.konkuk.dto;

import com.konkuk.asset.Langs;

public class Log {
    public int log_number;
    public String log_category;
    public String log_content;
    public String Day;

    public Log(int log_number, String log_category, String log_content, String Day) {
        this.log_number = log_number;
        this.log_category = log_category;
        this.log_content = log_content;
        this.Day = Day;
    }

    public static String getHeader() {
        return Langs.DATA_FILE_HEADER_LOG;
    }
}
