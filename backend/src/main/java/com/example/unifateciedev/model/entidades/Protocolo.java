package com.example.unifateciedev.model.entidades;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.*;

@Entity
@Table(name = "protocolo")
public class Protocolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_protocolo")
    private Integer idProtocolo;

    @Column(name = "protocolo_nome")
    private String protocoloNome;

    @Column(name = "protocolo_descricao")
    private String procoloDescicao;

    @Column(name = "status_curso_protocolo")
    private String statusCursoProtocolo;

    @Column(name = "protocolo_docname")
    private String protocoloDocNome;

    @Column(name = "protocolo_doctype")
    private String procoloDocType;

    public Integer getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public String getProtocoloNome() {
        return protocoloNome;
    }

    public void setProtocoloNome(String protocoloNome) {
        this.protocoloNome = protocoloNome;
    }

    public String getProcoloDescicao() {
        return procoloDescicao;
    }

    public void setProcoloDescicao(String procoloDescicao) {
        this.procoloDescicao = procoloDescicao;
    }

    public String getStatusCursoProtocolo() {
        return statusCursoProtocolo;
    }

    public void setStatusCursoProtocolo(String statusCursoProtocolo) {
        this.statusCursoProtocolo = statusCursoProtocolo;
    }

    public String getProtocoloDocNome() {
        return protocoloDocNome;
    }

    public void setProtocoloDocNome(String protocoloDocNome) {
        this.protocoloDocNome = protocoloDocNome;
    }

    public String getProcoloDocType() {
        return procoloDocType;
    }

    public void setProcoloDocType(String procoloDocType) {
        this.procoloDocType = procoloDocType;
    }
}
