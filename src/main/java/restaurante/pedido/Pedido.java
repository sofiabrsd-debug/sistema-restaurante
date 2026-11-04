package restaurante.pedido;

import restaurante.cardapio.ItemCardapio;
import java.util.ArrayList;

/**
 * Representa uma comanda individual de um cliente com sua lista de itens.
 */
public class Pedido {
    private final int numero;
    private String nomeCliente;
    private final ArrayList<ItemCardapio> itens;

    public Pedido(int numero, String nomeCliente) {
        this.numero = numero;
        setNomeCliente(nomeCliente);
        this.itens = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio.");
        }
        this.nomeCliente = nomeCliente.trim();
    }

    public void adicionarItem(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("O item do pedido não pode ser nulo.");
        }
        itens.add(item);
    }

    public boolean removerItem(int indice) {
        if (indice >= 0 && indice < itens.size()) {
            itens.remove(indice);
            return true;
        }
        return false;
    }

    public ArrayList<ItemCardapio> getItens() {
        return itens;
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemCardapio item : itens) {
            total += item.getPreco();
        }
        return total;
    }
}
