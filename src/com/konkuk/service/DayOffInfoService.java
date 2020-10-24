package com.konkuk.service;

import com.konkuk.UI;


import com.konkuk.dto.Employee;

public class DayOffInfoService {
    static final int WorkDay=260;
    float reductsalary;

    public  void  cal(int salary, float residualDayOff, int id){   //차감액 계산
        reductsalary=(salary/WorkDay)*(residualDayOff*-1);
        UI.print(id+"님의 연차 초과사용으로 인한 차감액은 "+reductsalary+"원 입니다");
    }
    public  void  refund_cal(int salary, float residualDayOff, int id){ //환급액 계산
        reductsalary=(salary/WorkDay)*residualDayOff;
        UI.print(id+"님의 연차 미사용으로 인한 환급액은 "+reductsalary+"원 입니다");
    }


}




