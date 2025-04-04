package br.com.mendes.controle_gastos.entity.transacao.dto;

import br.com.mendes.controle_gastos.entity.transacao.model.TransacaoTipo;

public record TransacaoResponseDTO(
    String descricao,
    Double valor,
    TransacaoTipo tipo
) {
}
