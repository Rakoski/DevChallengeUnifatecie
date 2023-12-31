package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Disciplina_MySQL")
public interface DisciplinasRepository extends JpaRepository<Disciplina, Long> {

    Optional<Disciplina> findById(Long idDisciplinas);

    List<Disciplina> findByNomeDisciplina(String nomeDisciplina);

    List<Disciplina> findByProfessor(String professor);

    @Query(value = "SELECT d.id_disciplina, d.nome_disciplina AS disciplina_nome, d.professor_disciplina " +
            "FROM u223541769_usuarios.disciplina d " +
            "JOIN u223541769_usuarios.curso_disciplina cd ON d.id_disciplina = cd.disciplina_id " +
            "JOIN u223541769_usuarios.curso c ON cd.curso_id = c.id_curso " +
            "WHERE c.id_curso = :id_curso", nativeQuery = true)
    List<Object[]> findDisciplinasByCursoId(@Param("id_curso") Long idCurso);

    @Query(value = "SELECT nota, status_disciplina FROM usuarios.usuario_disciplina " +
            "WHERE usuario_id = ?1 AND disciplina_id = ?2", nativeQuery = true)
    List<Object[]> findNotaStatusByUserAndDisciplinaId(@Param("usuario_id") Long usuario_id, @Param("disciplina_id") Long disciplina_id);

}