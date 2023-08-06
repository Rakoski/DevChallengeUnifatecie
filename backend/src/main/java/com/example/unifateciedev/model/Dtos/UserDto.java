package com.example.unifateciedev.model.Dtos;

import com.example.unifateciedev.model.entidades.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDto {

    int insertUser(UUID id, User user);

    default int insertUser(User user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    List<User> selectAllUsers();

    Optional<User> selectUserById(UUID id);

    int deleteUserById(UUID id);

    int updateUserById(UUID id, User user);
}
