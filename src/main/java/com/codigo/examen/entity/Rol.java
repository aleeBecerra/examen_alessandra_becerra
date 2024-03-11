package com.codigo.examen.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


public enum Rol {

   USER,
    ADMIN

}