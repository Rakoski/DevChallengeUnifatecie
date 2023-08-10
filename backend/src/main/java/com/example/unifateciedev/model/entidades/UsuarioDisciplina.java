package com.example.unifateciedev.model.entidades;

import javax.persistence.*;

@Entity
@Table(name = "usuario_disciplina")
public class UsuarioDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_disciplina")
    private Long idUsuarioDisciplina;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @Column(name = "nota")
    private int nota;

    @Column(name = "status_disciplina")
    private String statusDisciplina;

    public UsuarioDisciplina() {

    }

    public Long getIdUsuarioDisciplina() {
        return idUsuarioDisciplina;
    }

    public void setIdUsuarioDisciplina(Long idUsuarioDisciplina) {
        this.idUsuarioDisciplina = idUsuarioDisciplina;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
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

