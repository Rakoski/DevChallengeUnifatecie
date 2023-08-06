package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MySQL2")
    public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByNome(String nome);

    @Query(value = "SELECT c.id_curso, c.nome AS curso_nome, c.duracao_periodo " +
            "FROM cursos c " +
            "JOIN cursos_disciplinas cd ON c.id_curso = cd.curso_id " +
            "JOIN disciplinas d ON cd.disciplinas_id = d.id_disciplinas " +
            "WHERE d.id_disciplinas = :id_disciplina", nativeQuery = true)
    List<Object[]> findCursosByDisciplinaId(@Param("id_disciplina") Long idDisciplina);

    @Query(value = "SELECT d.id_disciplinas, d.nome_disciplina, d.professor " +
            "FROM disciplinas d " +
            "JOIN cursos_disciplinas cd ON d.id_disciplinas = cd.disciplinas_id " +
            "JOIN cursos c ON cd.curso_id = c.id_curso " +
            "WHERE c.id_curso = :id_curso", nativeQuery = true)
    List<Object[]> findDisciplinasByCursoId(@Param("id_curso") Long idCurso);

    @Query(value = "SELECT nome_status, curso_id, usuario_id FROM cursos_usuario WHERE usuario_id = :id_usuario"
    , nativeQuery = true)
    List<Object[]> findCursoInfoByUserId(@Param("id_usuario") Long id_usuario);

}
