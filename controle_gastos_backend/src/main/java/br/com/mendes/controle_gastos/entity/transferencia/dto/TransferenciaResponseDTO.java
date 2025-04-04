package br.com.mendes.controle_gastos.entity.transferencia.dto;

public record TransferenciaResponseDTO(
        String descricao,
        String remetente,
        String destinatario,
        String dataHora,
        Double valor
) {
}
