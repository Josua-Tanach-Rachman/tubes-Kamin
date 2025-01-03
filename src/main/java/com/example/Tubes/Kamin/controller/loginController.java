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

import com.example.Tubes.Kamin.logging.LoggingRepository;
import com.example.Tubes.Kamin.users.Users;
import com.example.Tubes.Kamin.users.UsersRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class loginController {
    @Autowired
    UsersRepository userRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    LoggingRepository logRepo;

    @GetMapping("/")
    public String loginPage() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String userAgent,
            @RequestParam String language,
            @RequestParam String screenResolution,
            @RequestParam String timezone,
            @RequestParam String platform,
            @RequestParam String canvas,
            Model model, HttpSession session) {
        Users user = userRepo.findByUsername(username).get(0);
        if (encoder.matches(password, user.getPassword())) {
            session.setAttribute("username", username);
            String res = userAgent + language + screenResolution + timezone + platform + canvas;

            String hashedFingerprint = hashFingerprintWithSHA256(res);
            // String fingerprint = encoder.encode(hashedFingerprint);

            if (user.getFingerprint() == null) {
                model.addAttribute("username", username);
                model.addAttribute("fingerprint", hashedFingerprint);
                return "tambahFingerprint";
            }

            // if(encoder.matches(hashedFingerprint, user.getFingerprint())){
            if (hashedFingerprint.equals(user.getFingerprint())) {
                return "redirect:/dashboard";
            } else {
                Optional<Users> userOptional = userRepo.searchByFingerprint(hashedFingerprint);
                if (userOptional.isPresent()) {
                    Users user2 = userOptional.get();
                    logRepo.addLog(user.getUsername(), 1, user2.getUsername());
                    return String.format("device is registered to %s", user2.getUsername());
                } else {
                    logRepo.addLog(user.getUsername(), 2, null);
                    return String.format("device unregistered, the device: %s", hashedFingerprint);
                }
                // return String.format("Gagal dengan userfingerprint: %s", hashedFingerprint);
            }

        } else {
            return "redirect:/";
        }
    }

    // Helper method to hash the fingerprint data using SHA-256
    private String hashFingerprintWithSHA256(String fingerprintData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fingerprintData.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes); // Convert byte array to hex string
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Handle exception appropriately
        }
    }

    // Helper method to convert byte array to hex string
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @PostMapping("/addFingerprint")
    public String addFingerprint(@RequestParam String username, @RequestParam String fingerprint) {
        userRepo.addFingerprint(username, fingerprint);
        return "redirect:/dashboard";
    }

    @GetMapping("/addAkun")
    public String addAkun() {
        String pass1 = encoder.encode("pass1");
        userRepo.addAkun("user1", pass1);
        String pass2 = encoder.encode("pass2");
        userRepo.addAkun("user2", pass2);
        return "redirect:/";
    }
}
