package com.example.unifateciedev.model.Dtos;

import com.example.unifateciedev.model.entidades.Disciplina;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplinasDto {

    int insertDisciplina(UUID id, Disciplina disciplina);

    default int insertDisciplina(Disciplina disciplina) {
        UUID id = UUID.randomUUID();
        return insertDisciplina(id, disciplina);
    }

    List<Disciplina> selectAllDisciplinas();

    Optional<Disciplina> selectDisciplinaById(UUID id);

    int deleteDisciplinaById(UUID id);

    int updateDisciplinaById(UUID id, Disciplina disciplina);
}
