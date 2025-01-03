package com.example.Tubes.Kamin.logging;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLoggingRepository implements LoggingRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int addLog(String username, String reasons, String category) {
        String sql = "INSERT INTO logging (username, reasons, category) VALUES (?,?,?) RETURNING idlog";
        int idArtis = jdbcTemplate.queryForObject(sql, Integer.class, username, reasons, category);
        return idArtis;
    }

    @Override
    public List<Logging> findByCategoryWithUsername(String category, String username) {
        String sql = "SELECT * FROM logging WHERE category = ?, username ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToLogging, category, "%" + category + "%");
    }

    private Logging mapRowToLogging(ResultSet resultSet, int rowNum) throws SQLException {
        return new Logging(
            resultSet.getInt("idLog"),
            resultSet.getTimestamp("time"),
            resultSet.getString("username"),
            resultSet.getString("reasons"),
            resultSet.getString("category")
        );
    }
}
