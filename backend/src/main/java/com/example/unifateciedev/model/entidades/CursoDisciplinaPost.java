package com.example.unifateciedev.model.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "curso_disciplina")
public class CursoDisciplinaPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso_disciplina")
    private Long CursoDisciplinaId;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoPost curso;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private DisciplinaPost disciplina;

    public CursoDisciplinaPost(CursoPost curso, DisciplinaPost disciplina) {
        this.curso = curso;
        this.disciplina = disciplina;
    }

    public CursoDisciplinaPost() {

    }

    public Long getCursoDisciplinaId() {
        return CursoDisciplinaId;
    }

    public void setCursoDisciplinaId(Long cursoDisciplinaId) {
        CursoDisciplinaId = cursoDisciplinaId;
    }

    public CursoPost getCurso() {
        return curso;
    }

    public void setCurso(CursoPost curso) {
        this.curso = curso;
    }

    public DisciplinaPost getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaPost disciplina) {
        this.disciplina = disciplina;
    }

    // No need for setters for cursoId and disciplinaId
}

