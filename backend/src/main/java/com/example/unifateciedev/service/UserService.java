package com.example.unifateciedev.service;

import com.example.unifateciedev.model.entidades.*;
import com.example.unifateciedev.service.repo.*;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinasRepository disciplinaRepository;

    @Autowired
    private CursoUsuarioRepository cursoUsuarioRepository;

    @Autowired
    private UserDisciplinaRepository usuarioDisciplinaRepository;

    @Transactional
    public void registerUserWithCourse(User user, Long courseId, String nomeStatusCursoUsuario) {
        Curso curso = cursoRepository.findById(courseId).orElseThrow(NoSuchElementException::new);

        // colocando email, nome, sobrenome, password, etc. baseado no DTO

        CursoUsuario cursoUsuario = new CursoUsuario();
        // colocando os atributos
        cursoUsuario.setUser(user);
        cursoUsuario.setCurso(curso);
        cursoUsuario.setNomeStatusCursoUsuario(nomeStatusCursoUsuario);
        cursoUsuarioRepository.save(cursoUsuario);

        for (CursoDisciplina cursoDisciplina : curso.getCursoDisciplina()) {
            UsuarioDisciplina usuarioDisciplina = new UsuarioDisciplina();
            usuarioDisciplina.setUsuario(user);
            usuarioDisciplina.setDisciplina(cursoDisciplina.getDisciplina());
            usuarioDisciplinaRepository.save(usuarioDisciplina);
        }

        userRepository.save(user);
    }

    @Transactional
    public void registrarUsuarioComSuaDisciplina(Long userId, Long disciplinaId, int nota, String statusDisciplina) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada"));

        UsuarioDisciplina usuarioDisciplina = new UsuarioDisciplina();
        usuarioDisciplina.setUsuario(user);
        usuarioDisciplina.setDisciplina(disciplina);
        usuarioDisciplina.setNota(nota);
        usuarioDisciplina.setStatusDisciplina(statusDisciplina);

        user.getUsuarioDisciplina().add(usuarioDisciplina);

        userRepository.save(user);
    }

}

