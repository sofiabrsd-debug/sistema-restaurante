package restaurante.financeiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controla o caixa financeiro do restaurante, registrando entradas de vendas,
 * estornos de cancelamentos e calculando o saldo acumulado.
 */
public class Caixa {
    private final List<Transacao> transacoes;
    private double saldo;

    public Caixa() {
        this.transacoes = new ArrayList<>();
        this.saldo = 0.0;
    }

    public void registrarVenda(int numeroPedido, double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor da transação não pode ser negativo.");
        }
        Transacao transacao = new Transacao(numeroPedido, valor, "VENDA");
        transacoes.add(transacao);
        saldo += valor;
    }

    public void registrarCancelamento(int numeroPedido, double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor do cancelamento não pode ser negativo.");
        }
        Transacao transacao = new Transacao(numeroPedido, valor, "CANCELAMENTO");
        transacoes.add(transacao);
        saldo -= valor;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Transacao> getTransacoes() {
        return Collections.unmodifiableList(transacoes);
    }
}
