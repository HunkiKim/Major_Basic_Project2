package com.konkuk.repository;

import com.konkuk.dto.Log;
import java.io.IOException;
import java.util.List;

public interface ILogRepository {
    public List<Log> getLogs();
    public Log add(Log log) throws IOException;
    public void addLog(String category, String content);

}
