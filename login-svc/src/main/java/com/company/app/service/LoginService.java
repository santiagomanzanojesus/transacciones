package com.company.app.service;

import com.company.app.entity.Usuario;
import com.company.app.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String password) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            return passwordEncoder.matches(password, userOpt.get().getPassword());
        }
        return false;
    }

    // Método para insertar usuario inicial (ejecutar al iniciar la app)
    @PostConstruct
    public void init() {
        if (!usuarioRepository.findByUsername("admin").isPresent()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            usuarioRepository.save(admin);
        }
    }
}
