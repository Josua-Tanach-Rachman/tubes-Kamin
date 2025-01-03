package com.example.Tubes.Kamin.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    List<Users> findByUsername(String username);
    int addFingerprint(String username, String fingerprint);
    int deleteFingerprint(String username);
    Optional<Users> searchByFingerprint(String fingerprint);
    int addAkun(String username, String password);
    Users mapRowToUsers(ResultSet resultSet, int rowNum) throws SQLException;
}
