package br.com.mendes.controle_gastos.entity.transferencia.service;

import br.com.mendes.controle_gastos.entity.pessoa.model.Pessoa;
import br.com.mendes.controle_gastos.entity.pessoa.repository.PessoaRepository;
import br.com.mendes.controle_gastos.entity.pessoa.service.PessoaService;
import br.com.mendes.controle_gastos.entity.transacao.model.TransacaoTipo;
import br.com.mendes.controle_gastos.entity.transferencia.exception.TransferenciaException;
import br.com.mendes.controle_gastos.entity.transferencia.model.Transferencia;
import br.com.mendes.controle_gastos.entity.transferencia.repository.TransferenciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TransferenciaService {
    private final TransferenciaRepository transferenciaRepository;
    private final PessoaRepository pessoaRepository;
    private final PessoaService pessoaService;


    /**
     * Transfere dinheiro de uma pessoa para outra.
     * <p>
     * Verifica se a pessoa existe, se a pessoa tem saldo suficiente, se a pessoa tem idade menor de 18 anos e se o destinatario tem idade menor de 18 anos.
     * Se todas as verifica estiverem corretas, atualiza o saldo da pessoa e salva a transferencia.
     * <p>
     * Retorna a lista de transferências.
     *
     * @param remetenteId  Id do remetente.
     * @param destinatarioId  Id do destinatario.
     * @param valor  Valor da transferencia.
     * @return  A lista de transferencias.
     */
    @Transactional
    public List<Transferencia> transferir(Long remetenteId, Long destinatarioId, Double valor) {
        Pessoa remetente = pessoaRepository.findById(remetenteId)
                .orElseThrow(TransferenciaException::remetenteNaoEncontrado);

        Pessoa destinatario = pessoaRepository.findById(destinatarioId)
                .orElseThrow(TransferenciaException::destinatarioNaoEncontrado);

        Transferencia transferenciaRemetente = new Transferencia();
        transferenciaRemetente.setValor(valor);
        transferenciaRemetente.setTipo(TransacaoTipo.DESPESA);
        transferenciaRemetente.setPessoa(remetente);
        transferenciaRemetente.setRemetente(remetente.getNome());
        transferenciaRemetente.setDestinatario(destinatario.getNome());
        transferenciaRemetente.setDataHora(LocalDateTime.now());
        transferenciaRemetente.setDescricao("Transferencia enviada para: " + destinatario.getNome());

        Transferencia transferenciaDestinatario = new Transferencia();
        transferenciaDestinatario.setValor(valor);
        if (destinatario.getIdade() < 18 && remetente.getIdade() > 18) {
            transferenciaDestinatario.setTipo(TransacaoTipo.MESADA);
        } else {
            transferenciaDestinatario.setTipo(TransacaoTipo.RECEITA);
        }
        transferenciaDestinatario.setPessoa(destinatario);
        transferenciaDestinatario.setRemetente(remetente.getNome());
        transferenciaDestinatario.setDestinatario(destinatario.getNome());
        transferenciaDestinatario.setDataHora(LocalDateTime.now());
        transferenciaDestinatario.setDescricao("Transferencia recebida de: " + remetente.getNome());


        if (remetente.getIdade() < 18) {
            throw TransferenciaException.menorDeIdade();
        }

        if (remetente.getSaldo() < transferenciaRemetente.getValor()) {
            throw TransferenciaException.saldoInsuficiente();
        }

        pessoaService.update(destinatarioId, transferenciaDestinatario);
        pessoaService.update(remetenteId, transferenciaRemetente);

        Transferencia reciboRemetente = transferenciaRepository.save(transferenciaRemetente);
        Transferencia reciboDestinatario = transferenciaRepository.save(transferenciaDestinatario);

        List<Transferencia> transferencia = new ArrayList<>();
        transferencia.add(reciboRemetente);
        transferencia.add(reciboDestinatario);

        return transferencia;

    }
}
