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

    @Query(value = "SELECT c.id_curso, c.curso_nome AS curso_nome, c.duracao_periodo_curso " +
            "FROM u223541769_usuarios.curso c " +
            "JOIN u223541769_usuarios.curso_disciplina cd ON c.id_curso = cd.curso_id " +
            "JOIN u223541769_usuarios.disciplina d ON cd.disciplina_id = d.id_disciplina " +
            "WHERE d.id_disciplina = :id_disciplina", nativeQuery = true)
    List<Object[]> findCursosByDisciplinaId(@Param("id_disciplina") Long idDisciplina);

    @Query(value = "SELECT d.id_disciplina, d.nome_disciplina, d.professor_disciplina " +
            "FROM u223541769_usuarios.disciplina d " +
            "JOIN u223541769_usuarios.curso_disciplina cd ON d.id_disciplina = cd.disciplina_id " +
            "JOIN u223541769_usuarios.curso c ON cd.curso_id = c.id_curso " +
            "WHERE c.id_curso = :id_curso", nativeQuery = true)
    List<Object[]> findDisciplinasByCursoId(@Param("id_curso") Long idCurso);

    @Query(value = "SELECT nome_status_curso_usuario, curso_id, usuario_id FROM u223541769_usuarios.curso_usuario WHERE usuario_id = :id_usuario"
    , nativeQuery = true)
    List<Object[]> findCursoInfoByUserId(@Param("id_usuario") Long id_usuario);

}
