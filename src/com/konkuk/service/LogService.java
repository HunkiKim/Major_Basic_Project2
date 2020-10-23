package com.konkuk.service;

import com.konkuk.repository.LogRepository;
import java.util.List;

public class LogService {
    LogRepository logRepository = LogRepository.getInstance();

    public List<String> get_log() {
        List<String> log_list = logRepository.get_log();
        return log_list;
    }
}
