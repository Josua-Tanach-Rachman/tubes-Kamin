package com.example.Tubes.Kamin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Tubes.Kamin.users.Users;
import com.example.Tubes.Kamin.users.UsersRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class loginController {
    @Autowired
    UsersRepository repo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String loginPage(){
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session){
        Users user = repo.findByUsername(username).get(0);
        if(encoder.matches(password, user.getPassword())){
            session.setAttribute("username", username);
            return "redirect:/dashboard";
        }
        else{
            return "redirect:/";
        }
    }

    @GetMapping("/addAkun")
    public String addAkun(){
        String pass1 = encoder.encode("pass1");
        repo.addAkun("user1", pass1);
        return "redirect:/";
    }
}
