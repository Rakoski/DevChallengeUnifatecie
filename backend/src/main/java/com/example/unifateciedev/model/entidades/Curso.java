package com.example.unifateciedev.model.entidades;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "curso_nome")
    private String nome;

    @Column(name = "duracao_periodo_curso")
    private int duracao_periodo;

    @OneToMany(targetEntity = CursoDisciplina.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id", referencedColumnName = "id_curso")
    private Set<CursoDisciplina> cursoDisciplina;

    @OneToMany(targetEntity = CursoUsuario.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id", referencedColumnName = "id_curso")
    private Set<CursoUsuario> cursoUsuarios;

    public Curso(Long idCurso, String nome, int duracao_periodo) {
        this.idCurso = idCurso;
        this.nome = nome;
        this.duracao_periodo = duracao_periodo;
    }

    public Curso() {

    }

    public void addCourseDiscipline(CursoDisciplina courseDiscipline) {
        cursoDisciplina.add(courseDiscipline);
        courseDiscipline.setCurso(this);
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDuracao_periodo() {
        return duracao_periodo;
    }

    public void setDuracao_periodo(int duracao_periodo) {
        this.duracao_periodo = duracao_periodo;
    }

    public Set<CursoDisciplina> getCursoDisciplina() {
        return cursoDisciplina;
    }

    public void setCursoDisciplina(Set<CursoDisciplina> cursoDisciplina) {
        this.cursoDisciplina = cursoDisciplina;
    }

    public Set<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setCursoUsuarios(Set<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }
}

