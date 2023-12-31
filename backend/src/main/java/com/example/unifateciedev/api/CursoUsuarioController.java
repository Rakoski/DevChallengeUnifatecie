package com.example.unifateciedev.api;

import com.example.unifateciedev.model.Dtos.UserRegistrationDto;
import com.example.unifateciedev.model.entidades.*;
import com.example.unifateciedev.service.repo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/curso_usuario")
public class CursoUsuarioController {

    protected CursoRepository cursoRepository;

    protected CursoPostRepository cursoPostRepository;

    protected CursoUsuarioRepository cursoUsuarioRepository;

    protected CursoUsuarioPostRepository cursoUsuarioPostRepository;

    private final UserRepository userRepository;

    public CursoUsuarioController(CursoRepository cursoRepository, CursoPostRepository cursoPostRepository, CursoUsuarioRepository cursoUsuarioRepository, CursoUsuarioPostRepository cursoUsuarioPostRepository, UserRepository userRepository) {
        this.cursoRepository = cursoRepository;
        this.cursoPostRepository = cursoPostRepository;
        this.cursoUsuarioRepository = cursoUsuarioRepository;
        this.cursoUsuarioPostRepository = cursoUsuarioPostRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrarUserComCurso(
            @RequestBody UserRegistrationDto userRegistrationDTO) {
        try {
            UserPost newUser = new UserPost();
            newUser.setIdUsuario(userRegistrationDTO.getIdUsuario());
            newUser.setEmail(userRegistrationDTO.getEmail());
            newUser.setNome(userRegistrationDTO.getNome());
            newUser.setSobrenome(userRegistrationDTO.getSobrenome());
            newUser.setPassword(userRegistrationDTO.getPassword());

            // Pegando um curso pelo seu id_curso
            Long courseId = userRegistrationDTO.getCourseId();
            CursoPost course = cursoPostRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found"));

            // Criando um nova instancia de CursoUsuario com suas associações
            CursoUsuarioPost cursoUsuario = new CursoUsuarioPost();
            cursoUsuario.setUser(newUser);
            cursoUsuario.setCurso(course);
            cursoUsuario.setNomeStatusCursoUsuario(userRegistrationDTO.getNomeStatusCursoUsuario());

            // Salvando a entidade no repositório a partir do constructor
            cursoUsuarioPostRepository.save(cursoUsuario);

            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user: " + e.getMessage());
        }
    }

    @GetMapping("/{id_usuario}")
    public ResponseEntity<List<Curso>> encontreUser(@PathVariable("id_usuario") Long id_usuario) {
        // dessa forma, o próprio servidor (nossas máquinas) vão pegar o email, ir na database e ver qual id corresponde
        // com o email e arquivar o id na sessão. Assim, quando o usuário acessar o endpoint o sprinboot vai dar um fetch
        // automático do @PathVariable e comparar com o da db, fazendo com que o usuário possa ver seus cursos.

        List<Object[]> cursoData = userRepository.findCursosByUserId(id_usuario);
        List<Curso> cursos = new ArrayList<>();

        for (Object[] row : cursoData) {
            Long id_curso = ((BigInteger) row[0]).longValue();
            String nome = (String) row[1];
            int duracao_periodo = (int) row[2];
            cursos.add(new Curso(id_curso, nome, duracao_periodo));
        }

        if (!cursos.isEmpty()) {
            return ResponseEntity.ok(cursos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // informações da duração do curso e o nome de seu curso pelo id dele
    @GetMapping("/curso_info/{id_curso}")
    public ResponseEntity<Map<String, Object>> getCursoInfo(@PathVariable("id_curso") Long id_curso) {
        Curso curso = cursoRepository.findById(id_curso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso not found with id: " + id_curso));

        Map<String, Object> cursoInfo = new HashMap<>();
        cursoInfo.put("nome", curso.getNome());
        cursoInfo.put("duracao_periodo", curso.getDuracao_periodo());

        return ResponseEntity.ok(cursoInfo);
    }

}
