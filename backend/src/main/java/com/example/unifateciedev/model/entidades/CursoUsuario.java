package com.example.unifateciedev.model.entidades;

import com.example.unifateciedev.model.entidades.User;

import javax.persistence.*;

@Entity
@Table(name = "curso_usuario")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curso_usuario_id")
    private Long idCursoUsuario;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(name = "nome_status_curso_usuario")
    private String nomeStatusCursoUsuario;

    public CursoUsuario() {

    }

    public Long getIdCursoUsuario() {
        return idCursoUsuario;
    }

    public void setIdCursoUsuario(Long idCursoUsuario) {
        this.idCursoUsuario = idCursoUsuario;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getNomeStatusCursoUsuario() {
        return nomeStatusCursoUsuario;
    }

    public void setNomeStatusCursoUsuario(String nomeStatusCursoUsuario) {
        this.nomeStatusCursoUsuario = nomeStatusCursoUsuario;
    }

    public CursoUsuario(Long idCursoUsuario, User user, Curso curso, String nomeStatusCursoUsuario) {
        this.idCursoUsuario = idCursoUsuario;
        this.user = user;
        this.curso = curso;
        this.nomeStatusCursoUsuario = nomeStatusCursoUsuario;
    }
}

