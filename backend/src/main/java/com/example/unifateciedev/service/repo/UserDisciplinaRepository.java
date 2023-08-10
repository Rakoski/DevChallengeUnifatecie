package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.UsuarioDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDisciplinaRepository extends JpaRepository<UsuarioDisciplina, Long> {
}
