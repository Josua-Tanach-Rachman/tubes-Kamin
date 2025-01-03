package com.example.Tubes.Kamin.logging;

import java.sql.Timestamp;
import java.util.List;

public interface LoggingRepository {
    List<Logging> findByCategoryWithUsername(int category, String username);
    int addLog(String username, int category, String intruder);
    List<Logging> showLog(int idCat);
    List<Logging> logFilterByDateCategory(int idCat,Timestamp begin, Timestamp end, String username, String intruder);
}
