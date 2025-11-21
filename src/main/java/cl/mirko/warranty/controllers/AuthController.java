package cl.mirko.warranty.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.mirko.warranty.services.UsuarioService;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return usuarioService.findByEmail(request.getEmail())
                .filter(usuario -> usuario.getPassword().equals(request.getPassword()))
                .map(usuario -> {
                    LoginResponse response = new LoginResponse();
                    response.setIdUsuario(usuario.getIdUsuario());
                    response.setNombre(usuario.getNombre());
                    response.setEmail(usuario.getEmail());
                    response.setRol(usuario.getRol());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Getter
    @Setter
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class LoginResponse {
        private Integer idUsuario;
        private String nombre;
        private String email;
        private String rol;
    }
}
