package com.example.Tubes.Kamin.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Tubes.Kamin.logging.Logging;
import com.example.Tubes.Kamin.logging.LoggingRepository;
import com.example.Tubes.Kamin.users.Users;
import com.example.Tubes.Kamin.users.UsersRepository;

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
    public String filteredLog(@RequestParam(value = "idCat", required = false) Integer idCat, 
    @RequestParam(value = "begin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate,
    @RequestParam(value ="username", required = false) String username,
    @RequestParam(value ="intruder", required = false) String intruder, Model model){
        model.addAttribute("begin", startDate);
        model.addAttribute("end", endDate);
        if (startDate == null) {
            startDate = LocalDate.of(1970, 1, 1); // Default to 1 January 1970
        }

        if (endDate == null) {
            endDate = LocalDate.now();
        }

        Timestamp start = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp end = Timestamp.valueOf(endDate.atTime(23, 59, 59));

        List<Logging> logs = logRepo.showLog(idCat);
        //testing
        if(username.equals("")){
            username = null;
        }
        if(intruder.equals("")){
            intruder = null;
        }
        List<Logging> logs2 = logRepo.logFilterByDateCategory(idCat,start,end,username,intruder);
        model.addAttribute("logs", logs2);
        model.addAttribute("category", idCat);
        model.addAttribute("username", username);
        model.addAttribute("intruder", intruder);
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
        model.addAttribute("deleted", username);
        userRepo.deleteFingerprint(username);
        List<Users> users = userRepo.showAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("feedback", "deleted");
        return "admin/deleteFP";
    }


}
