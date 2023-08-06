package com.example.unifateciedev.api;

import com.example.unifateciedev.model.entidades.Protocolo;
import com.example.unifateciedev.service.repo.ProtocoloRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/protocolo")
public class ProtocoloController {

    private ProtocoloRepository protocoloRepository;

    public ProtocoloController(ProtocoloRepository protocoloRepository) {
        this.protocoloRepository = protocoloRepository;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<Protocolo> registrarProtocolo(@RequestBody Protocolo protocolo) {
        try {
            Integer generatedId = protocoloRepository.inserirProtocolo(
                    protocolo.getProtocoloNome(),
                    protocolo.getProcoloDescicao(),
                    protocolo.getStatusCursoProtocolo(),
                    protocolo.getProtocoloDocNome(),
                    protocolo.getProcoloDocType()
            );

            protocolo.setIdProtocolo(generatedId);
            return ResponseEntity.ok(protocolo);
        } catch (Exception e) {
            System.out.print(e);

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/findByStatusCursoProtocolo/{statusCursoProtocolo}")
    public List<Protocolo> findByStatusCursoProtocolo(@PathVariable String statusCursoProtocolo) {
        return protocoloRepository.findByStatusCursoProtocolo(statusCursoProtocolo);
    }

}
