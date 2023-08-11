package com.example.unifateciedev.api;

import com.example.unifateciedev.api.utils.PasswordUtils;
import com.example.unifateciedev.model.entidades.User;
import com.example.unifateciedev.model.entidades.UserPost;
import com.example.unifateciedev.service.repo.CursoRepository;
import com.example.unifateciedev.service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.sql.*;
import java.util.*;

import static com.example.unifateciedev.api.utils.PasswordUtils.generateHash;

@RestController
@RequestMapping("/api/aluno")
public class UserController {

    protected CursoRepository cursoRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository, CursoRepository cursoRepository) {
        this.userRepository = userRepository;
        this.cursoRepository = cursoRepository;
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
    public ResponseEntity<UserPost> registerUser(@RequestBody UserPost request) {
        try {
            if (!PasswordUtils.isValidEmail(request.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email");
            }

            byte[] passwordSalt = PasswordUtils.generateSalt();
            byte[] passwordHash = generateHash(request.getPassword(), passwordSalt);

            String passwordHashString = Base64.getEncoder().encodeToString(passwordHash);
            String passwordSaltString = Base64.getEncoder().encodeToString(passwordSalt);

            try (Connection connection = getConnection()) {
                String query = "INSERT INTO usuarios.usuario (usuario_email, usuario_nome, usuario_sobrenome, " +
                        "password_hash, password_salt) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, request.getEmail());
                    statement.setString(2, request.getNome());
                    statement.setString(3, request.getSobrenome());
                    statement.setString(4, passwordHashString);
                    statement.setString(5, passwordSaltString);

                    statement.executeUpdate();
                }
            }

            return ResponseEntity.ok().build();
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error registering user", ex);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserPost request) {
        try {
            try (Connection connection = getConnection()) {
                String query = "SELECT usuario_email, usuario_nome, usuario_sobrenome, password_hash, password_salt FROM" +
                        " usuarios.usuario WHERE usuario_email = ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, request.getEmail());

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            byte[] storedPasswordHash = Base64.getDecoder().decode(resultSet.getString("password_hash"));
                            byte[] storedPasswordSalt = Base64.getDecoder().decode(resultSet.getString("password_salt"));

                            byte[] enteredPasswordHash = generateHash(request.getPassword(), storedPasswordSalt);

                            if (!MessageDigest.isEqual(storedPasswordHash, enteredPasswordHash)) {
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
                            }

                            return ResponseEntity.ok("Login successful");
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error logging in", ex);
        }
    }

    @GetMapping("/estudantes")
    public List<User> encontreTodosEstudantes() {
        return userRepository.findAll();
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

    @GetMapping("/encontre/{email}")
    public List<User> encontreUserPorEmail(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }
}
