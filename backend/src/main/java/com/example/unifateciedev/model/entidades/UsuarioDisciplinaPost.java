package com.example.unifateciedev.model.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "usuario_disciplina")
public class UsuarioDisciplinaPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_disciplina")
    private Long idUsuarioDisciplina;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserPost usuario;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private DisciplinaPost disciplina;

    @Column(name = "nota")
    private int nota;

    @Column(name = "status_disciplina")
    private String statusDisciplina;

    public UsuarioDisciplinaPost() {

    }

    public Long getIdUsuarioDisciplina() {
        return idUsuarioDisciplina;
    }

    public void setIdUsuarioDisciplina(Long idUsuarioDisciplina) {
        this.idUsuarioDisciplina = idUsuarioDisciplina;
    }

    public UserPost getUsuario() {
        return usuario;
    }

    public void setUsuario(UserPost usuario) {
        this.usuario = usuario;
    }

    public DisciplinaPost getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaPost disciplina) {
        this.disciplina = disciplina;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getStatusDisciplina() {
        return statusDisciplina;
    }

    public void setStatusDisciplina(String statusDisciplina) {
        this.statusDisciplina = statusDisciplina;
    }
}

