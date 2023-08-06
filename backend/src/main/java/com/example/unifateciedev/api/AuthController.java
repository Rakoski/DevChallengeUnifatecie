package com.example.unifateciedev.api;

import com.example.unifateciedev.model.entidades.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.unifateciedev.model.entidades.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



// É importante a criação de uma database chamada "usuarios" no localhost com uma table chamada Users para funcionar
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private Environment environment;

    // favor quem for usar para testes colocar sua password e não dar commit nela, já tive vários problemas de conexão
    // e fiquei aqui meia hora q nem trouxa debugando por causa de outra password debugando

    private Connection getConnection() throws SQLException {
        String server = "localhost";
        String database = "usuarios";
        String username = "root";
        String password = "Mateus0312";
        String url = "jdbc:mysql://" + server + "/" + database + "?user=" + username + "&password=" + password;
        return DriverManager.getConnection(url);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User request) {
        try {
            if (!isValidEmail(request.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email");
            }

            byte[] passwordSalt = generateSalt();
            byte[] passwordHash = generateHash(request.getPassword(), passwordSalt);

            String passwordHashString = Base64.getEncoder().encodeToString(passwordHash);
            String passwordSaltString = Base64.getEncoder().encodeToString(passwordSalt);

            try (Connection connection = getConnection()) {
                String query = "INSERT INTO usuario (email, nome, sobrenome, password_hash, password_salt) VALUES (?, ?, ?, ?, ?)";

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
                String query = "SELECT email, nome, sobrenome, password_hash, password_salt FROM usuario WHERE email = ?";

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

    private boolean isValidEmail(String email) {
        String pattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(email);
        return matcher.matches();
    }

    // Algorítmo JWT

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private byte[] generateHash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            return md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating hash", ex);
        }
    }
}
