package com.konkuk.service;

import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;
import com.konkuk.repository.DayOffRepository;

public class DayOffService {
    DayOffRepository dayOffRepository = DayOffRepository.getInstance();
    private int list_num = 1;

    public boolean use(int type, String reason, String start) {
//        dayOffRepository.findByDate();
//        dayOffRepository.findByEmployeeId();
//        등의 기능을 사용해서 구현
        //DayOff dayOff = new DayOff(list_num, id, name, reason, start, end, fcount);
//        dayOffRepository.add(dayOff);
        list_num++;
        return false;
    }

    public boolean add(String reason, int count){


        return false;
    }

    public boolean change(String reason, String start){


        return false;
    }

    public boolean cancel(DayOff dayOff){


        return false;
    }

    public boolean reduct(String reason, int count){


        return false;
    }

}
