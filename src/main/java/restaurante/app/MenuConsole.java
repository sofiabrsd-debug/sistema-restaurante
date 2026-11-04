package restaurante.app;

import restaurante.cardapio.ComBaconExtra;
import restaurante.cardapio.ComCheddar;
import restaurante.cardapio.ItemCardapio;
import restaurante.cardapio.Lanche;
import restaurante.pedido.Pedido;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

/**
 * Responsável exclusivamente pela interação com o usuário via console:
 * exibições, leituras, validação de entradas numéricas e tratamento de erros de digitação.
 */
public class MenuConsole {
    private final Scanner scanner;

    public MenuConsole() {
        this.scanner = new Scanner(System.in);
    }

    public MenuConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibirMenuPrincipal() {
        System.out.println("\n=== RESTAURANTE ===");
        System.out.println("1 - Registrar novo pedido");
        System.out.println("2 - Listar pedidos do dia");
        System.out.println("3 - Modificar pedido");
        System.out.println("4 - Fechar restaurante");
        System.out.println("0 - Sair");
    }

    public int lerOpcao() {
        return lerInteiro("Escolha uma opção: ");
    }

    public int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = scanner.nextInt();
                scanner.nextLine(); // consome a quebra de linha pendente
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
                scanner.nextLine(); // limpa a entrada incorreta do buffer
            }
        }
    }

    public String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            if (entrada != null && !entrada.trim().isEmpty()) {
                return entrada.trim();
            }
            System.out.println("Entrada inválida! O texto não pode ficar vazio.");
        }
    }

    public boolean lerSimNao(String mensagem) {
        while (true) {
            System.out.print(mensagem + " (S/N): ");
            String entrada = scanner.nextLine().trim().toUpperCase();
            if (entrada.equals("S") || entrada.equals("SIM")) {
                return true;
            }
            if (entrada.equals("N") || entrada.equals("NAO") || entrada.equals("NÃO")) {
                return false;
            }
            System.out.println("Opção inválida! Digite 'S' para Sim ou 'N' para Não.");
        }
    }

    public String lerNomeCliente() {
        return lerTexto("Informe o nome do cliente: ");
    }

    public int lerOpcaoLanche() {
        System.out.println("\n--- CARDÁPIO ---");
        System.out.println("1 - X-Burger (R$ 16,00)");
        System.out.println("2 - X-Salada (R$ 18,00)");
        System.out.println("3 - X-Bacon  (R$ 22,00)");
        while (true) {
            int opcao = lerInteiro("Escolha o lanche desejado (1 a 3): ");
            if (opcao >= 1 && opcao <= 3) {
                return opcao;
            }
            System.out.println("Opção inválida! Escolha 1 para X-Burger, 2 para X-Salada ou 3 para X-Bacon.");
        }
    }

    public void personalizarLanche(Lanche lanche) {
        System.out.println("\n--- Personalização do Lanche ---");
        System.out.println("Deseja retirar algum ingrediente do " + lanche.getNome() + "?");

        if (lerSimNao("Retirar queijo?")) {
            lanche.setQueijo(false);
        }
        if (lerSimNao("Retirar alface?")) {
            lanche.setAlface(false);
        }
        if (lerSimNao("Retirar tomate?")) {
            lanche.setTomate(false);
        }
        if (lerSimNao("Retirar cebola?")) {
            lanche.setCebola(false);
        }
    }

    public ItemCardapio configurarAdicionais(ItemCardapio item) {
        ItemCardapio itemAtual = item;
        boolean adicionando = true;

        while (adicionando) {
            System.out.println("\n--- Adicionais disponíveis ---");
            System.out.printf("Item até agora: %s (R$ %.2f)%n", itemAtual.getDescricao(), itemAtual.getPreco());
            System.out.println("1 - Adicionar Cheddar (+ R$ 4,00)");
            System.out.println("2 - Adicionar Bacon Extra (+ R$ 6,00)");
            System.out.println("0 - Não adicionar mais adicionais");

            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> {
                    itemAtual = new ComCheddar(itemAtual);
                    System.out.println("Cheddar adicionado!");
                }
                case 2 -> {
                    itemAtual = new ComBaconExtra(itemAtual);
                    System.out.println("Bacon Extra adicionado!");
                }
                case 0 -> adicionando = false;
                default -> System.out.println("Opção inválida de adicional!");
            }
        }

        return itemAtual;
    }

    public boolean perguntarAdicionarMaisUmItem() {
        return lerSimNao("Deseja adicionar mais um item ao pedido?");
    }

    public int lerNumeroPedidoParaBusca() {
        return lerInteiro("Digite o número do pedido: ");
    }

    public void exibirSubmenuModificacao() {
        System.out.println("\n--- MODIFICAR PEDIDO ---");
        System.out.println("1 - Acrescentar um item");
        System.out.println("2 - Remover um item existente");
        System.out.println("3 - Cancelar o pedido inteiro");
        System.out.println("0 - Voltar");
    }

    public int lerIndiceItemRemover(int maxItens) {
        while (true) {
            int escolha = lerInteiro("Digite o número do item a remover (1 a " + maxItens + "): ");
            if (escolha >= 1 && escolha <= maxItens) {
                return escolha - 1; // converte para índice 0-based
            }
            System.out.println("Número de item inválido. Tente novamente.");
        }
    }

    public void exibirItensNumerados(Pedido pedido) {
        System.out.printf("\nItens do Pedido #%d (%s):%n", pedido.getNumero(), pedido.getNomeCliente());
        for (int i = 0; i < pedido.getItens().size(); i++) {
            ItemCardapio item = pedido.getItens().get(i);
            System.out.printf("[%d] %s - R$ %.2f%n", (i + 1), item.getDescricao(), item.getPreco());
        }
    }

    public void exibirPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("Pedido inexistente.");
            return;
        }
        System.out.println("\n========================================");
        System.out.printf("  Comanda #%d - Cliente: %s%n", pedido.getNumero(), pedido.getNomeCliente());
        System.out.println("----------------------------------------");
        if (pedido.getItens().isEmpty()) {
            System.out.println("  (Nenhum item cadastrado)");
        } else {
            for (int i = 0; i < pedido.getItens().size(); i++) {
                ItemCardapio item = pedido.getItens().get(i);
                System.out.printf("  %d. %s - R$ %.2f%n", (i + 1), item.getDescricao(), item.getPreco());
            }
        }
        System.out.println("----------------------------------------");
        System.out.printf("  TOTAL DO PEDIDO: R$ %.2f%n", pedido.calcularTotal());
        System.out.println("========================================");
    }

    public void exibirListaPedidos(Collection<Pedido> pedidos) {
        System.out.println("\n=== PEDIDOS REGISTRADOS NO DIA ===");
        if (pedidos == null || pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado hoje.");
            return;
        }
        for (Pedido p : pedidos) {
            exibirPedido(p);
        }
    }

    public void exibirFechamentoDoDia(double total) {
        System.out.println("\n========================================");
        System.out.println("         FECHAMENTO DO DIA");
        System.out.println("========================================");
        System.out.printf("Faturamento Total do Dia: R$ %.2f%n", total);
        System.out.println("========================================");
    }

    public void exibirFechamentoDoDia(double total, Set<String> combinacoes) {
        System.out.println("\n========================================");
        System.out.println("         FECHAMENTO DO DIA");
        System.out.println("========================================");
        System.out.printf("Faturamento Total do Dia: R$ %.2f%n", total);
        System.out.printf("Quantidade de combinações diferentes: %d%n", combinacoes.size());
        System.out.println("----------------------------------------");
        if (combinacoes.isEmpty()) {
            System.out.println("Nenhum lanche foi vendido hoje.");
        } else {
            System.out.println("Combinações vendidas:");
            for (String desc : combinacoes) {
                System.out.println(" - " + desc);
            }
        }
        System.out.println("========================================");
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirSucesso(String mensagem) {
        System.out.println("[SUCESSO] " + mensagem);
    }

    public void exibirErro(String mensagem) {
        System.out.println("[ERRO] " + mensagem);
    }
}
