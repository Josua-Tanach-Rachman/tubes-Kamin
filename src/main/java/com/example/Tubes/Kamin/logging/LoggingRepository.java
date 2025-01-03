package com.example.Tubes.Kamin.logging;

import java.util.List;

public interface LoggingRepository {
    List<Logging> findByCategoryWithUsername(int category, String username);
    int addLog(String username, int category, String intruder);
    List<Logging> showLog(int idCat);
}
