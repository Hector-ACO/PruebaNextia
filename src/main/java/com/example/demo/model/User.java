package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User  extends BaseModel {

    @NotEmpty(message = "El usuario se encuentra vacio")
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "La contraseña se encuentra vacia")
    private String password;

    @Email
    private String email;

    @Transient
    private String token;

}
