package com.example.Tubes.Kamin.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUsersRepository implements UsersRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Users> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.query(sql, this::mapRowToUsers, username);
    }

    @Override
    public int addFingerprint(String username, String fingerprint) {
        String sql = "UPDATE users Set fingerprint = ? WHERE username = ?";
        return jdbcTemplate.update(sql, fingerprint,username);
    }

    @Override
    public int deleteFingerprint(String username) {
        String sql = "UPDATE users Set fingerprint = NULL WHERE username = ?";
        return jdbcTemplate.update(sql,username);
    }

    

    @Override
    public int addAkun(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES (?,?)";
        return jdbcTemplate.update(sql,username,password);
    }

    @Override
    public Users mapRowToUsers(ResultSet resultSet, int rowNum) throws SQLException {
        return new Users(
            resultSet.getString("username"), 
            resultSet.getString("password"), 
            resultSet.getString("fingerprint")
        );
    }
}
