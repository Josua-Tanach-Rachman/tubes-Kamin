package com.example.Tubes.Kamin.logging;

import java.util.List;

public interface LoggingRepository {
    List<Logging> findByCategoryWithUsername(String category, String username);
    int addLog(String username, String reasons, String category);
}
