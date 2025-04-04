package br.com.mendes.controle_gastos.entity.transferencia.exception;

public class TransferenciaException extends RuntimeException {

    public TransferenciaException(String message) {
        super(message);
    }

    /**
     * Lança uma exceção indicando que o saldo é insuficiente para realizar a transferência.
     *
     * @return Uma instância de TransferenciaException com a mensagem de erro apropriada.
     */
    public static TransferenciaException saldoInsuficiente() {
        return new TransferenciaException("Saldo insuficiente para realizar essa transferência.");
    }

    /**
     * Lança uma exceção indicando que menores de idade não podem realizar transferência.
     *
     * @return Uma instância de TransferenciaException com a mensagem de erro apropriada.
     */
    public static TransferenciaException menorDeIdade() {
        return new TransferenciaException("Menores de idade não podem realizar transferência.");
    }

    /**
     * Lança uma exceção indicando que o remetente não foi encontrado.
     *
     * @return Uma instância de TransferenciaException com a mensagem de erro apropriada.
     */
    public static TransferenciaException remetenteNaoEncontrado() {
        return new TransferenciaException("Remetente não encontrado.");
    }

    /**
     * Lança uma exceção indicando que o destinatário não foi encontrado.
     *
     * @return Uma instância de TransferenciaException com a mensagem de erro apropriada.
     */
    public static TransferenciaException destinatarioNaoEncontrado() {
        return new TransferenciaException("Destinatário não encontrado.");
    }
}
