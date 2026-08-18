package com.company.app.controller;

import com.company.app.dto.LoginRequestDto;
import com.company.app.service.LoginService;
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

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        boolean success = authService.authenticate(request.getUsername(), request.getPassword());
        if (success) {
            return ResponseEntity.ok(Map.of("mensaje", "Login exitoso"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }
    }
}
