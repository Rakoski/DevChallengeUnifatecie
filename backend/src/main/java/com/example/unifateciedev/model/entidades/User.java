package com.example.unifateciedev.model.entidades;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "usuario_email")
    private String email;

    @Column(name = "usuario_nome")
    private String nome;

    @Column(name = "usuario_sobrenome")
    private String sobrenome;

    @Transient
    private String password;

    @Column(name = "password_hash")
    private byte[] passwordHash;

    @Column(name = "password_salt")
    private byte[] passwordSalt;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = CursoUsuario.class)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    private Set<CursoUsuario> cursoUsuario;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = UsuarioDisciplina.class)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    private Set<UsuarioDisciplina> usuarioDisciplina;

    public User() {

    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public Set<CursoUsuario> getCursoUsuario() {
        return cursoUsuario;
    }

    public void setCursoUsuario(Set<CursoUsuario> cursoUsuario) {
        this.cursoUsuario = cursoUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UsuarioDisciplina> getUsuarioDisciplina() {
        return usuarioDisciplina;
    }

    public void setUsuarioDisciplina(Set<UsuarioDisciplina> usuarioDisciplina) {
        this.usuarioDisciplina = usuarioDisciplina;
    }
}
