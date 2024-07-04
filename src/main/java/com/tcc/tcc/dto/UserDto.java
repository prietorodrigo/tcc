package com.tcc.tcc.dto;

import com.tcc.tcc.controller.EditValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public class UserDto {
    private Long id;
    @NotEmpty(groups = EditValidation.class)
    private String firstName;
    @NotEmpty(groups = EditValidation.class)
    private String lastName;
    @NotEmpty(message = "O e-mail não deve estar vazio", groups = EditValidation.class)
    @Email
    private String email;
    @NotEmpty(message = "A senha não deve estar vazia")
    private String password;
    private String foto;
    @NotEmpty(groups = EditValidation.class)
    private String matricula;
    private String siape;
    private String lattes;
    @NotEmpty(message = "O papel não deve estar vazio", groups = EditValidation.class)
    private Set<String> roleName;

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

    public String getLattes() {
        return lattes;
    }

    public void setLattes(String lattes) {
        this.lattes = lattes;
    }

    public Set<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(Set<String> roleName) {
        this.roleName = roleName;
    }
}
