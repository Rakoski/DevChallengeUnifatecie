package com.example.unifateciedev.api;

import com.example.unifateciedev.model.Dtos.DisciplinaRegistroCursoDto;
import com.example.unifateciedev.model.Dtos.DisciplinaRegistroUserDto;
import com.example.unifateciedev.model.entidades.Curso;
import com.example.unifateciedev.model.entidades.Disciplina;
import com.example.unifateciedev.service.ServicoCurso;
import com.example.unifateciedev.service.UserService;
import com.example.unifateciedev.service.repo.CursoRepository;
import com.example.unifateciedev.service.repo.DisciplinasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/api/disciplina")
public class DisciplinaController {

    // o que eu queria fazer é um sistema que consiga registrar disciplinas com diversos cursos e, assim que um usuário
    // for registrado com tal curso ele é automáticamente registrado com todas as disciplinas desse curso. Após isso,
    // queria fazer um método PUT para conseguir alterar a nota do usuário em tal disciplina

    protected CursoRepository cursoRepository;

    protected DisciplinasRepository disciplinasRepository;

    @Autowired
    private ServicoCurso servicoCurso;

    @Autowired
    private UserService userService;

    @PostMapping("/cursos/registrar")
    public ResponseEntity<String> colocarCursoComDisciplina(@RequestBody DisciplinaRegistroCursoDto disciplinaRegistroCursoDto) {
        try {
            servicoCurso.registrarCursoComDisciplina(
                    disciplinaRegistroCursoDto.getCursoId(),
                    disciplinaRegistroCursoDto.getDisciplinaId()
            );

            return ResponseEntity.ok("Disciplina registered with curso successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering disciplina with curso: " + e.getMessage());
        }
    }

    @PostMapping("/usuario/registrar")
    public ResponseEntity<String> registrarUsuarioComDisciplina(@RequestBody DisciplinaRegistroUserDto disciplinaRegistroUserDto) {
        try {
            userService.registrarUsuarioComSuaDisciplina(
                    disciplinaRegistroUserDto.getUserId(),
                    disciplinaRegistroUserDto.getDisciplinaId(),
                    disciplinaRegistroUserDto.getNota(),
                    disciplinaRegistroUserDto.getStatusDisciplina()
            );

            return ResponseEntity.ok("User registered with disciplina successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user with disciplina: " + e.getMessage());
        }
    }


    @GetMapping("/{id_disciplina}")
    public ResponseEntity<List<Curso>> encontreCursosPeloIdDaDisciplina(@PathVariable("id_disciplina") Long idDisciplina) {
        List<Object[]> cursoData = cursoRepository.findCursosByDisciplinaId(idDisciplina);
        List<Curso> cursos = new ArrayList<>();

        for (Object[] row : cursoData) {
            Long id_curso = ((BigInteger) row[0]).longValue();
            String nome = (String) row[1];
            Integer duracao_periodo = (Integer) row[2];
            cursos.add(new Curso(id_curso, nome, duracao_periodo));
        }

        if (!cursos.isEmpty()) {
            return ResponseEntity.ok(cursos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/disciplina_info/{id_disciplina}")
    public Optional<Disciplina> getDisciplina(@PathVariable("id_disciplina") Long idDisciplina) {
        return disciplinasRepository.findById(idDisciplina);
    }

    @GetMapping("/cursos_disciplinas/{id_curso}")
    public ResponseEntity<List<Disciplina>> findDisciplinasByCursoId(@PathVariable("id_curso") Long idCurso) {
        List<Object[]> disciplinaData = cursoRepository.findDisciplinasByCursoId(idCurso);
        List<Disciplina> disciplinas = new ArrayList<>();

        for (Object[] row : disciplinaData) {
            Long id_disciplina = ((BigInteger) row[0]).longValue();
            String nome_disciplina = (String) row[1];
            String professor = (String) row[2];
            disciplinas.add(new Disciplina(id_disciplina, nome_disciplina, professor));
        }

        if (!disciplinas.isEmpty()) {
            return ResponseEntity.ok(disciplinas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/get")
    public List<Disciplina> encontreTodasDisciplinas() {
        return disciplinasRepository.findAll();
    }

    @GetMapping("/nome/{nome}")
    public List<Disciplina> encontreDisciplinaPorNome(String nomeDisciplina) {
        return disciplinasRepository.findByNomeDisciplina(nomeDisciplina);
    }

    @GetMapping("/disciplina_usuario/{id_usuario}/{id_disciplina}")
    public ResponseEntity<Map<String, Object>> encontreUserDisciplinaPorId(@PathVariable("id_usuario") Long usuarioId, @PathVariable("id_disciplina") Long disciplinaId) {
        List<Object[]> notaStatusList = disciplinasRepository.findNotaStatusByUserAndDisciplinaId(usuarioId, disciplinaId);

        if (notaStatusList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dados de disciplina não encontrados para o usuário com id " + usuarioId + " e disciplina com id " + disciplinaId);
        }

        Object[] notaStatus = notaStatusList.get(0);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("usuario_id", usuarioId);
        userInfo.put("disciplina_id", disciplinaId);
        userInfo.put("nota", notaStatus[0]);
        userInfo.put("status_disciplina", notaStatus[1]);

        return ResponseEntity.ok(userInfo);
    }

}
