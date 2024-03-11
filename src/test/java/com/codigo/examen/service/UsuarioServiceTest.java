package com.codigo.examen.service;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.service.impl.UsuarioServiceImpl;
import com.google.common.base.Verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioServiceImpl;
    private Usuario usuario;

    @BeforeEach
    void setUp(){

        usuario=Usuario.builder()
                .idUsuario(1L)
                .username("queka")
                .password("queka")
                .email("queka@gmail.com")
                .telefono("982173779")
                .role(Rol.USER)
                .build();

    }

    @Test
    @DisplayName("Test para crear un usuario")
    void testCrearUsuario(){
       given(usuarioRepository.findById(usuario.getIdUsuario())).willReturn(Optional.empty());
        given(usuarioRepository.save(usuario)).willReturn(usuario);
        ResponseEntity<Usuario> usuario1 = usuarioServiceImpl.createUsuario(usuario);
        // lenient().when(usuarioRepository.findById(usuario.getIdUsuario()).thenReturn(Optional.empty());

        assertThat(usuario1).isNotNull();

    }
    @Test
    @DisplayName("Test para obtener un usuario por Id")
    void testObtenerUsuarioPorId(){

        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuario));

        Usuario usuario1 = usuarioServiceImpl.getUsuarioById(usuario.getIdUsuario()).getBody();


        assertThat(usuario1).isNotNull();
    }
   @Test
       @DisplayName("Test para actualizar un usuario")
    void testActualizarUsuario(){
        long idUsuario=1L;
        given(usuarioRepository.save(usuario)).willReturn(usuario);
        usuario.setEmail("ale528@gmail.com");
        usuario.setUsername("aleB");

        Usuario usuario1 = usuarioServiceImpl.updateUsuario(idUsuario, usuario).getBody();


       assert usuario1 != null;
       assertThat(usuario1.getEmail()).isEqualTo("ale528@gmail.com");
        assertThat(usuario1.getUsername()).isEqualTo("aleB");
    }
   @Test
    @DisplayName("Test para eliminar un usuario")
    void testEliminarUsuario(){

        long usuarioId = 1L;
        willDoNothing().given(usuarioRepository).deleteById(usuarioId);
        usuarioServiceImpl.deleteUsuario(usuarioId);
        verify(usuarioRepository, times(1)).deleteById(usuarioId);
    }
}
