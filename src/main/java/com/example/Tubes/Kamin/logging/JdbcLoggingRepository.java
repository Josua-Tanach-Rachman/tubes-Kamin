package com.example.Tubes.Kamin.logging;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLoggingRepository implements LoggingRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int addLog(String username, int category, String intruder) {
        String sql = "INSERT INTO logging (username, category, intruder) VALUES (?,?,?) RETURNING idlog";
        int idlog = jdbcTemplate.queryForObject(sql, Integer.class, username, category, intruder);
        return idlog;
    }

    @Override
    public List<Logging> findByCategoryWithUsername(int category, String username) {
        String sql = "SELECT * FROM logging WHERE category = ?, username ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToLogging, category, "%" + category + "%");
    }

    @Override
    public List<Logging> showLog(int idCat) {
        String sql = 
            "SELECT idlog, time, username, categories.category, intruder " +
            "FROM logging LEFT JOIN categories ON logging.category = categories.idCat ";
        if (idCat > 0)
            sql = sql + "WHERE logging.category = " + idCat + " ";
        sql = sql + "ORDER BY time DESC;";
        return jdbcTemplate.query(sql, this::mapRowToLogging);
    }

    @Override
    public List<Logging> logFilterByDateCategory(int idCat,Timestamp begin, Timestamp end) {
        String sql = 
            "SELECT idlog, time, username, categories.category, intruder " +
            "FROM logging LEFT JOIN categories ON logging.category = categories.idCat "+
            "WHERE time >= ? AND time <= ? ";
        if (idCat > 0)
            sql = sql + "AND logging.category = " + idCat + " ";
        sql = sql + "ORDER BY time DESC;";
        return jdbcTemplate.query(sql, this::mapRowToLogging,begin,end);
    }

    private Logging mapRowToLogging(ResultSet resultSet, int rowNum) throws SQLException {
        return new Logging(
            resultSet.getInt("idLog"),
            resultSet.getTimestamp("time"),
            resultSet.getString("username"),
            resultSet.getString("category"),
            resultSet.getString("intruder")
        );
    }
}
