package com.example.unifateciedev.model.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class CursoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "nome")
    private String nome;

    @Column(name = "duracao_periodo")
    private int duracao_periodo;

    @ManyToMany(mappedBy = "cursos", fetch = FetchType.EAGER)
    private Set<UserPost> usuarios;

    @ManyToMany(mappedBy = "cursos", fetch = FetchType.EAGER)
    private Set<DisciplinaPost> disciplinas;

    public CursoPost(Set<UserPost> usuario) {
        this.usuarios = usuario;
    }

    public void setUsuarios(Set<UserPost> usuarios) {
        this.usuarios = usuarios;
    }

    public Set<UserPost> getUsuarios() {
        return usuarios;
    }

    public CursoPost() {

    }

    public CursoPost(Long idCurso, int duracao_periodo, String nome) {
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

    public void setDisciplina(Set<DisciplinaPost> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Set<DisciplinaPost> getDisciplinaPosts() {
        return disciplinas;
    }
}
