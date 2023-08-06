package com.example.unifateciedev.api;

import com.example.unifateciedev.model.entidades.Curso;
import com.example.unifateciedev.model.entidades.CursoPost;
import com.example.unifateciedev.model.entidades.User;
import com.example.unifateciedev.model.entidades.UserPost;
import com.example.unifateciedev.service.repo.CursoRepository;
import com.example.unifateciedev.service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.sql.*;
import java.util.*;

import static com.example.unifateciedev.api.utils.PasswordUtils.generateHash;
import static com.example.unifateciedev.api.utils.PasswordUtils.generateSalt;

// vamos ter que definir quais id's vão ser de quais cursos pra isso funcionar. Vou começar bem simples, com só 2 cursos
// agronomia id = 9
// matemática id = 11.

@RestController
@RequestMapping("/api/curso")
public class CursoController {

    // vou precisar ter um setter para que o id_usuario seja instanciado como uma variável para que eu consiga fazer
    // chegar no método GET do {id_usuario}
    private Long id_usuario;

    private final UserRepository userRepository;

    private final CursoRepository cursoRepository;


    @Autowired
    public CursoController(UserRepository userRepository, CursoRepository cursoRepository, CursoRepository cursoRepository1) {
        this.userRepository = userRepository;
        this.cursoRepository = cursoRepository1;
    }

    public void setIdUsuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    private Connection getConnection() throws SQLException {
        String server = "localhost";
        String database = "usuarios";
        String username = "root";
        String password = "Mateus0312";
        String url = "jdbc:mysql://" + server + "/" + database + "?user=" + username + "&password=" + password;
        return DriverManager.getConnection(url);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserPost request) {
        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;
        try {
            byte[] passwordSalt = generateSalt();
            byte[] passwordHash = generateHash(request.getPassword(), passwordSalt);

            String passwordHashString = Base64.getEncoder().encodeToString(passwordHash);
            String passwordSaltString = Base64.getEncoder().encodeToString(passwordSalt);

            try (Connection connection = getConnection()) {
                String insertUsuarioQuery = "INSERT INTO usuario (email, nome, sobrenome, password_hash, password_salt) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement usuarioStatement = connection.prepareStatement(insertUsuarioQuery, Statement.RETURN_GENERATED_KEYS)) {
                    usuarioStatement.setString(1, request.getEmail());
                    usuarioStatement.setString(2, request.getNome());
                    usuarioStatement.setString(3, request.getSobrenome());
                    usuarioStatement.setString(4, passwordHashString);
                    usuarioStatement.setString(5, passwordSaltString);
                    usuarioStatement.executeUpdate();

                    long idUsuario;
                    try (ResultSet generatedKeys = usuarioStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            idUsuario = generatedKeys.getLong(1);
                        } else {
                            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no registro do usuário");
                        }
                    }

                    String insertCursosUsuarioQuery = "INSERT INTO cursos_usuario (usuario_id, curso_id, nome_status) VALUES (?, ?, ?)";
                    try (PreparedStatement cursosUsuarioStatement = connection.prepareStatement(insertCursosUsuarioQuery)) {
                        for (CursoPost curso : request.getCursos()) {
                            String selectCursoQuery = "SELECT id_curso FROM cursos WHERE nome = ?";
                            try (PreparedStatement selectCursoStatement = connection.prepareStatement(selectCursoQuery)) {
                                selectCursoStatement.setString(1, curso.getNome());
                                long cursoId;
                                try (ResultSet resultSet = selectCursoStatement.executeQuery()) {
                                    if (resultSet.next()) {
                                        cursoId = resultSet.getLong("id_curso");
                                    } else {
                                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso não encontrado: " + curso.getNome());

                                    }
                                }

                                cursosUsuarioStatement.setLong(1, idUsuario);
                                cursosUsuarioStatement.setLong(2, cursoId);
                                if(randomNumber == 3) {
                                    cursosUsuarioStatement.setString(3, "MATRICULADO");
                                }
                                if(randomNumber == 2) {
                                    cursosUsuarioStatement.setString(3, "CANCELADO");
                                }
                                if(randomNumber == 1) {
                                    cursosUsuarioStatement.setString(3, "TRANSFERIDO");
                                }
                                if(randomNumber == 4) {
                                    cursosUsuarioStatement.setString(3, "TRANCADO");
                                }
                                if(randomNumber == 5) {
                                    cursosUsuarioStatement.setString(3, "CONCLUIDO");
                                }
                                cursosUsuarioStatement.addBatch();
                            }
                        }

                        cursosUsuarioStatement.executeBatch();
                    }
                }
            }

            return ResponseEntity.ok().build();
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error no registro do usuário", ex);
        }
    }


    public Long pegueUserIdPeloEmail(String email) {
        try (Connection connection = getConnection()) {
            String selectUserIdQuery = "SELECT id_usuario FROM usuario WHERE email = ?";
            try (PreparedStatement selectUserIdStatement = connection.prepareStatement(selectUserIdQuery)) {
                selectUserIdStatement.setString(1, email);
                try (ResultSet resultSet = selectUserIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getLong("id_usuario");
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching user ID", ex);
        }
    }

    public List<User> encontreTodosEstudantes() {
        return userRepository.findAll();
    }

    @GetMapping("/cursos_usuario/{id_usuario}")
    public ResponseEntity<List<Curso>> encontreUser(@PathVariable("id_usuario") Long id_usuario) {
        // dessa forma, o próprio servidor (nossas máquinas) vão pegar o email, ir na database e ver qual id corresponde
        // com o email e arquivar o id na sessão. Assim, quando o usuário acessar o endpoint o sprinboot vai dar um fetch
        // automático do @PathVariable e comparar com o da db, fazendo com que o usuário possa ver seus cursos.

        List<Object[]> cursoData = userRepository.findCursosByUserId(id_usuario);
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

    @GetMapping("/usuario_info/{id_usuario}")
    public ResponseEntity<Map<String, String>> encontreUserPorId(@PathVariable("id_usuario") Long id_usuario) {
        User user = userRepository.findById(id_usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com id " + id_usuario));

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("nome", user.getNome());
        userInfo.put("sobrenome", user.getSobrenome());
        userInfo.put("email", user.getEmail());

        return ResponseEntity.ok(userInfo);
    }


    @GetMapping("/curso_usuario/{id_usuario}")
    public ResponseEntity<Object[]> encontreCursoUsuarioInfoPorId(@PathVariable("id_usuario") Long id_usuario) {
        List<Object[]> result = cursoRepository.findCursoInfoByUserId(id_usuario);
        System.out.println(result);
        if (!result.isEmpty()) {
            return ResponseEntity.ok(new List[]{result});
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/curso_info/{id_curso}")
    public ResponseEntity<Map<String, Object>> getCursoInfo(@PathVariable("id_curso") Long id_curso) {
        Curso curso = cursoRepository.findById(id_curso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso not found with id: " + id_curso));

        Map<String, Object> cursoInfo = new HashMap<>();
        cursoInfo.put("nome", curso.getNome());
        cursoInfo.put("duracao_periodo", curso.getDuracao_periodo());

        return ResponseEntity.ok(cursoInfo);
    }

    @GetMapping("/encontre/{email}")
    public List<User> encontreUserPorEmail(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }

}
