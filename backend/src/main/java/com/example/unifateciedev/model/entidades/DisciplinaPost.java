package com.example.unifateciedev.model.entidades;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disciplinas")
public class DisciplinaPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplinas")
    private Long idDisciplinas;

    @Column(name = "nome_disciplina")
    private String nomeDisciplina;

    @Column(name = "professor")
    private String professor;

    @ManyToMany
    @JoinTable(
            name = "cursos_disciplinas",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplinas_id")
    )
    private Set<CursoPost> cursos;

    @ManyToMany
    @JoinTable(
            name = "usuario_disciplina",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private Set<UserPost> userPosts;

    public DisciplinaPost(Long idDisciplina, String nomeDisciplina, String professor, String disciplina_periodo) {
        this.idDisciplinas = idDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.professor = professor;
    }

    public DisciplinaPost() {

    }

    public Long getIdDisciplina() {
        return idDisciplinas;
    }

    public void setIdDisciplinas(Long idDisciplinas) {
        this.idDisciplinas = idDisciplinas;
    }

    public String getNome_disciplina() {
        return nomeDisciplina;
    }

    public void setNome_disciplina(String nome_disciplina) {
        this.nomeDisciplina = nome_disciplina;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Set<CursoPost> getCursos() {
        return cursos;
    }

    public void setCursos(Set<CursoPost> cursos) {
        this.cursos = cursos;
    }

    public Long getIdDisciplinas() {
        return idDisciplinas;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public Set<UserPost> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(Set<UserPost> userPosts) {
        this.userPosts = userPosts;
    }
}