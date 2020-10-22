package com.konkuk.service;

import com.konkuk.UI;
import com.konkuk.asset.Langs;
import com.konkuk.dto.DayOff;
import com.konkuk.repository.DayOffHistoryRepository;
import com.konkuk.repository.DayOffRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayOffHistoryService {
    DayOffHistoryRepository dayOffHistoryRepository = DayOffHistoryRepository.getInstance();
    private List<DayOff> dayOffList;
    public boolean annualHistory(int employeeId, int annualPage) {
        // 해당 페이지의 연간 연차내역 출력
        String record = "";
        this.dayOffList = dayOffHistoryRepository.findByEmployeeId(employeeId);
        // TODO: 생성 날짜 기준 내림차순 정렬

        int listSize = this.dayOffList.size();
        List<DayOff> subList = new ArrayList<>(this.dayOffList.subList(annualPage*10, Math.min(listSize,annualPage*10+9)));
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

        return false;
    }

    public boolean advancedSearch(int employeeId, int searchPage, Date start, Date end){
        return false;
    }

    public boolean isNextPageExists(int annualPage) {
        return annualPage < dayOffList.size();
    }

    public boolean isPrevPageExists(int annualPage) {
        return annualPage == 0;
    }
}
