package com.example.unifateciedev.model.entidades;

import javax.persistence.*;

@Entity
@Table(name = "curso_usuario")
public class CursoUsuarioPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curso_usuario_id")
    private Long idCursoUsuario;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserPost user;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoPost curso;

    @Column(name = "nome_status_curso_usuario")
    private String nomeStatusCursoUsuario;

    public CursoUsuarioPost() {

    }

    public Long getIdCursoUsuario() {
        return idCursoUsuario;
    }

    public void setIdCursoUsuario(Long idCursoUsuario) {
        this.idCursoUsuario = idCursoUsuario;
    }

    public UserPost  getUser() {
        return user;
    }

    public void setUser(UserPost user) {
        this.user = user;
    }

    public CursoPost getCurso() {
        return curso;
    }

    public void setCurso(CursoPost curso) {
        this.curso = curso;
    }

    public String getNomeStatusCursoUsuario() {
        return nomeStatusCursoUsuario;
    }

    public void setNomeStatusCursoUsuario(String nomeStatusCursoUsuario) {
        this.nomeStatusCursoUsuario = nomeStatusCursoUsuario;
    }

    public CursoUsuarioPost(Long idCursoUsuario, UserPost user, CursoPost curso, String nomeStatusCursoUsuario) {
        this.idCursoUsuario = idCursoUsuario;
        this.user = user;
        this.curso = curso;
        this.nomeStatusCursoUsuario = nomeStatusCursoUsuario;
    }
}

