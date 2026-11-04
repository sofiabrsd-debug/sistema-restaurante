package restaurante.cardapio;

/**
 * Interface que representa qualquer item comercializado no cardápio,
 * seja um lanche básico ou um item decorado com adicionais.
 */
public interface ItemCardapio {
    String getDescricao();
    double getPreco();
}
