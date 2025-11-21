package cl.mirko.warranty.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.mirko.warranty.models.Usuario;
import cl.mirko.warranty.repositories.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        crearSiNoExiste(
                "admin@warranty.cl",
                "Administrador Warranty",
                "admin123",
                "ADMIN"
        );

        crearSiNoExiste(
                "usuario@warranty.cl",
                "Usuario Demo",
                "usuario123",
                "USER"
        );
    }

    private void crearSiNoExiste(String email, String nombre, String password, String rol) {
        usuarioRepository.findByEmail(email).orElseGet(() -> {
            Usuario usuario = Usuario.builder()
                    .nombre(nombre)
                    .email(email)
                    .password(password)
                    .rol(rol)
                    .build();
            return usuarioRepository.save(usuario);
        });
    }
}
