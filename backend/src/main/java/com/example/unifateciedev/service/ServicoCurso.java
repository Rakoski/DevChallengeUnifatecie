package com.example.unifateciedev.service;

import com.example.unifateciedev.service.repo.CursoRepository;
import com.example.unifateciedev.service.repo.DisciplinasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
