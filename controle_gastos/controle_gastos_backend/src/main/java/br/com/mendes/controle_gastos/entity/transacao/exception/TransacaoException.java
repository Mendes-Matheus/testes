package br.com.mendes.controle_gastos.entity.transacao.exception;

public class TransacaoException extends RuntimeException {

    public TransacaoException(String message) {
        super(message);
    }

    /**
     * Lança uma exceção indicando que o saldo é insuficiente para realizar a transação.
     *
     * @return Uma instância de TransacaoException com a mensagem de erro apropriada.
     */
    public static TransacaoException saldoInsuficiente() {
        return new TransacaoException("Saldo insuficiente para realizar essa transação.");
    }

    /**
     * Lança uma exceção indicando que o menor de idade pode apenas registrar despesas.
     *
     * @return Uma instância de TransacaoException com a mensagem de erro apropriada.
     */
    public static TransacaoException menorDeIdade() {
        return new TransacaoException("Menores de idade podem apenas registrar despesas.");
    }


    /**
     * Lança uma exceção indicando que a pessoa não foi encontrada.
     *
     * @return Uma instancia de TransacaoException com a mensagem de erro apropriada.
     */
    public static TransacaoException pessoaNaoEncontrada() {
        return new TransacaoException("Pessoa n o encontrada.");
    }
}