package br.com.mendes.controle_gastos.entity.transacao.controller;

import br.com.mendes.controle_gastos.entity.transacao.dto.TransacaoRequestDTO;
import br.com.mendes.controle_gastos.entity.transacao.dto.TransacaoResponseDTO;
import br.com.mendes.controle_gastos.entity.transacao.model.Transacao;
import br.com.mendes.controle_gastos.entity.transacao.service.TransacaoService;
import br.com.mendes.controle_gastos.infraestructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Salva uma nova transação associada a uma pessoa.
     *
     * @param pessoaId           ID da pessoa associada à transação.
     * @param transacaoRequestDTO Dados da transação a ser salva.
     * @return A transação salva, mapeada para o DTO de resposta.
     */
    @PostMapping("/{pessoaId}")
    public ResponseEntity<TransacaoResponseDTO> save(@PathVariable Long pessoaId, @Valid @RequestBody TransacaoRequestDTO transacaoRequestDTO) {
        Transacao transacao = objectMapperUtil.map(transacaoRequestDTO, Transacao.class);
        Transacao savedTransacao = this.transacaoService.save(pessoaId, transacao);
        TransacaoResponseDTO transacaoResponseDTO = objectMapperUtil.map(savedTransacao, TransacaoResponseDTO.class);

        return ResponseEntity.ok(transacaoResponseDTO);
    }


    /**
     * Retorna uma lista paginada de todas as transações.
     *
     * @param pageable Parâmetros de paginação (tamanho da página, ordenação, etc.)
     * @return Uma lista paginada de transações, mapeada para o DTO de resposta.
     */
    @GetMapping
    public ResponseEntity<Page<?>> getAll(@PageableDefault(size = 20, sort = {"id"}) Pageable pageable) {
        Page<TransacaoResponseDTO> transacaoPage = transacaoService.getAll(pageable).getBody();
        assert transacaoPage != null;
        Page<?> transacaoResponseDTO = transacaoPage.map(transacao -> objectMapperUtil.map(transacao, TransacaoResponseDTO.class));

        return ResponseEntity.ok(transacaoResponseDTO);
    }

    /**
     * Exclui uma transação com base no ID fornecido.
     *
     * @param id O ID da transação a ser excluída.
     * @return Uma resposta sem conteúdo (204 No Content) caso a exclusão seja bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
