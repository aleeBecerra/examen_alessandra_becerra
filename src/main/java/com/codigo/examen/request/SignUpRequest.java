package com.codigo.examen.request;

import com.codigo.examen.entity.Rol;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SignUpRequest {
    private String email;
    private String username;
    private String password;
    private Set<Rol> roles = new HashSet<>();

}
