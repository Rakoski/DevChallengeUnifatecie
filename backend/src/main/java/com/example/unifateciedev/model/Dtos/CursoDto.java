package com.example.unifateciedev.model.Dtos;

import com.example.unifateciedev.model.entidades.Curso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CursoDto {

    int insertCurso(UUID id, Curso curso);

    default int insertCurso(Curso curso) {
        UUID id = UUID.randomUUID();
        return insertCurso(id, curso);
    }

    List<Curso> selectAllCursos();

    Optional<Curso> selectCursoById(UUID id);

    int deleteCursoById(UUID id);

    int updateCursoById(UUID id, Curso curso);
}
