package com.konkuk.service;

import com.konkuk.dto.DayOff;
import com.konkuk.repository.DayOffHistoryRepository;
import com.konkuk.repository.DayOffRepository;

public class DayOffHistoryService {
    DayOffHistoryRepository dayOffHistoryRepository = DayOffHistoryRepository.getInstance();

    public boolean annualHistory(int annualPage) {
        // 해당 페이지의 연간 연차내역 출력
        return false;
    }

    public boolean advancedSearch(int searchPage){
        return false;
    }

    public boolean isExistsNextPage(int annualPage) {
        return annualPage < dayOffHistoryRepository.getSize();
    }

    public boolean isExistsPrevPage(int annualPage) {
        return annualPage == 0;
    }
}
