package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.CursoDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoDisciplinaRepository extends JpaRepository<CursoDisciplina, Long> {
}
