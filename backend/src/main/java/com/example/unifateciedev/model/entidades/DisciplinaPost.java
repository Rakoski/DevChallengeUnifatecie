package com.example.unifateciedev.model.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disciplina")
public class DisciplinaPost {
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
    private Set<CursoDisciplinaPost> CursoDisciplina;

    @OneToMany(targetEntity = UsuarioDisciplina.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id_disciplina")
    private Set<UsuarioDisciplinaPost> usuarioDisciplina;

    public DisciplinaPost() {

    }

    public DisciplinaPost(Long idDisciplinas, String nomeDisciplina, String professor) {
        this.idDisciplinas = idDisciplinas;
        this.nomeDisciplina = nomeDisciplina;
        this.professor = professor;
    }

    public Set<UsuarioDisciplinaPost> getUsuarioDisciplina() {
        return usuarioDisciplina;
    }

    public void setUsuarioDisciplina(Set<UsuarioDisciplinaPost> usuarioDisciplina) {
        this.usuarioDisciplina = usuarioDisciplina;
    }

    public Set<CursoDisciplinaPost> getCursoDisciplina() {
        return CursoDisciplina;
    }

    public void setCursoDisciplina(Set<CursoDisciplinaPost> cursoDisciplina) {
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