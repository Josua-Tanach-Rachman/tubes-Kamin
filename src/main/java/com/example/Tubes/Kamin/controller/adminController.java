package com.example.Tubes.Kamin.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Tubes.Kamin.logging.Logging;
import com.example.Tubes.Kamin.logging.LoggingRepository;
import com.example.Tubes.Kamin.users.Users;
import com.example.Tubes.Kamin.users.UsersRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class adminController {
    @Autowired
    UsersRepository userRepo;

    @Autowired
    LoggingRepository logRepo;


    @GetMapping("/admin")
    public String admin(Model model){
        return "admin/main";
    }

    @GetMapping("/admin/checkLog")
    public String showLog(Model model){
        List<Logging> logs = logRepo.showLog(0);
        model.addAttribute("logs", logs);
        return "admin/checkLog";
    }

    @PostMapping("/admin/checkLog")
    public String filteredLog(@RequestParam(value = "idCat", required = false) Integer idCat, Model model){
        List<Logging> logs = logRepo.showLog(idCat);
        model.addAttribute("logs", logs);
        return "admin/checkLog";
    }

    @GetMapping("/admin/deleteFP")
    public String deleteFP(Model model){
        List<Users> users = userRepo.showAllUsers();
        model.addAttribute("users", users);
        return "admin/deleteFP";
    }

    @PostMapping("/admin/deleteFP")
    public String feedbackFP(@RequestParam(value = "username", required = false) String username, Model model){
        userRepo.deleteFingerprint(username);
        List<Users> users = userRepo.showAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("feedback", "deleted");
        return "admin/deleteFP";
    }


}
