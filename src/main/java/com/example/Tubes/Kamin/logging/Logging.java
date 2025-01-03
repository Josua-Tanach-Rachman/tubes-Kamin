package com.example.Tubes.Kamin.logging;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Logging {
    private int idLog;
    private Timestamp time;
    private String username;
    private String reasons;
    private String category;
}
