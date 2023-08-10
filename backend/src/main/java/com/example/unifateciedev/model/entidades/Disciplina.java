package com.example.unifateciedev.model.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disciplina")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplina")
    private Long idDisciplinas;

    @Column(name = "nome_disciplina")
    private String nomeDisciplina;

    @Column(name = "professor_disciplina")
    private String professor;

    @OneToMany(targetEntity = CursoDisciplina.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id_disciplina")
    private Set<CursoDisciplina> CursoDisciplina;

    @OneToMany(targetEntity = UsuarioDisciplina.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id_disciplina")
    private Set<UsuarioDisciplina> usuarioDisciplina;

    public Disciplina() {

    }

    public Disciplina(Long idDisciplinas, String nomeDisciplina, String professor) {
        this.idDisciplinas = idDisciplinas;
        this.nomeDisciplina = nomeDisciplina;
        this.professor = professor;
    }

    public Set<UsuarioDisciplina> getUsuarioDisciplina() {
        return usuarioDisciplina;
    }

    public void setUsuarioDisciplina(Set<UsuarioDisciplina> usuarioDisciplina) {
        this.usuarioDisciplina = usuarioDisciplina;
    }

    public Set<com.example.unifateciedev.model.entidades.CursoDisciplina> getCursoDisciplina() {
        return CursoDisciplina;
    }

    public void setCursoDisciplina(Set<com.example.unifateciedev.model.entidades.CursoDisciplina> cursoDisciplina) {
        CursoDisciplina = cursoDisciplina;
    }

    public Long getIdDisciplinas() {
        return idDisciplinas;
    }

    public void setIdDisciplinas(Long idDisciplinas) {
        this.idDisciplinas = idDisciplinas;
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

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}