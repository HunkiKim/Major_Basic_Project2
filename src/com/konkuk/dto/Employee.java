package com.konkuk.dto;

public class Employee {
    int id;
    String name;
    int salary;
    int residualDayOff;

    public static String getHeader() {
        return "\"사번\",\"이름\",\"연봉\",\"잔여 연차\"";
    }
}
