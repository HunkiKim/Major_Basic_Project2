package com.konkuk.service;

import com.konkuk.dto.Log;
import com.konkuk.repository.LogRepository;
import java.util.List;

public class LogService {
    LogRepository logRepository = LogRepository.getInstance();

    public List<Log> getLogs() {
        return logRepository.getLogs();
    }
}
