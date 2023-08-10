package com.example.unifateciedev.model.Dtos;

import com.example.unifateciedev.model.entidades.Disciplina;

public class DisciplinaRegistroCursoDto {

    private Long cursoDisciplinaId;
    private Long cursoId;
    private Long disciplinaId;

    public DisciplinaRegistroCursoDto(Long cursoDisciplinaId, Long cursoId, Long disciplinaId) {
        this.cursoDisciplinaId = cursoDisciplinaId;
        this.cursoId = cursoId;
        this.disciplinaId = disciplinaId;
    }

    public DisciplinaRegistroCursoDto() {

    }

    public Long getCursoDisciplinaId() {
        return cursoDisciplinaId;
    }

    public void setCursoDisciplinaId(Long cursoDisciplinaId) {
        this.cursoDisciplinaId = cursoDisciplinaId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }
}
