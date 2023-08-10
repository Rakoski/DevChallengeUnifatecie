package com.example.unifateciedev.model.entidades;

import javax.persistence.*;

@Entity
@Table(name = "curso_disciplina")
public class CursoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso_disciplina")
    private Long CursoDisciplinaId;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    public Long getCursoDisciplinaId() {
        return CursoDisciplinaId;
    }

    public void setCursoDisciplinaId(Long cursoDisciplinaId) {
        CursoDisciplinaId = cursoDisciplinaId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    // No need for setters for cursoId and disciplinaId
}

