package com.konkuk.dto;

import com.konkuk.asset.Langs;

public class Employee {
    public int id;
    public String name;
    public int salary;
    public float residualDayOff;

    public Employee(int id, String name, int salary, float residualDayOff) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.residualDayOff = residualDayOff;
    }

    public static String getHeader() {
        return Langs.DATA_FILE_HEADER_EMPLOYEE;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }
    public float getResidualDayOff() { return residualDayOff; }
    public void setResidualDayOff(float num) { this.residualDayOff = num; }
}
