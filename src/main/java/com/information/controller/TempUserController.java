package com.information.controller;

import com.information.entity.User;
import com.information.repository.UserRepository;
import util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TempUserController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/join")
    public void join(String nation, String language, String address, String name, long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setNation(nation);
        user.setLanguage(language);
        user.setAddress(address);
        user.setName(name);
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(long userId, HttpServletResponse res) {
        User user = userRepository.findById(userId).get();
        String accessToken = jwtUtil.createToken(userId, user.getRole(), "access");
        res.addHeader("access-token", accessToken);
        return ResponseEntity.ok("");
    }
}
