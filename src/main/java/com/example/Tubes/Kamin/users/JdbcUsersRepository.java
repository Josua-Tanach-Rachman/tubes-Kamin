package com.example.Tubes.Kamin.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
        return jdbcTemplate.update(sql, fingerprint, username);
    }

    @Override
    public int deleteFingerprint(String username) {
        String sql = "UPDATE users Set fingerprint = NULL WHERE username = ?";
        return jdbcTemplate.update(sql, username);
    }

    @Override
    public Optional<Users> searchByFingerprint(String fingerprint) {
        String sql = "SELECT * FROM users WHERE fingerprint = ?";
        List<Users> res = jdbcTemplate.query(sql, this::mapRowToUsers, fingerprint);
        return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
    }

    @Override
    public int addAkun(String username, String password, String roles) {
        String sql = "INSERT INTO users(username, password, roles) VALUES (?,?,?)";
        return jdbcTemplate.update(sql, username, password, roles);
    }

    @Override
    public List<Users> showAllUsers() {
        String sql = "SELECT * FROM users WHERE fingerprint IS NOT NULL ORDER BY username ASC;";
        return jdbcTemplate.query(sql, this::mapRowToUsers);
    }

    @Override
    public Users mapRowToUsers(ResultSet resultSet, int rowNum) throws SQLException {
        return new Users(
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("roles"),
                resultSet.getString("fingerprint"));
    }
}
