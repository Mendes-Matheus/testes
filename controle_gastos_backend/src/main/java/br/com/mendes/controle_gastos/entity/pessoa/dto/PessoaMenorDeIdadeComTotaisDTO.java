package br.com.mendes.controle_gastos.entity.pessoa.dto;

public record PessoaMenorDeIdadeComTotaisDTO(
        Long id,
        String nome,
        Integer idade,
        Double mesada,
        Double despesas,
        Double saldo
) {
}
