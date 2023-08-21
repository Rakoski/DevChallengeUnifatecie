package com.example.unifateciedev.service;

import com.example.unifateciedev.model.entidades.Curso;
import com.example.unifateciedev.model.entidades.CursoDisciplina;
import com.example.unifateciedev.model.entidades.Disciplina;
import com.example.unifateciedev.service.repo.CursoRepository;
import com.example.unifateciedev.service.repo.DisciplinasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ServicoCurso {

    private final CursoRepository cursoRepository;
    private final DisciplinasRepository disciplinaRepository;

    @Autowired
    public ServicoCurso(CursoRepository cursoRepository, DisciplinasRepository disciplinaRepository) {
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public void registrarCursoComDisciplina(Long courseId, Long disciplineId) {
        Curso course = cursoRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        Disciplina discipline = disciplinaRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada"));

        CursoDisciplina courseDiscipline = new CursoDisciplina(course, discipline);
        course.addCourseDiscipline(courseDiscipline);

        cursoRepository.save(course);
    }
}
