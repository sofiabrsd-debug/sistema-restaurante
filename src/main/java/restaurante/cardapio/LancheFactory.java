package restaurante.cardapio;

/**
 * Fábrica responsável por instanciar os lanches padrão do cardápio.
 * Cada chamada gera uma nova instância independente de Lanche.
 */
public class LancheFactory {

    private LancheFactory() {
        // Construtor privado para evitar instanciação de classe utilitária
    }

    public static Lanche xBurger() {
        return new Lanche("X-Burger", 16.0);
    }

    public static Lanche xSalada() {
        return new Lanche("X-Salada", 18.0);
    }

    public static Lanche xBacon() {
        return new Lanche("X-Bacon", 22.0);
    }

}
