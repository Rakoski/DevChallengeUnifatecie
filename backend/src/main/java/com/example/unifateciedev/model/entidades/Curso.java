package com.example.unifateciedev.model.entidades;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "nome")
    private String nome;

    @Column(name = "duracao_periodo")
    private int duracao_periodo;

    @ManyToMany(mappedBy = "cursos", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<User> usuarios;

    @ManyToMany(mappedBy = "cursos", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Disciplina> disciplinas;

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Curso(Set<User> usuario) {
        this.usuarios = usuario;
    }

    public void setUsuarios(Set<User> usuarios) {
        this.usuarios = usuarios;
    }

    public Set<User> getUsuarios() {
        return usuarios;
    }

    public Curso() {

    }

    public Curso(Long idCurso, int duracao_periodo, String nome) {
        this.idCurso = idCurso;
        this.duracao_periodo = duracao_periodo;
        this.nome = nome;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public int getDuracao_periodo() {
        return duracao_periodo;
    }

    public void setDuracao_periodo(int duracao_periodo) {
        this.duracao_periodo = duracao_periodo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Disciplina> getDisciplina() {
        return disciplinas;
    }

    public void setDisciplinaPosts(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Set<Disciplina> getDisciplinaPosts() {
        return disciplinas;
    }

}

