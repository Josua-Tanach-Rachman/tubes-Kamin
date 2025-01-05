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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String awal(){
        return "redirect:/login";
    }

    @GetMapping("/login")
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
            Model model, HttpSession session, RedirectAttributes redirectAttributes) 
    {
        List<Users> listUser = userRepo.findByUsername(username);
        if(listUser.size() == 0){
            return "redirect:/login";
        }
        Users user = listUser.get(0);
        if(user.getRoles().equals("admin")){
            return "redirect:/admin/checkLog";
        }
        if (encoder.matches(password, user.getPassword())) {
            session.setAttribute("username", username);
            String res = userAgent + language + screenResolution + timezone + platform + canvas;

            String hashedFingerprint = hashFingerprintWithSHA256(res);
            // String fingerprint = encoder.encode(hashedFingerprint);

            if (user.getFingerprint() == null) {
                model.addAttribute("username", user.getUsername());
                model.addAttribute("fingerprint", hashedFingerprint);
                model.addAttribute("shouldShowPopup", true);
                return "index";
            }

            // if(encoder.matches(hashedFingerprint, user.getFingerprint())){
            if (hashedFingerprint.equals(user.getFingerprint())) {
                redirectAttributes.addFlashAttribute("kalimat","You have successfully signed into your account. You can close this window and continue using the application.");
                redirectAttributes.addFlashAttribute("judul","Login Successful");
                return "redirect:/dashboard";
            } else {
                Optional<Users> userOptional = userRepo.searchByFingerprint(hashedFingerprint);
                if (userOptional.isPresent()) {
                    Users user2 = userOptional.get();
                    logRepo.addLog(user.getUsername(), 1, user2.getUsername());
                    redirectAttributes.addFlashAttribute("kalimat","Your device is registered to username: " + user2.getUsername() + ". Please log out from this application");
                    redirectAttributes.addFlashAttribute("judul","Login Failed");
                    return "redirect:/dashboard";
                    // return String.format("device is registered to %s", user2.getUsername());
                } else {
                    logRepo.addLog(user.getUsername(), 2, null);
                    redirectAttributes.addFlashAttribute("kalimat","Your account is registered to another device");
                    redirectAttributes.addFlashAttribute("judul","Login Failed");
                    return "redirect:/dashboard";
                    // return String.format("device unregistered, the device: %s", hashedFingerprint);
                }
                // return String.format("Gagal dengan userfingerprint: %s", hashedFingerprint);
            }

        } else {
            return "redirect:/salahpassword";
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
    public String addFingerprint(@RequestParam String username, @RequestParam String fingerprint, RedirectAttributes redirectAttributes) {
        userRepo.addFingerprint(username, fingerprint);
        redirectAttributes.addFlashAttribute("kalimat","You have successfully signed into your account. You can close this window and continue using the application.");
        redirectAttributes.addFlashAttribute("judul","Login Successful");
        redirectAttributes.addFlashAttribute("shouldShowPopup", true);
        return "redirect:/dashboard";
    }

    @GetMapping("/addAkun")
    public String addAkun() {
        String pass1 = encoder.encode("pass1");
        userRepo.addAkun("user1", pass1,"pengguna");
        String pass2 = encoder.encode("pass2");
        userRepo.addAkun("user2", pass2, "pengguna");
        String admin = encoder.encode("admin");
        userRepo.addAkun("admin", admin, "admin");
        return "redirect:/";
    }
}
