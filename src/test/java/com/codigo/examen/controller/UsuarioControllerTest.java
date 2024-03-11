package com.codigo.examen.controller;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsuarioService usuarioService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void testCrearUsuario() throws Exception {
        Usuario usuario = Usuario.builder()
                .idUsuario(1L)
                .username("alessandra")
                .password("odinqueka")
                .email("alessandra@gmail.com")
                .telefono("982173771")
                .role(Rol.ADMIN)
                .build();

        given(usuarioService.createUsuario(any(Usuario.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)));

        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(usuario.getUsername())))
                .andExpect(jsonPath("$.password", is(usuario.getPassword())))
                .andExpect(jsonPath("$.email", is(usuario.getEmail())))
                .andExpect(jsonPath("$.telefono", is(usuario.getTelefono())))
                .andExpect(jsonPath("$.role", is (usuario.getRole())));
    }
    @Test
    void testObtenerUsuarioPorId() throws Exception {
        long usuarioId = 1L;
        Usuario usuario = Usuario.builder()
                .username("alessandra")
                .password("odinqueka")
                .email("alessandra@gmail.com")
                .telefono("982173771")
                .role(Rol.ADMIN)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/usuarios/{id}", usuarioId));
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is(usuario.getUsername())))
                .andExpect(jsonPath("$.password", is(usuario.getPassword())))
                .andExpect(jsonPath("$.email", is(usuario.getEmail())))
                .andExpect(jsonPath("$.telefono", is(usuario.getTelefono())));
    }
   @Test
    void testActualizarUsuario() throws Exception{

        long usuarioId = 1L;
        Usuario usuarioGuardado = Usuario.builder()
                .username("Andrea")
                .password("Ramirez")
                .email("andrea@gmail.com")
                .telefono("981737731")
                .role(Rol.ADMIN)
                .build();

        Usuario usuarioActualizado = Usuario.builder()
                .username("Flor")
                .password("Perez")
                .email("flor@gmail.com")
                .telefono("981737732")
                .role(Rol.USER)
                .build();
        given(usuarioService.updateUsuario( usuarioId, (Usuario.class).newInstance()))
                .willAnswer(invocation -> invocation.getArgument(0));


        ResultActions resultActions = mockMvc.perform(put("/api/usuarios/{id}", usuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioActualizado)));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is(usuarioActualizado.getUsername())))
                .andExpect(jsonPath("$.password", is(usuarioActualizado.getPassword())))
                .andExpect(jsonPath("$.email", is(usuarioActualizado.getEmail())));
    }
    @Test
    void testEliminarUsuario() throws Exception{
        long usuarioId = 1L;
        willDoNothing().given(usuarioService).deleteUsuario(usuarioId);

        //when
        ResultActions resultActions = mockMvc.perform(delete("/api/usuarios/{id}", usuarioId));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }




}