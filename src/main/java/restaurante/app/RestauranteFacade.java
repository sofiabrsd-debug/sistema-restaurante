package restaurante.app;

import restaurante.cardapio.ItemCardapio;
import restaurante.cardapio.Lanche;
import restaurante.cardapio.LancheFactory;
import restaurante.pedido.GerenciadorPedidos;
import restaurante.pedido.Pedido;

import java.util.Collection;
import java.util.HashSet;

/**
 * Fachada (Facade Pattern) que unifica e coordena as operações do sistema
 * de restaurante, desacoplando o menu e o fluxo de controle das regras de negócio.
 */
public class RestauranteFacade {
    private final GerenciadorPedidos gerenciadorPedidos;

    public RestauranteFacade() {
        this.gerenciadorPedidos = new GerenciadorPedidos();
    }

    public Pedido criarPedido(String nomeCliente) {
        return gerenciadorPedidos.criarPedido(nomeCliente);
    }

    public Pedido buscarPedido(int numero) {
        return gerenciadorPedidos.buscarPedido(numero);
    }

    public boolean cancelarPedido(int numero) {
        return gerenciadorPedidos.cancelarPedido(numero);
    }

    public boolean adicionarItemAoPedido(int numeroPedido, ItemCardapio item) {
        Pedido pedido = buscarPedido(numeroPedido);
        if (pedido != null) {
            pedido.adicionarItem(item);
            return true;
        }
        return false;
    }

    public boolean removerItemDoPedido(int numeroPedido, int indice) {
        Pedido pedido = buscarPedido(numeroPedido);
        if (pedido != null) {
            return pedido.removerItem(indice);
        }
        return false;
    }

    public Collection<Pedido> listarPedidos() {
        return gerenciadorPedidos.getPedidos();
    }

    public double calcularTotalDoDia() {
        return gerenciadorPedidos.calcularTotalDoDia();
    }

    public HashSet<String> obterCombinacoesDiferentes() {
        return gerenciadorPedidos.obterCombinacoesDiferentes();
    }

    public Lanche criarLancheBase(int opcao) {
        return switch (opcao) {
            case 1 -> LancheFactory.xBurger();
            case 2 -> LancheFactory.xSalada();
            case 3 -> LancheFactory.xBacon();
            default -> throw new IllegalArgumentException("Opção de lanche inválida: " + opcao);
        };
    }
}
