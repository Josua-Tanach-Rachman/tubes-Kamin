package com.example.Tubes.Kamin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Tubes.Kamin.users.UsersRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class dashboardController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UsersRepository repo;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        return "/dashboard";
    }
    
    @PostMapping("/mulaiKuis")
    public String mulaiKuis(
    @RequestParam String userAgent,
    @RequestParam String language,
    @RequestParam String screenResolution,
    @RequestParam String
    timezone,
    @RequestParam String platform,
    @RequestParam String
    canvas,
    Model model, HttpSession session, RedirectAttributes redirectAttributes){
        String username = (String) session.getAttribute("username");
        String res = userAgent + language + screenResolution + timezone + platform + canvas;
        String fingerprint = encoder.encode(res);
        repo.addFingerprint(username, fingerprint);
        redirectAttributes.addFlashAttribute("fingerprint",fingerprint);
        return "redirect:/halamanKuis";
    }

    @GetMapping("/halamanKuis")
    public String halamanKuis(){
        return "/halamanKuis";
    }

    @PostMapping("/selesaiKuis")
    public String selesaiKuis(HttpSession session){
        String username = (String) session.getAttribute("username");
        repo.deleteFingerprint(username);
        return "redirect:/dashboard";
    }
}
