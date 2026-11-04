package restaurante.financeiro;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma transação financeira realizada no restaurante (ex: recebimento de pedido ou cancelamento).
 */
public class Transacao {
    private final int numeroPedido;
    private final double valor;
    private final String tipo;
    private final LocalDateTime dataHora;

    public Transacao(int numeroPedido, double valor, String tipo) {
        this.numeroPedido = numeroPedido;
        this.valor = valor;
        this.tipo = tipo;
        this.dataHora = LocalDateTime.now();
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("[%s] Pedido #%d | Tipo: %s | Valor: R$ %.2f",
                dataHora.format(dtf), numeroPedido, tipo, valor);
    }
}
