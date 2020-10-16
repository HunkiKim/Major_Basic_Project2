package com.konkuk.dto;

public class Employee {
    public int id;
    public String name;
    public int salary;
    public int residualDayOff;

    public static String getHeader() {
        return "\"사번\",\"이름\",\"연봉\",\"잔여 연차\"";
    }
}
