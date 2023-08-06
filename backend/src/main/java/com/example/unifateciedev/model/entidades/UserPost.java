package com.example.unifateciedev.model.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class UserPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name = "email")
    private String email;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Transient
    String password;

    @Column(name = "password_hash")
    private byte[] passwordHash;

    @Column(name = "password_salt")
    private byte[] passwordSalt;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cursos_usuario",
            joinColumns = {
                    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "curso_id", referencedColumnName = "id_curso")
            }
    )
    private Set<CursoPost> cursos;

    @ManyToMany(mappedBy = "cursos", fetch = FetchType.EAGER)
    private Set<DisciplinaPost> disciplinas;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Set<DisciplinaPost> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<DisciplinaPost> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Set<CursoPost> getCursos() {
        return cursos;
    }

    public void setCursos(Set<CursoPost> cursos) {
        this.cursos = cursos;
    }

    public Long getId_user() {
        return id_usuario;
    }

    public void setId_user(Long id_user) {
        this.id_usuario = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
}