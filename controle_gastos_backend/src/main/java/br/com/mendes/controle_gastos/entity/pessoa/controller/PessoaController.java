package br.com.mendes.controle_gastos.entity.pessoa.controller;

import br.com.mendes.controle_gastos.entity.pessoa.dto.PessoaComTotaisDTO;
import br.com.mendes.controle_gastos.entity.pessoa.dto.PessoaMenorDeIdadeComTotaisDTO;
import br.com.mendes.controle_gastos.entity.pessoa.dto.PessoaSaveRequestDTO;
import br.com.mendes.controle_gastos.entity.pessoa.dto.PessoaResponseDTO;
import br.com.mendes.controle_gastos.entity.pessoa.model.Pessoa;
import br.com.mendes.controle_gastos.entity.pessoa.service.PessoaService;
import br.com.mendes.controle_gastos.entity.transacao.model.Transacao;
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
@RequiredArgsConstructor
@RequestMapping("/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;
    private final ObjectMapperUtil objectMapperUtil;


    /**
     * Salva uma nova pessoa no sistema.
     *
     * @param pessoaSaveRequestDTO Dados da pessoa a ser salva.
     * @return A pessoa salva, mapeada para o DTO de resposta.
     */
    @PostMapping
    public ResponseEntity<PessoaResponseDTO> save(@Valid @RequestBody PessoaSaveRequestDTO pessoaSaveRequestDTO) {
        Pessoa pessoa = objectMapperUtil.map(pessoaSaveRequestDTO, Pessoa.class);
        Pessoa savedPessoa = pessoaService.save(pessoa);
        PessoaResponseDTO pessoaResponseDTO = objectMapperUtil.map(savedPessoa, PessoaResponseDTO.class);

        return ResponseEntity.ok(pessoaResponseDTO);
    }

    /**
     * Retorna uma pessoa com base no ID fornecido.
     *
     * @param id O ID da pessoa a ser retornada.
     * @return A pessoa encontrada, mapeada para o DTO de resposta.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> getById(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.getById(id);
        PessoaResponseDTO pessoaResponseDTO = objectMapperUtil.map(pessoa, PessoaResponseDTO.class);

        return ResponseEntity.ok(pessoaResponseDTO);
    }



    /**
     * Atualiza uma pessoa com base na transação fornecida.
     *
     * @param pessoaId O ID da pessoa a ser atualizada.
     * @param transacao A transa o a ser adicionada.
     * @return A pessoa atualizada.
     */
    @PutMapping("/{pessoaId}/transacoes")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long pessoaId, @RequestBody Transacao transacao) {
        Pessoa updatedPessoa = pessoaService.update(pessoaId, transacao);
        return ResponseEntity.ok(updatedPessoa);
    }

    /**
     * Retorna uma lista paginada de todas as pessoas.
     *
     * @param pageable Parâmetros de paginação (tamanho da página, ordenação, etc.)
     * @return Uma lista paginada de pessoas, mapeada para o DTO de resposta
     */
    @GetMapping
    public ResponseEntity<Page<?>> getAll(@PageableDefault(size = 20, sort = {"id"}) Pageable pageable) {
        Page<Pessoa> pessoasPage = pessoaService.getAll(pageable);

        return getPageResponseEntity(pessoasPage);
    }

    /**
     * Converte uma página de pessoas em uma página de DTOs com totais, considerando a idade da pessoa.
     * Se a pessoa tiver menos de 18 anos, é criado um PessoaMenorDeIdadeComTotaisDTO.
     * Caso contrário, é criado um PessoaComTotaisDTO.
     *
     * @param pessoasPage Página de pessoas a ser convertida.
     * @return Página de DTOs com totais.
     */
    private ResponseEntity<Page<?>> getPageResponseEntity(Page<Pessoa> pessoasPage) {
        Page<?> pessoaComTotaisDTO = pessoasPage.map(pessoa -> {
            if (pessoa.getIdade() < 18) {
                return new PessoaMenorDeIdadeComTotaisDTO(
                        pessoa.getId(),
                        pessoa.getNome(),
                        pessoa.getIdade(),
                        pessoa.getMesada(),
                        pessoa.getDespesas(),
                        pessoa.getSaldo()
                    );
            } else {
                return objectMapperUtil.map(pessoa, PessoaComTotaisDTO.class);
            }
        });

        return ResponseEntity.ok(pessoaComTotaisDTO);
    }

    /**
     * Exclui uma pessoa com base no ID fornecido.
     *
     * @param id O ID da pessoa a ser excluída.
     * @return Uma resposta sem conteúdo (204 No Content) caso a exclusão seja bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retorna uma lista paginada de todas as pessoas, excluindo a pessoa com o ID fornecido.
     *
     * @param remetenteId O ID da pessoa a ser excluída da lista.
     * @param pageable Parâmetros de paginação (tamanho da página, ordenação, etc.)
     * @return Uma lista paginada de pessoas, excluindo a pessoa com o ID fornecido.
     */
    @GetMapping("/{remetenteId}/transferencia")
    public ResponseEntity<Page<?>> getAllExcludingRemetente(
            @PathVariable Long remetenteId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable
    ) {
        Page<Pessoa> pessoasPage = pessoaService.getAllExcludingRemetente(remetenteId, pageable);

        return getPageResponseEntity(pessoasPage);
    }

}
