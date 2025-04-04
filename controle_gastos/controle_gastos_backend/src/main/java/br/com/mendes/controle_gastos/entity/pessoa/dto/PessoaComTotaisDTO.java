package br.com.mendes.controle_gastos.entity.pessoa.dto;


public record PessoaComTotaisDTO (
        Long id,
        String nome,
        Integer idade,
        Double receitas,
        Double despesas,
        Double saldo
) {
}
