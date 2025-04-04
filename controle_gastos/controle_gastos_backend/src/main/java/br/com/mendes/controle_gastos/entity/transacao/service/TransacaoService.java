package br.com.mendes.controle_gastos.entity.transacao.service;

import br.com.mendes.controle_gastos.entity.pessoa.model.Pessoa;
import br.com.mendes.controle_gastos.entity.pessoa.repository.PessoaRepository;
import br.com.mendes.controle_gastos.entity.pessoa.service.PessoaService;
import br.com.mendes.controle_gastos.entity.transacao.dto.TransacaoResponseDTO;
import br.com.mendes.controle_gastos.entity.transacao.exception.TransacaoException;
import br.com.mendes.controle_gastos.entity.transacao.model.Transacao;
import br.com.mendes.controle_gastos.entity.transacao.model.TransacaoTipo;
import br.com.mendes.controle_gastos.entity.transacao.repository.TransacaoRepository;
import br.com.mendes.controle_gastos.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final PessoaRepository pessoaRepository;
    private final PessoaService pessoaService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Salva uma nova transação associada a uma pessoa.
     *
     * <p>
     * Verifica se a pessoa existe e se a idade da pessoa é maior que 18
     * (caso seja uma receita).
     * </p>
     *
     * <p>
     * Verifica se o saldo da pessoa é maior que o valor da transação (caso seja uma despesa).
     * </p>
     *
     * <p>
     * Atualiza o saldo da pessoa com base na transação.
     * </p>
     *
     * @param pessoaId   ID da pessoa associada à transação.
     * @param transacao  A transação a ser salva.
     * @return A transação salva.
     */
    public Transacao save(Long pessoaId, Transacao transacao) {
        // Verifica se a pessoa existe
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(TransacaoException::pessoaNaoEncontrada);
        // Associa a pessoa à transação
        transacao.setPessoa(pessoa);
        transacao.setDataHora(LocalDateTime.now());

        // Verifica a idade da pessoa
        if (pessoa.getIdade() < 18 && transacao.getTipo().equals(TransacaoTipo.RECEITA)) {
            throw TransacaoException.menorDeIdade();
        }

        if (pessoa.getSaldo() < transacao.getValor() && transacao.getTipo().equals(TransacaoTipo.DESPESA)) {
            throw TransacaoException.saldoInsuficiente();
        }

        // Atualiza o saldo da pessoa
        pessoaService.update(pessoaId, transacao);

        // Salva a transação
        return transacaoRepository.save(transacao);
    }

    /**
     * Retorna uma lista paginada de todas as transações.
     *
     * <p>
     * A lista é ordenada por ID em ordem crescente.
     * </p>
     *
     * @param pageable Parâmetros de paginação (tamanho da página, ordenação, etc.)
     * @return Uma lista paginada de transações, mapeada para o DTO de resposta.
     */
    @GetMapping
    public ResponseEntity<Page<TransacaoResponseDTO>> getAll(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        Page<Transacao> transacaoPage = transacaoRepository.findAll(pageable);
        Page<TransacaoResponseDTO> transacaoResponseDTO = transacaoPage.map(transacao -> objectMapperUtil.map(transacao, TransacaoResponseDTO.class));

        return ResponseEntity.ok(transacaoResponseDTO);
    }


    /**
     * Deleta uma transa o com base no ID fornecido.
     *
     * @param id O ID da transa o a ser excluída.
     */
    public void delete(Long id){
        transacaoRepository.deleteById(id);
    }

}
