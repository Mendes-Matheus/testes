package br.com.mendes.controle_gastos.entity.pessoa.model;

import br.com.mendes.controle_gastos.entity.transacao.model.Transacao;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa a entidade Pessoa na base de dados
 */
@Entity
@Table(name = "pessoa")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer idade;

    @Column
    private Double receitas = 0.0;

    @Column
    private Double despesas = 0.0;

    @Column Double mesada = 0.0;

    @Column
    private Double saldo = 0.0;

    @JoinColumn(name = "transacao_id")
    @OneToMany
    private List<Transacao> transacoes;

}
