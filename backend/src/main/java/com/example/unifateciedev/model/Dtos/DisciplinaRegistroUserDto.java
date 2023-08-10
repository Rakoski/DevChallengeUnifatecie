package com.example.unifateciedev.model.Dtos;

public class DisciplinaRegistroUserDto {

    private Long userId;
    private Long disciplinaId;
    private int nota;
    private String statusDisciplina;

    public DisciplinaRegistroUserDto(Long idDisciplinaRegistro, Long userId, Long disciplinaId, int nota, String statusDisciplina) {
        this.userId = userId;
        this.disciplinaId = disciplinaId;
        this.nota = nota;
        this.statusDisciplina = statusDisciplina;
    }

    public DisciplinaRegistroUserDto() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
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
