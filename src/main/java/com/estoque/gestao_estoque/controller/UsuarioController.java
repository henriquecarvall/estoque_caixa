package com.estoque.gestao_estoque.controller;

import com.estoque.gestao_estoque.model.Usuario;
import com.estoque.gestao_estoque.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        }

        if (!isSenhaValida(usuario.getSenha())) {
            return ResponseEntity.badRequest().body("Senha deve ter 8 caracteres, 1 letra maiúscula e 1 número");
        }

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        return ResponseEntity.ok(usuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            Usuario usuarioExistente = usuario.get();

            if (!usuarioExistente.getEmail().equals(usuarioDetails.getEmail()) &&
                    usuarioService.existsByEmail(usuarioDetails.getEmail())) {
                return ResponseEntity.badRequest().body("E-mail já cadastrado");
            }

            usuarioExistente.setNome(usuarioDetails.getNome());
            usuarioExistente.setEmail(usuarioDetails.getEmail());
            usuarioExistente.setPerfil(usuarioDetails.getPerfil());
            usuarioExistente.setAtivo(usuarioDetails.getAtivo());

            if (usuarioDetails.getSenha() != null && !usuarioDetails.getSenha().isEmpty()) {
                if (!isSenhaValida(usuarioDetails.getSenha())) {
                    return ResponseEntity.badRequest().body("Senha deve ter 8 caracteres, 1 letra maiúscula e 1 número");
                }
                usuarioExistente.setSenha(new BCryptPasswordEncoder().encode(usuarioDetails.getSenha()));
            }

            return ResponseEntity.ok(usuarioService.save(usuarioExistente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean isSenhaValida(String senha) {
        return senha.length() >= 8 &&
                senha.matches(".*[A-Z].*") &&
                senha.matches(".*\\d.*");
    }
}