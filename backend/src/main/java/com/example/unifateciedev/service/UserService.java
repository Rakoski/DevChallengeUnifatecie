package com.example.unifateciedev.service;

import com.example.unifateciedev.model.entidades.*;
import com.example.unifateciedev.service.repo.*;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // Set other user attributes (email, nome, sobrenome, password, etc.) based on DTO

        CursoUsuario cursoUsuario = new CursoUsuario();
        // Set cursoUsuario attributes
        cursoUsuario.setUser(user);
        cursoUsuario.setCurso(curso);
        cursoUsuario.setNomeStatusCursoUsuario(nomeStatusCursoUsuario);
        cursoUsuarioRepository.save(cursoUsuario);

        for (CursoDisciplina cursoDisciplina : curso.getCursoDisciplina()) {
            UsuarioDisciplina usuarioDisciplina = new UsuarioDisciplina();
            usuarioDisciplina.setUsuario(user);  // Set the associated user
            usuarioDisciplina.setDisciplina(cursoDisciplina.getDisciplina());
            usuarioDisciplinaRepository.save(usuarioDisciplina);
        }

        userRepository.save(user);
    }


}

