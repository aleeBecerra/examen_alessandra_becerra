package com.codigo.examen.repository;

import com.codigo.examen.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private Usuario usuario;
    @BeforeEach
    void setUp(){
        usuario=Usuario.builder()
                .username("gabriel")
                .password("quekaodin")
                .email("gabriel@gmail.com")
                .telefono("999888777")
                .build();
    }

    @Test
    void testCrearUsuario(){
        Usuario usuario1 = usuarioRepository.save(usuario);

        assertThat(usuario1).isNotNull();
        assertThat(usuario1.getIdUsuario()).isGreaterThan(0);

    }
    @Test
    void testObtenerUsuarioPorId(){
        usuarioRepository.save(usuario);
        Usuario usuario1 = usuarioRepository.findById(usuario.getIdUsuario()).get();

        assertThat(usuario1).isNotNull();
    }

    @Test
    void testActualizarUsuario(){
        usuarioRepository.save(usuario);
        Usuario usuario1=usuarioRepository.findById(usuario.getIdUsuario()).get();
        usuario1.setUsername("alessandra");
        usuario1.setPassword("odinqueka");
        usuario1.setEmail("odin@gmail.com");
        usuario1.setTelefono("982173771");
        Usuario usuarioUpdate=usuarioRepository.save(usuario1);

        assertThat(usuarioUpdate.getPassword()).isEqualTo("odinqueka");
        assertThat(usuarioUpdate.getUsername()).isEqualTo("alessandra");
    }
    @Test
    void testEliminarUsuario(){
        usuarioRepository.save(usuario);
        usuarioRepository.deleteById(usuario.getIdUsuario());
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getIdUsuario());
        assertThat(usuarioOptional).isEmpty();
    }
}
