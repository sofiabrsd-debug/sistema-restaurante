package restaurante.cardapio;

/**
 * Decorator que adiciona Cheddar a um ItemCardapio.
 * Acrescenta R$ 4,00 ao preço e " + cheddar" à descrição.
 */
public class ComCheddar implements ItemCardapio {
    private final ItemCardapio item;

    public ComCheddar(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("O item decorado não pode ser nulo.");
        }
        this.item = item;
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + cheddar";
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 4.0;
    }

    @Override
    public String toString() {
        return String.format("%s - R$ %.2f", getDescricao(), getPreco());
    }
}
