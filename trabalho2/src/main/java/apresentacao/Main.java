package apresentacao;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import negocio.Anotacao;
import persistencia.AnotacaoDAO;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/keeptabajara", "postgres", "postgres");
             Scanner scanner = new Scanner(System.in)) {

            AnotacaoDAO anotacaoDAO = new AnotacaoDAO(connection);

            System.out.println("Bem-vindo ao Google Keep Tabajara!");
            System.out.print("Digite seu nome de usuário ou 0 para sair: ");
            String nome = scanner.nextLine();

            while (!nome.equals("0")) {
                System.out.print("Digite sua senha: ");
                String senha = scanner.nextLine();

                int autorId = validarLogin(connection, nome, senha);
                if (autorId == -1) {
                    System.out.println("Login inválido. Tente novamente.");
                } else {
                    System.out.println("Login realizado com sucesso!");
                    int opcao;
                    do {
                        System.out.println("""
                            \nMenu:
                            1. Cadastrar
                            2. Excluir
                            3. Alterar
                            4. Copiar
                            5. Listar
                            0. Sair
                            Escolha uma opção:
                            """);
                        opcao = scanner.nextInt();
                        scanner.nextLine(); // Consumir newline

                        switch (opcao) {
                            case 1 -> cadastrarAnotacao(scanner, anotacaoDAO, autorId);
                            case 2 -> excluirAnotacao(scanner, anotacaoDAO, autorId);
                            case 3 -> alterarAnotacao(scanner, anotacaoDAO, autorId);
                            case 4 -> copiarAnotacao(scanner, anotacaoDAO, autorId);
                            case 5 -> listarAnotacoesComAutor(scanner, anotacaoDAO);
                            case 0 -> System.out.println("Saindo...");
                            default -> System.out.println("Opção inválida.");
                        }
                    } while (opcao != 0);

                }

                System.out.print("Digite seu nome de usuário ou 0 para sair: ");
                nome = scanner.nextLine();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int validarLogin(Connection connection, String nome, String senha) throws SQLException {
        String sql = "SELECT id FROM autores WHERE nome = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    private static void cadastrarAnotacao(Scanner scanner, AnotacaoDAO anotacaoDAO, int autorId) throws SQLException {
        System.out.print("Digite o título: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite a descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite a cor: ");
        String cor = scanner.nextLine();
        System.out.print("Digite o caminho da foto: ");
        String caminhoFoto = scanner.nextLine();

        byte[] foto = null;
        try {
            foto = Files.readAllBytes(Paths.get(caminhoFoto));
        } catch (IOException e) {
            System.out.println("Erro ao ler a foto.");
        }

        Anotacao anotacao = new Anotacao();
        anotacao.setTitulo(titulo);
        anotacao.setDataHora(LocalDateTime.now());
        anotacao.setDescricao(descricao);
        anotacao.setCor(cor);
        anotacao.setFoto(foto);
        anotacao.setAutorId(autorId);

        anotacaoDAO.cadastrarAnotacao(anotacao);
        System.out.println("Anotação cadastrada com sucesso!");
    }

    private static void excluirAnotacao(Scanner scanner, AnotacaoDAO anotacaoDAO, int autorId) throws SQLException {
        // Pergunta se o usuário deseja listar suas anotações
        System.out.print("Deseja listar suas anotações? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            // Lista apenas as anotações do autor
            List<Anotacao> anotacoes = anotacaoDAO.listarAnotacoesPorAutor(autorId);
            if (anotacoes.isEmpty()) {
                System.out.println("Você não possui anotações cadastradas.");
                return;
            }
            System.out.println("\nSuas Anotações:");
            for (Anotacao anotacao : anotacoes) {
                System.out.printf("- ID: %d | Título: %s%n", anotacao.getId(), anotacao.getTitulo());
            }
        }
    
        // Solicita o ID da anotação a ser excluída
        System.out.print("Digite o ID da anotação a ser excluída ou 0 para cancelar: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (id == 0) {
            System.out.println("Operação cancelada.");
            return;
        }
    
        // Chama o DAO para verificar e excluir a anotação
        boolean excluido = anotacaoDAO.excluirAnotacaoPorId(autorId, id);
        if (excluido) {
            System.out.println("Anotação excluída com sucesso!");
        } else {
            System.out.println("Erro: Anotação não encontrada ou não pertence a você.");
        }
    }

    private static void alterarAnotacao(Scanner scanner, AnotacaoDAO anotacaoDAO, int autorId) throws SQLException {
        System.out.print("Deseja listar suas anotações? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            for (Anotacao anotacao : anotacaoDAO.listarAnotacoesPorAutor(autorId)) {
                System.out.println("- " + anotacao.getTitulo());
            }
        }
    
        System.out.print("Digite o título da anotação a ser alterada ou 0 para cancelar: ");
        String titulo = scanner.nextLine();
        if (!titulo.equals("0")) {
            Anotacao anotacao = anotacaoDAO.buscarAnotacaoPorTitulo(autorId, titulo);
            if (anotacao == null) {
                System.out.println("Anotação não encontrada ou pertence a outro autor.");
                return;
            }
    
            System.out.print("Digite o novo título (ou 0 para manter): ");
            String novoTitulo = scanner.nextLine();
            if (!novoTitulo.equals("0")) {
                anotacao.setTitulo(novoTitulo);
            }
    
            System.out.print("Digite a nova descrição (ou 0 para manter): ");
            String novaDescricao = scanner.nextLine();
            if (!novaDescricao.equals("0")) {
                anotacao.setDescricao(novaDescricao);
            }
    
            System.out.print("Digite a nova cor (ou 0 para manter): ");
            String novaCor = scanner.nextLine();
            if (!novaCor.equals("0")) {
                anotacao.setCor(novaCor);
            }
    
            System.out.print("Digite o caminho da nova foto (ou 0 para manter): ");
            String caminhoNovaFoto = scanner.nextLine();
            if (!caminhoNovaFoto.equals("0")) {
                try {
                    byte[] novaFoto = Files.readAllBytes(Paths.get(caminhoNovaFoto));
                    anotacao.setFoto(novaFoto);
                } catch (IOException e) {
                    System.out.println("Erro ao ler a nova foto. Mantendo a original.");
                }
            }
    
            anotacao.setDataHora(LocalDateTime.now()); // Atualizar a data/hora para o momento da alteração
            anotacaoDAO.alterarAnotacao(anotacao);
            System.out.println("Anotação alterada com sucesso!");
        }
    }


    private static void copiarAnotacao(Scanner scanner, AnotacaoDAO anotacaoDAO, int autorId) throws SQLException {
        // Solicita o ID da anotação a ser copiada
        System.out.print("Deseja listar suas anotações ou todas as anotações? (s = suas / t = todas / n = nenhuma): ");
        String opcao = scanner.nextLine().toLowerCase();
    
        List<Anotacao> anotacoes;
        if (opcao.equals("s")) {
            anotacoes = anotacaoDAO.listarAnotacoesPorAutor(autorId);
        } else if (opcao.equals("t")) {
            anotacoes = anotacaoDAO.listarTodasAnotacoesComAutor();
        } else {
            System.out.println("Operação cancelada.");
            return;
        }
    
        if (anotacoes.isEmpty()) {
            System.out.println("Nenhuma anotação encontrada.");
            return;
        }
    
        // Lista as anotações disponíveis
        System.out.println("\nAnotações Disponíveis:");
        for (Anotacao anotacao : anotacoes) {
            System.out.printf("- ID: %d | Título: %s | Autor: %s%n", 
                              anotacao.getId(), anotacao.getTitulo(), anotacao.getAutorNome());
        }
    
        // Solicita o ID da anotação original e o novo título
        System.out.print("Digite o ID da anotação que deseja copiar ou 0 para cancelar: ");
        int idOriginal = Integer.parseInt(scanner.nextLine());
        if (idOriginal == 0) {
            System.out.println("Operação cancelada.");
            return;
        }
    
        System.out.print("Digite o novo título para a cópia: ");
        String novoTitulo = scanner.nextLine();
    
        // Chama o DAO para copiar a anotação
        boolean copiada = anotacaoDAO.copiarAnotacaoPorId(autorId, idOriginal, novoTitulo);
        if (copiada) {
            System.out.println("Anotação copiada com sucesso!");
        } else {
            System.out.println("Erro: Anotação original não encontrada.");
        }
    }


private static void listarAnotacoesComAutor(Scanner scanner, AnotacaoDAO anotacaoDAO) throws SQLException {
    // Obtém todas as anotações com autor
    List<Anotacao> anotacoes = anotacaoDAO.listarTodasAnotacoesComAutor();

    if (anotacoes.isEmpty()) {
        System.out.println("Não há anotações cadastradas.");
        return;
    }

    // Pergunta ao usuário o critério de ordenação
    System.out.print("\nComo deseja ordenar as anotações? (1: ID, 2: Autor, 3: Data/Hora): ");
    int criterioOrdenacao = Integer.parseInt(scanner.nextLine());

    // Ordena a lista com base na escolha
    switch (criterioOrdenacao) {
        case 1 -> anotacoes.sort(Comparator.comparingInt(Anotacao::getId)); // Ordena por ID
        case 2 -> anotacoes.sort(Comparator.comparing(Anotacao::getAutorNome, String.CASE_INSENSITIVE_ORDER)); // Ordena por Autor
        case 3 -> anotacoes.sort(Comparator.comparing(Anotacao::getDataHora)); // Ordena por Data/Hora
        default -> System.out.println("Opção inválida. Exibindo na ordem padrão."); // Ordem original
    }

    // Exibe a lista de anotações com ID
    System.out.println("\nLista de Anotações:");
    for (Anotacao anotacao : anotacoes) {
        String statusFoto = (anotacao.getFoto() != null) ? "Com Foto" : "Sem Foto";
        System.out.printf("ID: %d | Data/Hora: %s | Título: %s | Descrição: %s | Cor: %s | Autor: %s | Foto: %s%n",
                anotacao.getId(), anotacao.getDataHora(), anotacao.getTitulo(),
                anotacao.getDescricao(), anotacao.getCor(),
                anotacao.getAutorNome(), statusFoto);
    }

    // Pergunta ao usuário se deseja exibir uma foto
    System.out.print("\nDeseja exibir uma foto? (Digite o ID ou 0 para voltar ao menu): ");
    int escolhaId = Integer.parseInt(scanner.nextLine()); // Entrada como ID

    if (escolhaId != 0) {
        // Localiza a anotação pelo ID informado
        Anotacao anotacao = anotacoes.stream()
                .filter(a -> a.getId() == escolhaId)
                .findFirst()
                .orElse(null);

        // Verifica se a anotação existe e tem foto
        if (anotacao != null && anotacao.getFoto() != null) {
            exibirFoto(anotacao.getFoto());
        } else if (anotacao == null) {
            System.out.println("Anotação não encontrada.");
        } else {
            System.out.println("Essa anotação não possui foto.");
        }
    }
}

private static void exibirFoto(byte[] foto) {
    // Simula exibição da foto (salvando em arquivo ou processando em UI real)
    System.out.println("Exibindo foto...");
    try {
        Files.write(Paths.get("foto_exibida.jpg"), foto);
        System.out.println("Foto salva como 'foto_exibida.jpg'. Abra o arquivo para visualizar.");
    } catch (IOException e) {
        System.out.println("Erro ao salvar a foto: " + e.getMessage());
    }
}


}