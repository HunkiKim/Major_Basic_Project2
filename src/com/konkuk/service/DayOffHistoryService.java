package com.konkuk.service;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.repository.DayOffHistoryRepository;
import com.konkuk.repository.DayOffRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DayOffHistoryService {
    DayOffHistoryRepository dayOffHistoryRepository = DayOffHistoryRepository.getInstance();
    private List<DayOff> dayOffList;

    public boolean getHistory(int employeeId, int pageNumber, Date start, Date end){
        String record = "";
        this.dayOffList = dayOffHistoryRepository.findByDate(employeeId, start, end);
        dayOffList.sort(new Comparator<DayOff>() {
            @Override
            public int compare(DayOff o1, DayOff o2) {
                // id값은 중복되지 않기 때문에 return 0은 따로 안 만들었습니다.
                if (o1.id>o2.id){
                    return -1;
                }else{
                    return 1;
                }

            }
        });

        int listSize = this.dayOffList.size();
        if(listSize<=0){
            return false;
        }
        List<DayOff> subList = new ArrayList<>(this.dayOffList.subList(pageNumber*10, Math.min(listSize,pageNumber*10+9)));
        UI.print(Langs.DATA_FILE_HEADER_DAYOFF);
        for(DayOff data:subList){
            record+=(data.num+" "+
                    data.id+" "+
                    data.dayOffNumber+" "+
                    data.reason+" "+
                    data.dateDayOffStart+" "+
                    data.dateDayOffEnd+" "+
                    data.dateCreated);
            UI.print(record);
        }

        return true;
    }

    public boolean isNextPageExists(int annualPage) {
        return annualPage < dayOffList.size();
    }

    public boolean isPrevPageExists(int annualPage) {
        return annualPage == 0;
    }
}
