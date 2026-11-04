package restaurante.cardapio;

/**
 * Decorator que adiciona Bacon Extra a um ItemCardapio.
 * Acrescenta R$ 6,00 ao preço e " + bacon extra" à descrição.
 */
public class ComBaconExtra implements ItemCardapio {
    private final ItemCardapio item;

    public ComBaconExtra(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("O item decorado não pode ser nulo.");
        }
        this.item = item;
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + bacon extra";
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 6.0;
    }

    @Override
    public String toString() {
        return String.format("%s - R$ %.2f", getDescricao(), getPreco());
    }
}
