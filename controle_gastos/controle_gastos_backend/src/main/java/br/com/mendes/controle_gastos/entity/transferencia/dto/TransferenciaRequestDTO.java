package br.com.mendes.controle_gastos.entity.transferencia.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransferenciaRequestDTO(

        @NotNull(message = "O valor é obrigatório")
        @Min(value = 0, message = "O valor deve ser maior que zero")
        Double valor
) {
}
