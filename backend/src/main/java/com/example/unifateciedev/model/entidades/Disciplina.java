package com.example.unifateciedev.model.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disciplinas")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplinas")
    private Long idDisciplinas;

    @Column(name = "nome_disciplina")
    private String nomeDisciplina;

    @Column(name = "professor")
    private String professor;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cursos_disciplinas",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplinas_id")
    )
    @JsonBackReference
    private Set<Curso> cursos;

    public Long getIdDisciplinas() {
        return idDisciplinas;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getProfessor() {
        return professor;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    @ManyToMany
    @JoinTable(
            name = "usuario_disciplina",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    @JsonManagedReference
    private Set<User> user;

    public Disciplina(Long idDisciplina, String nomeDisciplina, String professor) {
        this.idDisciplinas = idDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.professor = professor;
    }

    public Disciplina() {

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

    public void setNome_disciplina(String nome) {
        this.nomeDisciplina = nome;
    }

    public String getProfesor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }
}