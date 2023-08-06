package com.example.unifateciedev.api;

import com.example.unifateciedev.model.entidades.*;
import com.example.unifateciedev.service.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api/disciplina")
public class DisciplinaController {

    private final UserRepository userRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinasRepository disciplinasRepository;

    @Autowired
    public DisciplinaController(UserRepository userRepository, CursoRepository cursoRepository, DisciplinasRepository disciplinasRepository, DisciplinasRepository disciplinasRepository1) {
        this.userRepository = userRepository;
        this.cursoRepository = cursoRepository;
        this.disciplinasRepository = disciplinasRepository1;
    }

    private Connection getConnection() throws SQLException {
        String server = "localhost";
        String database = "usuarios";
        String username = "root";
        String password = "Mateus0312";
        String url = "jdbc:mysql://" + server + "/" + database + "?user=" + username + "&password=" + password;
        return DriverManager.getConnection(url);
    }

    @PostMapping("/register_curso")
    public ResponseEntity<DisciplinaPost> salvarDisciplina(@RequestBody DisciplinaPost disciplinaPost) {
        Random random = new Random();
        int randomNumber2 = random.nextInt(5) + 1;
        try (Connection connection = getConnection()) {
            String insertDisciplinaQuery = "INSERT INTO disciplinas (nome_disciplina, professor) VALUES (?, ?)";
            try (PreparedStatement disciplinaStatement = connection.prepareStatement(insertDisciplinaQuery, Statement.RETURN_GENERATED_KEYS)) {
                disciplinaStatement.setString(1, disciplinaPost.getNome_disciplina());
                disciplinaStatement.setString(2, disciplinaPost.getProfessor());

                disciplinaStatement.executeUpdate();

                try (ResultSet generatedKeys = disciplinaStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long idDisciplina = generatedKeys.getLong(1);
                        disciplinaPost.setIdDisciplinas(idDisciplina);


                        String insertCursosDisciplinasQuery = "INSERT INTO cursos_disciplinas (curso_id, disciplinas_id) VALUES (?, ?)";
                        try (PreparedStatement cursosDisciplinasStatement = connection.prepareStatement(insertCursosDisciplinasQuery)) {
                            for (CursoPost cursoPost : disciplinaPost.getCursos()) {
                                cursosDisciplinasStatement.setLong(1, cursoPost.getIdCurso());
                                cursosDisciplinasStatement.setLong(2, idDisciplina);
                                cursosDisciplinasStatement.addBatch();
                            }
                            cursosDisciplinasStatement.executeBatch();
                        }
                        String insertUsuarioIntoDisciplina = "INSERT INTO usuario_disciplina (usuario_id, disciplina_id, nota, status_disciplina) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertUsuarioIntoDisciplinaStatement = connection.prepareStatement(insertUsuarioIntoDisciplina)) {
                            for (UserPost userPost : disciplinaPost.getUserPosts()) {
                                insertUsuarioIntoDisciplinaStatement.setLong(1, userPost.getId_user());
                                insertUsuarioIntoDisciplinaStatement.setLong(2, idDisciplina);
                                if (randomNumber2 == 1 || randomNumber2 == 2) {
                                    int nota = random.nextInt(100) + 1;
                                    if (nota < 60) {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "REPROVADO");
                                    } else {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "APROVADO");
                                    }
                                    insertUsuarioIntoDisciplinaStatement.setInt(3, nota);
                                } else {
                                    if (randomNumber2 == 3) {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "A CURSAR");
                                        insertUsuarioIntoDisciplinaStatement.setString(3, null);
                                    } else if (randomNumber2 == 4) {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "DISPENSADO");
                                        insertUsuarioIntoDisciplinaStatement.setString(3, null);
                                    } else {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "CURSANDO");
                                        insertUsuarioIntoDisciplinaStatement.setString(3, null);
                                    }
                                }
                                insertUsuarioIntoDisciplinaStatement.addBatch();
                                insertUsuarioIntoDisciplinaStatement.executeBatch();
                            }
                        }
                    } else {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no registro da disciplina");
                    }
                }

                return new ResponseEntity<>(disciplinaPost, HttpStatus.CREATED);
            }
        } catch (SQLException e) {
            throw new  ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no registro da disciplina", e);
        }
    }

    @PostMapping("/register_usuario")
    public ResponseEntity<DisciplinaPost> salvarDisciplinaComUsuario(@RequestBody DisciplinaPost disciplinaPost) {
        Random random = new Random();
        int randomNumber2 = random.nextInt(5) + 1;
        try (Connection connection = getConnection()) {
            String insertDisciplinaQuery = "INSERT INTO disciplinas (nome_disciplina, professor) VALUES (?, ?)";
            try (PreparedStatement disciplinaStatement = connection.prepareStatement(insertDisciplinaQuery, Statement.RETURN_GENERATED_KEYS)) {
                disciplinaStatement.setString(1, disciplinaPost.getNome_disciplina());
                disciplinaStatement.setString(2, disciplinaPost.getProfessor());

                disciplinaStatement.executeUpdate();

                try (ResultSet generatedKeys = disciplinaStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long idDisciplina = generatedKeys.getLong(1);
                        disciplinaPost.setIdDisciplinas(idDisciplina);

                        String insertUsuarioIntoDisciplina = "INSERT INTO usuario_disciplina (usuario_id, disciplina_id, nota, status_disciplina) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertUsuarioIntoDisciplinaStatement = connection.prepareStatement(insertUsuarioIntoDisciplina)) {
                            for (UserPost userPost : disciplinaPost.getUserPosts()) {
                                insertUsuarioIntoDisciplinaStatement.setLong(1, userPost.getId_user());
                                insertUsuarioIntoDisciplinaStatement.setLong(2, idDisciplina);
                                if (randomNumber2 == 1 || randomNumber2 == 2) {
                                    int nota = random.nextInt(100) + 1;
                                    if (nota < 60) {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "REPROVADO");
                                    } else {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "APROVADO");
                                    }
                                    insertUsuarioIntoDisciplinaStatement.setInt(3, nota);
                                } else {
                                    if (randomNumber2 == 3) {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "A CURSAR");
                                        insertUsuarioIntoDisciplinaStatement.setString(3, null);
                                    } else if (randomNumber2 == 4) {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "DISPENSADO");
                                        insertUsuarioIntoDisciplinaStatement.setString(3, null);
                                    } else {
                                        insertUsuarioIntoDisciplinaStatement.setString(4, "CURSANDO");
                                        insertUsuarioIntoDisciplinaStatement.setString(3, null);
                                    }
                                }
                                insertUsuarioIntoDisciplinaStatement.addBatch();
                                insertUsuarioIntoDisciplinaStatement.executeBatch();
                            }
                        }
                    } else {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no registro da disciplina");
                    }
                }

                return new ResponseEntity<>(disciplinaPost, HttpStatus.CREATED);
            }
        } catch (SQLException e) {
            throw new  ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no registro da disciplina", e);
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
            cursos.add(new Curso(id_curso, duracao_periodo, nome));
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
