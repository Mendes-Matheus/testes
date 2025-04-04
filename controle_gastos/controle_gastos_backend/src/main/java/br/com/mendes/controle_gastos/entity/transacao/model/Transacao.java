    package br.com.mendes.controle_gastos.entity.transacao.model;

    import br.com.mendes.controle_gastos.entity.pessoa.model.Pessoa;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDateTime;

    /**
     * Representa a entidade Transação na base de dados
     */
    @Entity
    @Table(name = "transacao")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Transacao {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(nullable = false)
        private String descricao;

        @Column(nullable = false)
        private Double valor;

        @Column(nullable = false)
        private LocalDateTime dataHora;

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private TransacaoTipo tipo;

        @JoinColumn(name = "pessoa_id", nullable = false)
        @ManyToOne
        private Pessoa pessoa;


    }
