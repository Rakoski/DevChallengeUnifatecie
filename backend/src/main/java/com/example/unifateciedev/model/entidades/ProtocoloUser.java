package com.example.unifateciedev.model.entidades;

import javax.persistence.*;

@Entity
@Table(name = "protocolo_usuario")
public class ProtocoloUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_protocolouser")
    private int protocoloUser;

    @Column(name = "usuario_id")
    private int userId;

    @Column(name = "curso_id")
    private int cursoId;

    @Column(name = "protocolo_typeid")
    private int protocoloTypeId;

    public int getProtocoloUser() {
        return protocoloUser;
    }

    public void setProtocoloUser(int protocoloUser) {
        this.protocoloUser = protocoloUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public int getProtocoloTypeId() {
        return protocoloTypeId;
    }

    public void setProtocoloTypeId(int protocoloTypeId) {
        this.protocoloTypeId = protocoloTypeId;
    }

    public int getProtocoloEtapa() {
        return protocoloEtapa;
    }

    public void setProtocoloEtapa(int protocoloEtapa) {
        this.protocoloEtapa = protocoloEtapa;
    }

    public String getProtocoloSetor() {
        return protocoloSetor;
    }

    public void setProtocoloSetor(String protocoloSetor) {
        this.protocoloSetor = protocoloSetor;
    }

    public String getProtocoloStatus() {
        return protocoloStatus;
    }

    public void setProtocoloStatus(String protocoloStatus) {
        this.protocoloStatus = protocoloStatus;
    }

    public String getProtocoloCampo() {
        return protocoloCampo;
    }

    public void setProtocoloCampo(String protocoloCampo) {
        this.protocoloCampo = protocoloCampo;
    }

    public String getProtocoloDocpath() {
        return protocoloDocpath;
    }

    public void setProtocoloDocpath(String protocoloDocpath) {
        this.protocoloDocpath = protocoloDocpath;
    }

    public String getProtocoloDocreturn() {
        return protocoloDocreturn;
    }

    public void setProtocoloDocreturn(String protocoloDocreturn) {
        this.protocoloDocreturn = protocoloDocreturn;
    }

    @Column(name = "protocolo_etapa")
    private int protocoloEtapa;

    @Column(name = "protocolo_setor")
    private String protocoloSetor;

    @Column(name = "protocolo_status")
    private String protocoloStatus;

    @Column(name = "protocolo_campo1")
    private String protocoloCampo;

    @Column(name = "protocolo_docpath")
    private String protocoloDocpath;

    @Column(name = "protocolo_docreturn")
    private String protocoloDocreturn;


}
