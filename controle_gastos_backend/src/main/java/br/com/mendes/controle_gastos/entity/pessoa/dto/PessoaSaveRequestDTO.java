package br.com.mendes.controle_gastos.entity.pessoa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PessoaSaveRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @Min(value = 0, message = "A idade deve ser maior ou igual a zero")
        Integer idade
) {
}
