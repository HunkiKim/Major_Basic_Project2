package com.konkuk.service;

import com.konkuk.repository.LogRepository;
import java.util.List;

public class LogService {
    public static List<String> get_log() {
        List<String> log_list = LogRepository.get_log();
        return log_list;
    }
}
