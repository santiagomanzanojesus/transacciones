package com.company.app.controller;

import com.company.app.dto.LoginRequestDto;
import com.company.app.service.LoginService;
import com.company.app.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService authService;
    private final JwtUtil jwtUtil;
    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        boolean success = authService.authenticate(request.getUsername(), request.getPassword());
        if (success) {

            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(Map.of("mensaje", "Login exitoso", "token", token,
                    "username", request.getUsername()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }
    }
}
