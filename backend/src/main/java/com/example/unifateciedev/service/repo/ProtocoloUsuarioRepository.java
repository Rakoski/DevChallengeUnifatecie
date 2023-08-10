package com.example.unifateciedev.service.repo;

import com.example.unifateciedev.model.entidades.ProtocoloUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("Protocolo_usuario")
public interface ProtocoloUsuarioRepository extends JpaRepository<ProtocoloUser, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuarios.protocolo_usuario(usuario_id, curso_id, protocolo_typeid, protocolo_etapa, protocolo_setor, protocolo_status, protocolo_campo1, protocolo_docpath) " +
            "VALUES (?1, ?2, ?3, 0, 'ALUNO', 'EM PROCESSO', ?4, ?5)", nativeQuery = true)
    Integer colocarNoUsuarioProtocolo(int usuarioId, int cursoId, int protocoloTypeId, String protocoloCampo1, String protocoloDocPath);
}
