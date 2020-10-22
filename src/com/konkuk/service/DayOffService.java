package com.konkuk.service;

import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;

public class DayOffService {
    DayOffRepository dayOffRepository = DayOffRepository.getInstance();

    public boolean use(int type, String reason, String start) {
//
//        dayOffRepository.findByDate();
//        dayOffRepository.findByEmployeeId();
//        등의 기능을 사용해서 구현
//        DayOff dayOff = new DayOff(0, id, .... )
//        dayOffRepository.add(dayoff);
        return false;
    }

    public boolean add(String reason, String count){


        return false;
    }

    public boolean change(String reason, String start){


        return false;
    }

    public boolean cancel(){

        return false;
    }

    public boolean reduct(String reason, String count){


        return false;
    }

}
