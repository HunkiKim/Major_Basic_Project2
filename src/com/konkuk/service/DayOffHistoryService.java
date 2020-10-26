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
                return -o1.dateDayOffStart.compareTo(o2.dateDayOffStart);
            }
        });

        int listSize = this.dayOffList.size();


        if(listSize<=0){
            return false;
        }
        List<DayOff> subList = new ArrayList<>(this.dayOffList.subList(pageNumber*10, Math.min(listSize,pageNumber*10+10)));

        //UI.print(Langs.DATA_FILE_HEADER_DAYOFF);
        UI.print2(Langs.HORIZON1);
        System.out.println(String.format("%s %4s %9s %4s %10s %10s %7s", "번호", "사번", "변동 연차 수", "사유", "연차 시작 시간", "연차 종료 시간", "생성 날짜"));
        UI.print2(Langs.HORIZON1);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");

        for(DayOff data:subList){
            System.out.println(String.format("%s %5s %10s %7s %13s %13s %14s",
                    data.id,
                    data.employeeId,
                    data.changedDayOffCount,
                    data.reason,
                    simpleDate.format(data.dateDayOffStart),
                    simpleDate.format(data.dateDayOffEnd),
                    simpleDate.format(data.dateCreated)));
//            record+=(data.id+" "+
//                    data.employeeId+" "+
//                    data.changedDayOffCount+" "+
//                    data.reason+" "+
//                    simpleDate.format(data.dateDayOffStart)+" "+
//                    simpleDate.format(data.dateDayOffEnd)+" "+
//                    simpleDate.format(data.dateCreated))+"\n";
        }
        UI.print2(Langs.HORIZON1);
        return true;
    }

    public boolean isNextPageExists(int annualPage) {
        int existPage = (this.dayOffList.size()-1)/10;
        if(annualPage<existPage){
            return true;
        }
        return false;
    }

    public boolean isPrevPageExists(int annualPage) {
        if(annualPage>=1){
            return true;
        }
        return false;
    }
}
