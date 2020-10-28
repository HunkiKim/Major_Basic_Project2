package com.konkuk.service;

public class DayOffInfoService {
    static final int WorkDay=260;
    float reductsalary;

    public  float  cal(float salary, float residualDayOff){   //차감액 계산
        reductsalary=(salary/WorkDay)*(residualDayOff*-1);
        return reductsalary;
    }
    public  float  refund_cal(float salary, float residualDayOff){ //환급액 계산
        reductsalary=(salary/WorkDay)*residualDayOff;
        return reductsalary;
    }


}




