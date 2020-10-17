package com.konkuk.dto;

import com.konkuk.asset.Langs;

public class Employee {
    public int id;
    public String name;
    public int salary;
    public int residualDayOff;

    public Employee(int id, String name, int salary, int residualDayOff) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.residualDayOff = residualDayOff;
    }

    public static String getHeader() {
        return Langs.DATA_FILE_HEADER_EMPLOYEE;
    }
}
