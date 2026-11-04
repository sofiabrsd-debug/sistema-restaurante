package restaurante.pedido;

import restaurante.cardapio.ItemCardapio;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Gerencia todas as comandas do restaurante, garantindo a unicidade
 * dos números de pedidos e o fechamento do dia.
 */
public class GerenciadorPedidos {
    private final HashMap<Integer, Pedido> pedidos = new HashMap<>();
    private int contadorPedidos = 1;

    public Pedido criarPedido(String nomeCliente) {
        Pedido novo = new Pedido(contadorPedidos, nomeCliente);
        pedidos.put(novo.getNumero(), novo);
        contadorPedidos++;
        return novo;
    }

    public Pedido buscarPedido(int numero) {
        return pedidos.get(numero);
    }

    public boolean cancelarPedido(int numero) {
        return pedidos.remove(numero) != null;
    }

    public Collection<Pedido> getPedidos() {
        return pedidos.values();
    }

    public double calcularTotalDoDia() {
        double total = 0.0;
        for (Pedido p : pedidos.values()) {
            total += p.calcularTotal();
        }
        return total;
    }

    public HashSet<String> obterCombinacoesDiferentes() {
        HashSet<String> combinacoes = new HashSet<>();
        for (Pedido p : pedidos.values()) {
            for (ItemCardapio item : p.getItens()) {
                combinacoes.add(item.getDescricao());
            }
        }
        return combinacoes;
    }

    public int getTotalPedidos() {
        return pedidos.size();
    }
}
