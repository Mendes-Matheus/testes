package br.com.mendes.controle_gastos.entity.pessoa.service;


import br.com.mendes.controle_gastos.entity.pessoa.exception.PessoaException;
import br.com.mendes.controle_gastos.entity.pessoa.model.Pessoa;
import br.com.mendes.controle_gastos.entity.pessoa.repository.PessoaRepository;
import br.com.mendes.controle_gastos.entity.transacao.model.Transacao;
import br.com.mendes.controle_gastos.entity.transacao.model.TransacaoTipo;
import br.com.mendes.controle_gastos.entity.transacao.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final TransacaoRepository transacaoRepository;

    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    /**
     * Retorna uma pessoa com base no ID fornecido.
     *
     * @param id O ID da pessoa a ser retornada.
     * @return A pessoa encontrada.
     * @throws PessoaException se a pessoa com o ID fornecido não for encontrada.
     */
    public Pessoa getById(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> PessoaException.notFound(id));
    }

    /**
     * Retorna uma lista paginada de todas as pessoas.
     *
     * @param pageable Parâmetros de paginação (tamanho da página, ordenação, etc.)
     * @return Uma lista paginada de pessoas.
     */
    public Page<Pessoa> getAll(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }


    /**
     * Atualiza uma pessoa com uma transacao.
     *
     * @param pessoaId   ID da pessoa a ser atualizada.
     * @param transacao  Transacao a ser adicionada a pessoa.
     * @return A pessoa atualizada.
     */
    @Transactional
    public Pessoa update(Long pessoaId, Transacao transacao) {
        Pessoa pessoa = transacao.getPessoa();
        validarTransacao(transacao, pessoa);

        return pessoaRepository.findById(pessoaId)
                .map(p -> {
                    atualizarValores(p, transacao);
                    return pessoaRepository.save(p);
                })
                .orElseThrow(() -> PessoaException.notFound(pessoaId));
    }

    /**
     * Valida se a transação é válida para ser adicionada a uma pessoa.
     *
     * @param transacao A transação a ser validada.
     * @param pessoa    A pessoa que está recebendo a transação.
     */
    private void validarTransacao(Transacao transacao, Pessoa pessoa) {
        if (transacao.getValor() <= 0) {
            throw PessoaException.valorTransacaoInvalido();
        }
        if (transacao.getTipo().equals(TransacaoTipo.DESPESA) && pessoa.getSaldo() < transacao.getValor()) {
            throw PessoaException.saldoInsuficiente();
        }
    }

    /**
     * Atualiza os valores de uma pessoa com base em uma transação.
     * <p>
     * A lógica de atualização é a seguinte:
     * <ul>
     * <li>Se a transação for do tipo RECEITA, soma o valor da transação ao valor total de receitas e ao saldo da pessoa.</li>
     * <li>Se a transação for do tipo MESADA, soma o valor da transação ao valor total de mesadas e ao saldo da pessoa.</li>
     * <li>Se a transação for do tipo DESPESA, soma o valor da transação ao valor total de despesas e subtrai o valor da transação do saldo da pessoa.</li>
     * </ul>
     *
     * @param pessoa   A pessoa a ser atualizada.
     * @param transacao A transação a ser considerada.
     */
    private void atualizarValores(Pessoa pessoa, Transacao transacao) {
        switch (transacao.getTipo()) {
            case RECEITA:
                pessoa.setReceitas(pessoa.getReceitas() + transacao.getValor());
                pessoa.setSaldo(pessoa.getSaldo() + transacao.getValor());
                break;
            case MESADA:
                pessoa.setMesada(pessoa.getMesada() + transacao.getValor());
                pessoa.setSaldo(pessoa.getSaldo() + transacao.getValor());
                break;
            case DESPESA:
                pessoa.setDespesas(pessoa.getDespesas() + transacao.getValor());
                pessoa.setSaldo(pessoa.getSaldo() - transacao.getValor());
                break;
            default:
                throw new IllegalArgumentException("Tipo de transação desconhecido: " + transacao.getTipo());
        }
    }

    /**
     * Exclui uma pessoa com base no ID fornecido.
     *
     * @param id O ID da pessoa a ser excluída.
     * @throws PessoaException se a pessoa com o ID fornecido não for encontrada.
     * @throws RuntimeException se ocorrer um erro inesperado durante a exclusão.
     */
    @Transactional
    public void delete(Long id) {
        try {
            // Verifica se a pessoa existe
            if (!pessoaRepository.existsById(id)) {
                throw PessoaException.notFound(id);
            }
            // Verifica se existem transações associadas à pessoa e as deleta
            if (transacaoRepository.existsByPessoaId(id)) {
                transacaoRepository.deleteAllByPessoaId(id);
            }
            pessoaRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Erro inesperado ao excluir pessoa com id {}: {}", id, e.getMessage());
            throw new RuntimeException("Erro inesperado ao excluir pessoa com id " + id, e);
        }
    }

    public Page<Pessoa> getAllExcludingRemetente(Long pessoaId, Pageable pageable) {
        if (pessoaId != null) {
            return pessoaRepository.findAllByIdNot(pessoaId, pageable);
        }
        return pessoaRepository.findAll(pageable);
    }

}
