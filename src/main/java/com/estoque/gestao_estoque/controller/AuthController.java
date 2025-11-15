package com.estoque.gestao_estoque.controller;

import com.estoque.gestao_estoque.model.Usuario;
import com.estoque.gestao_estoque.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserDetails userDetails = usuarioService.loadUserByUsername(loginRequest.getEmail());

            if (passwordEncoder.matches(loginRequest.getSenha(), userDetails.getPassword())) {
                Optional<Usuario> usuarioOpt = usuarioService.findByEmail(loginRequest.getEmail());
                Usuario usuario = usuarioOpt.get();

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login realizado com sucesso");
                response.put("usuario", Map.of(
                        "id", usuario.getId(),
                        "nome", usuario.getNome(),
                        "email", usuario.getEmail(),
                        "perfil", usuario.getPerfil()
                ));
                return ResponseEntity.ok(response);
            }
        } catch (UsernameNotFoundException e) {
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "E-mail ou senha inválidos");
        return ResponseEntity.status(401).body(response);
    }
}

class LoginRequest {
    private String email;
    private String senha;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}