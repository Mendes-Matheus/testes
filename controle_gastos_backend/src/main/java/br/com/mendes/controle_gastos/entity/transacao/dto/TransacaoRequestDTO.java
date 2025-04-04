package br.com.mendes.controle_gastos.entity.transacao.dto;

import br.com.mendes.controle_gastos.entity.transacao.model.TransacaoTipo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransacaoRequestDTO(
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotNull(message = "O valor é obrigatório")
        @Min(value = 0, message = "O valor deve ser maior que zero")
        Double valor,

        @NotNull(message = "O tipo é obrigatório")
        TransacaoTipo tipo
) {
}
