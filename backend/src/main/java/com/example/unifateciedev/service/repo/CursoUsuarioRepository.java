package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.Curso;
import com.example.unifateciedev.model.entidades.CursoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoUsuarioRepository extends JpaRepository<CursoUsuario, Long> {
}
