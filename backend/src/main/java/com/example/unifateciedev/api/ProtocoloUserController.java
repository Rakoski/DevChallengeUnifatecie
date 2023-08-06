package com.example.unifateciedev.api;

import com.example.unifateciedev.model.entidades.ProtocoloUser;
import com.example.unifateciedev.service.repo.ProtocoloUsuarioRepository;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/protocoloUser")
public class ProtocoloUserController {

    private ProtocoloUsuarioRepository protocoloUsuarioRepository;

    public ProtocoloUserController(ProtocoloUsuarioRepository protocoloUsuarioRepository) {
        this.protocoloUsuarioRepository = protocoloUsuarioRepository;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<ProtocoloUser> registrarProtocoloUsuario(@RequestBody ProtocoloUser protocoloUsuario) {
        try {
            int generatedId = protocoloUsuarioRepository.colocarNoUsuarioProtocolo(protocoloUsuario.getUserId(),
                    protocoloUsuario.getCursoId(),
                    protocoloUsuario.getProtocoloTypeId(),
                    protocoloUsuario.getProtocoloCampo(),
                    protocoloUsuario.getProtocoloDocpath());

            protocoloUsuario.setProtocoloUser(generatedId);
            return ResponseEntity.ok(protocoloUsuario);
        } catch (Exception e) {
            System.out.print(e);

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
