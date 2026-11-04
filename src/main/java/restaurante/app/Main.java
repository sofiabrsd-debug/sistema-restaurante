package restaurante.app;

import restaurante.cardapio.ItemCardapio;
import restaurante.cardapio.Lanche;
import restaurante.pedido.Pedido;

import java.util.Collection;
import java.util.Set;


 // Oii pro! Aqui é onde o "software" vai funcionar para os funcionários.
// o fluxo principal é por aqui e toda a interação com o usuário ao MenuConsole
 // e as operações de negócio à RestauranteFacade também.

public class Main {
    public static void main(String[] args) {
        RestauranteFacade facade = new RestauranteFacade();
        MenuConsole menu = new MenuConsole();

        boolean executando = true;
        while (executando) {
            menu.exibirMenuPrincipal();
            int opcao = menu.lerOpcao();

            switch (opcao) {
                case 1 -> registrarNovoPedido(facade, menu);
                case 2 -> listarPedidos(facade, menu);
                case 3 -> modificarPedido(facade, menu);
                case 4 -> fecharRestaurante(facade, menu);
                case 0 -> {
                    menu.exibirMensagem("Saindo do sistema... Até logo!");
                    executando = false;
                }
                default -> menu.exibirErro("Opção inválida! Escolha uma opção do menu.");
            }
        }
    }

    private static void registrarNovoPedido(RestauranteFacade facade, MenuConsole menu) {
        String nomeCliente = menu.lerNomeCliente();
        Pedido pedido = facade.criarPedido(nomeCliente);

        boolean adicionarMaisItens = true;
        while (adicionarMaisItens) {
            int opcaoLanche = menu.lerOpcaoLanche();
            Lanche lanche = facade.criarLancheBase(opcaoLanche);
            menu.personalizarLanche(lanche);
            ItemCardapio itemFinal = menu.configurarAdicionais(lanche);

            facade.adicionarItemAoPedido(pedido.getNumero(), itemFinal);
            menu.exibirSucesso("Item adicionado: " + itemFinal.getDescricao());

            adicionarMaisItens = menu.perguntarAdicionarMaisUmItem();
        }

        menu.exibirPedido(pedido);
    }

    private static void listarPedidos(RestauranteFacade facade, MenuConsole menu) {
        Collection<Pedido> pedidos = facade.listarPedidos();
        menu.exibirListaPedidos(pedidos);
    }

    private static void modificarPedido(RestauranteFacade facade, MenuConsole menu) {
        int numero = menu.lerNumeroPedidoParaBusca();
        Pedido pedido = facade.buscarPedido(numero);

        if (pedido == null) {
            menu.exibirErro("Pedido #" + numero + " não foi encontrado.");
            return;
        }

        menu.exibirPedido(pedido);
        menu.exibirSubmenuModificacao();
        int subOpcao = menu.lerOpcao();

        switch (subOpcao) {
            case 1 -> {
                // Acrescentar um item
                int opcaoLanche = menu.lerOpcaoLanche();
                Lanche lanche = facade.criarLancheBase(opcaoLanche);
                menu.personalizarLanche(lanche);
                ItemCardapio itemFinal = menu.configurarAdicionais(lanche);

                facade.adicionarItemAoPedido(pedido.getNumero(), itemFinal);
                menu.exibirSucesso("Item adicionado com sucesso!");
                menu.exibirPedido(pedido);
            }
            case 2 -> {
                // Remover um item existente
                if (pedido.getItens().isEmpty()) {
                    menu.exibirErro("O pedido não possui itens para remover.");
                    return;
                }
                menu.exibirItensNumerados(pedido);
                int indice = menu.lerIndiceItemRemover(pedido.getItens().size());
                boolean removido = facade.removerItemDoPedido(pedido.getNumero(), indice);
                if (removido) {
                    menu.exibirSucesso("Item removido com sucesso!");
                } else {
                    menu.exibirErro("Não foi possível remover o item.");
                }
                menu.exibirPedido(pedido);
            }
            case 3 -> {
                // Cancelar o pedido inteiro
                boolean cancelado = facade.cancelarPedido(numero);
                if (cancelado) {
                    menu.exibirSucesso("Pedido #" + numero + " cancelado com sucesso e removido do fechamento.");
                } else {
                    menu.exibirErro("Falha ao cancelar o pedido #" + numero);
                }
            }
            case 0 -> menu.exibirMensagem("Operação cancelada. Retornando ao menu principal.");
            default -> menu.exibirErro("Opção inválida de modificação.");
        }
    }

    private static void fecharRestaurante(RestauranteFacade facade, MenuConsole menu) {
        double total = facade.calcularTotalDoDia();
        Set<String> combinacoes = facade.obterCombinacoesDiferentes();
        menu.exibirFechamentoDoDia(total, combinacoes);
    }
}
