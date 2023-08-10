package com.example.unifateciedev.model.Dtos;

public class UserRegistrationDto {

    private Long idUsuario;
    private String email;
    private String nome;
    private String sobrenome;
    private String password;
    private String nomeStatusCursoUsuario;
    private Long courseId;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String email, String nome, String sobrenome, String password, String nomeStatusCursoUsuario, Long courseId) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.password = password;
        this.nomeStatusCursoUsuario = nomeStatusCursoUsuario;
        this.courseId = courseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNomeStatusCursoUsuario() {
        return nomeStatusCursoUsuario;
    }

    public void setNomeStatusCursoUsuario(String nomeStatusCursoUsuario) {
        this.nomeStatusCursoUsuario = nomeStatusCursoUsuario;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
