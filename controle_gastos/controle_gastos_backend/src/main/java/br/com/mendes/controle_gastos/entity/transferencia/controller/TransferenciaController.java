package br.com.mendes.controle_gastos.entity.transferencia.controller;

import br.com.mendes.controle_gastos.entity.transferencia.dto.TransferenciaRequestDTO;
import br.com.mendes.controle_gastos.entity.transferencia.dto.TransferenciaResponseDTO;
import br.com.mendes.controle_gastos.entity.transferencia.model.Transferencia;
import br.com.mendes.controle_gastos.entity.transferencia.service.TransferenciaService;
import br.com.mendes.controle_gastos.infraestructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transferencia")
@RequiredArgsConstructor
public class TransferenciaController {
    private final TransferenciaService transacaoService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Transferir dinheiro de uma pessoa para outra
     *
     * @param remetenteId  id do remetente
     * @param destinatarioId  id do destinatario
     * @param transferenciaRequestDTO   objeto com valor da transferencia
     * @return  lista de objetos {@link TransferenciaResponseDTO} com informacoes da transferencia
     */
    @PostMapping("/{remetenteId}/{destinatarioId}")
    public ResponseEntity<List<TransferenciaResponseDTO>> transferir(
            @PathVariable Long remetenteId,
            @PathVariable Long destinatarioId,
            @Valid @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {

        Transferencia transacao = objectMapperUtil.map(transferenciaRequestDTO, Transferencia.class);
        List<Transferencia> transferencias = transacaoService.transferir(remetenteId, destinatarioId, transacao.getValor());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<TransferenciaResponseDTO> transferenciaResponseDTOs = transferencias.stream()
                .map(t -> new TransferenciaResponseDTO(
                        t.getDescricao(),
                        t.getRemetente(),
                        t.getDestinatario(),
                        t.getDataHora().format(formatter),
                        t.getValor()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(transferenciaResponseDTOs);
    }

}
