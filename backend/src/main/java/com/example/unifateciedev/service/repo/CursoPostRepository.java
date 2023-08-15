package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.CursoPost;
import com.example.unifateciedev.model.entidades.CursoUsuarioPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoPostRepository extends JpaRepository<CursoPost, Long> {
}
