package com.example.Tubes.Kamin.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Users {
    String username;
    String password;
    String roles;
    String fingerprint;
}
