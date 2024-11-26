package apresentacao;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
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
                        System.out.println("\n1. Cadastrar\n2. Excluir\n3. Alterar\n0. Sair");
                        System.out.print("Escolha uma opção: ");
                        opcao = scanner.nextInt();
                        scanner.nextLine(); // Consumir newline

                        switch (opcao) {
                            case 1 -> cadastrarAnotacao(scanner, anotacaoDAO, autorId);
                            case 2 -> excluirAnotacao(scanner, anotacaoDAO, autorId);
                            case 3 -> alterarAnotacao(scanner, anotacaoDAO, autorId);
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
        System.out.print("Deseja listar suas anotações? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            for (Anotacao anotacao : anotacaoDAO.listarAnotacoesPorAutor(autorId)) {
                System.out.println("- " + anotacao.getTitulo());
            }
        }

        System.out.print("Digite o título da anotação a ser excluída ou 0 para cancelar: ");
        String titulo = scanner.nextLine();
        if (!titulo.equals("0")) {
            anotacaoDAO.excluirAnotacao(autorId, titulo);
            System.out.println("Anotação excluída com sucesso!");
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
}