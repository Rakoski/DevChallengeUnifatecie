package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.Curso;
import com.example.unifateciedev.model.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Repository("MY__SQL")
    public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

    @Query(value = "SELECT c.id_curso, c.curso_nome AS curso_nome, c.duracao_periodo_curso " +
            "FROM u223541769_usuarios.curso c " +
            "JOIN u223541769_usuarios.curso_usuario cu ON c.id_curso = cu.curso_id " +
            "JOIN u223541769_usuarios.usuario u ON cu.usuario_id = u.id_usuario " +
            "WHERE u.id_usuario = :id_usuario", nativeQuery = true)
    List<Object[]> findCursosByUserId(@Param("id_usuario") Long id_usuario);


}
