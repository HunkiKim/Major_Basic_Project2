package com.konkuk.service;

import com.konkuk.dto.Employee;

public class DayOffInfoService {
        static final int WorkDay=260;
        int reductsalary;

      public void cal( int salary, int residualDayOff){ //환급액 계산
            reductsalary=(salary/WorkDay)*residualDayOff;
             System.out.println(reductsalary);
        }

    }
