package br.com.mendes.controle_gastos.entity.pessoa.exception;

public class PessoaException extends RuntimeException {

    public PessoaException(String message) {
        super(message);
    }

    /**
     * Lança uma exceção indicando que uma pessoa com o ID especificado não foi encontrada.
     *
     * @param id O ID da pessoa que não foi encontrada.
     * @return Uma instância de PessoaException com a mensagem de erro apropriada.
     */
    public static PessoaException notFound(Long id) {
        return new PessoaException("Pessoa com ID " + id + " não encontrada.");
    }

    /**
     * Lança uma exceção indicando que o saldo é insuficiente.
     *
     * @return Uma instância de PessoaException com a mensagem de erro apropriada.
     */
    public static PessoaException saldoInsuficiente() {
        return new PessoaException("Saldo insuficiente.");
    }

    /**
     * Lança uma exceção indicando que o valor da transação é invalido.
     * <p>
     * A exceção lançada quando o valor da transação o menor ou igual a zero.
     * </p>
     * @return Uma instancia de PessoaException com a mensagem de erro apropriada.
     */
    public static PessoaException valorTransacaoInvalido() {
        return new PessoaException("O valor da transa o deve ser positivo.");
    }
}