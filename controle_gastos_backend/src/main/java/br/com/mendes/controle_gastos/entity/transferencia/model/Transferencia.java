package br.com.mendes.controle_gastos.entity.transferencia.model;

import br.com.mendes.controle_gastos.entity.transacao.model.Transacao;
import jakarta.persistence.Entity;
import lombok.*;

/**
 * Representa a entidade Transferencia na base de dados
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transferencia extends Transacao {
    private String remetente;
    private String destinatario;
}
