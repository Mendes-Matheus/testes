package br.com.mendes.controle_gastos.entity.pessoa.repository;

import br.com.mendes.controle_gastos.entity.pessoa.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    /**
     * Retorna uma lista paginada de todas as pessoas, excluindo a pessoa com o ID fornecido.
     *
     * @param pessoaId O ID da pessoa a ser excluida da lista.
     * @param pageable Parâmetros de pagina   o (tamanho da p  gina, ordena   o, etc.).
     * @return Uma lista paginada de pessoas, excluindo a pessoa com o ID fornecido.
     */
    Page<Pessoa> findAllByIdNot(Long pessoaId, Pageable pageable);
}
