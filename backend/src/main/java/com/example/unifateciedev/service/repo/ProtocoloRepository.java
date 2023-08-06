package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.Protocolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProtocoloRepository extends JpaRepository<Protocolo, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO protocolo(protocolo_nome, protocolo_descricao, status_curso_protocolo, protocolo_docname, protocolo_doctype) " +
            "VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    Integer inserirProtocolo(String protocoloNome, String protocoloDescricao, String statusCursoProtocolo,
                             String protocoloDocName, String protocoloDocType);

    @Query("SELECT p FROM Protocolo p WHERE p.statusCursoProtocolo LIKE %?1%")
    List<Protocolo> findByStatusCursoProtocolo(String statusCursoProtocolo);
}
