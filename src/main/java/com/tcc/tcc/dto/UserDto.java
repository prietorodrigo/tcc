package com.tcc.tcc.dto;

import com.tcc.tcc.controller.EditValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserDto {
    private Long id;
    @NotEmpty(groups = EditValidation.class)
    private String firstName;
    @NotEmpty(groups = EditValidation.class)
    private String lastName;
    @NotEmpty(message = "Email should not be empty", groups = EditValidation.class)
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private String foto;
    @NotEmpty(groups = EditValidation.class)
    private String matricula;
    private String siape;
    @NotEmpty(message = "Role should not be empty", groups = EditValidation.class)
    private String roleName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
