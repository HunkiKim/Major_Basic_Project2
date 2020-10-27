package com.konkuk.service;

import com.konkuk.UI;


import com.konkuk.dto.Employee;

public class DayOffInfoService {
    static final int WorkDay=260;
    float reductsalary;

    public  float  cal(int salary, float residualDayOff, String name){   //차감액 계산
        reductsalary=(salary/WorkDay)*(residualDayOff*-1);
        return reductsalary;
    }
    public  float  refund_cal(int salary, float residualDayOff, String name){ //환급액 계산
        reductsalary=(salary/WorkDay)*residualDayOff;
        return reductsalary;
    }


}




