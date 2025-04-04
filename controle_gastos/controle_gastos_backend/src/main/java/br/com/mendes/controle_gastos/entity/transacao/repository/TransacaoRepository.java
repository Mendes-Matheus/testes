package br.com.mendes.controle_gastos.entity.transacao.repository;

import br.com.mendes.controle_gastos.entity.transacao.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransacaoRepository extends JpaRepository <Transacao, Long> {
    /**
     * Deleta todas as transa es de uma pessoa com base no ID fornecido.
     *
     * @param id O ID da pessoa a ser excluida.
     */
    void deleteAllByPessoaId(Long id);

    /**
     * Verifica se existem transa es de uma pessoa com base no ID fornecido.
     *
     * @param id O ID da pessoa a ser verificada.
     * @return true se existirem transa es, false caso contr rio.
     */
    boolean existsByPessoaId(Long id);
}
