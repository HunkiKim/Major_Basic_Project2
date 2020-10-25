package com.konkuk.service;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.repository.DayOffRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DayOffHistoryService {
    DayOffRepository dayOffRepository = DayOffRepository.getInstance();
    private List<DayOff> dayOffList;

    public boolean getHistory(int employeeId, int pageNumber, Date start, Date end, int option){
        String record = "";
        this.dayOffList = dayOffRepository.findByDate(employeeId, start, end, option);
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
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");

        for(DayOff data:subList){
            record+=(data.id+" "+
                    data.employeeId+" "+
                    data.changedDayOffCount+" "+
                    data.reason+" "+
                    simpleDate.format(data.dateDayOffStart)+" "+
                    simpleDate.format(data.dateDayOffEnd)+" "+
                    simpleDate.format(data.dateCreated));
            UI.print(record);
        }

        return true;
    }

    public boolean isNextPageExists(int annualPage) {
//        return annualPage < dayOffList.size();
        if(annualPage>0 && annualPage<(dayOffList.size()-1)/10+1){
            return true;
        }
        return false;
    }

    public boolean isPrevPageExists(int annualPage) {
        if(annualPage>1 && annualPage<=(dayOffList.size()-1)/10+1){
            return true;
        }
        return false;
    }
}
