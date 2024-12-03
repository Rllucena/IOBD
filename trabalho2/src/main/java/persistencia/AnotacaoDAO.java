package persistencia;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import negocio.Anotacao;

public class AnotacaoDAO {
    private Connection connection;

    public AnotacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarAnotacao(Anotacao anotacao) throws SQLException {
        String sql = "INSERT INTO anotacoes (titulo, data_hora, descricao, cor, foto, autor_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, anotacao.getTitulo());
            stmt.setTimestamp(2, Timestamp.valueOf(anotacao.getDataHora()));
            stmt.setString(3, anotacao.getDescricao());
            stmt.setString(4, anotacao.getCor());
            stmt.setBytes(5, anotacao.getFoto());
            stmt.setInt(6, anotacao.getAutorId());
            stmt.executeUpdate();
        }
    }

    public List<Anotacao> listarAnotacoesPorAutor(int autorId) throws SQLException {
        String sql = "SELECT * FROM anotacoes WHERE autor_id = ?";
        List<Anotacao> anotacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, autorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Anotacao anotacao = new Anotacao();
                    anotacao.setId(rs.getInt("id"));
                    anotacao.setTitulo(rs.getString("titulo"));
                    anotacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    anotacao.setDescricao(rs.getString("descricao"));
                    anotacao.setCor(rs.getString("cor"));
                    anotacao.setFoto(rs.getBytes("foto"));
                    anotacao.setAutorId(rs.getInt("autor_id"));
                    anotacoes.add(anotacao);
                }
            }
        }
        return anotacoes;
    }

    public void excluirAnotacao(int autorId, String titulo) throws SQLException {
        String sql = "DELETE FROM anotacoes WHERE autor_id = ? AND titulo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, autorId);
            stmt.setString(2, titulo);
            stmt.executeUpdate();
        }
    }

    public void alterarAnotacao(Anotacao anotacao) throws SQLException {
        String sql = "UPDATE anotacoes SET titulo = ?, data_hora = ?, descricao = ?, cor = ?, foto = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, anotacao.getTitulo());
            stmt.setTimestamp(2, Timestamp.valueOf(anotacao.getDataHora()));
            stmt.setString(3, anotacao.getDescricao());
            stmt.setString(4, anotacao.getCor());
            stmt.setBytes(5, anotacao.getFoto());
            stmt.setInt(6, anotacao.getId());
            stmt.executeUpdate();
        }
    }

    
    
    public List<Anotacao> listarTodasAnotacoesComAutor() throws SQLException {
        String sql = """
            SELECT a.id, a.titulo, a.data_hora, a.descricao, a.cor, a.foto, a.autor_id, au.nome AS autor_nome
            FROM anotacoes a
            JOIN autores au ON a.autor_id = au.id
            ORDER BY a.data_hora
        """;
    
        List<Anotacao> anotacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Anotacao anotacao = new Anotacao();
                anotacao.setId(rs.getInt("id"));
                anotacao.setTitulo(rs.getString("titulo"));
                anotacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                anotacao.setDescricao(rs.getString("descricao"));
                anotacao.setCor(rs.getString("cor"));
                anotacao.setFoto(rs.getBytes("foto"));
                anotacao.setAutorId(rs.getInt("autor_id"));
                anotacao.setAutorNome(rs.getString("autor_nome")); // Adiciona o nome do autor
    
                anotacoes.add(anotacao);
            }
        }
        return anotacoes;
    }
    
        
    public Anotacao buscarAnotacaoPorTitulo(int autorId, String titulo) throws SQLException {
        String sql = "SELECT * FROM anotacoes WHERE autor_id = ? AND titulo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, autorId);
            stmt.setString(2, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Anotacao anotacao = new Anotacao();
                    anotacao.setId(rs.getInt("id"));
                    anotacao.setTitulo(rs.getString("titulo"));
                    anotacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    anotacao.setDescricao(rs.getString("descricao"));
                    anotacao.setCor(rs.getString("cor"));
                    anotacao.setFoto(rs.getBytes("foto"));
                    anotacao.setAutorId(rs.getInt("autor_id"));
                    return anotacao;
                }
            }
        }
        return null; // Caso não encontre a anotação
    }




    
    public void copiarAnotacao(int autorId, String tituloOriginal, String novoTitulo) throws SQLException {
        String sqlBuscar = "SELECT * FROM anotacoes WHERE autor_id = ? AND titulo = ?";
        String sqlInserir = "INSERT INTO anotacoes (titulo, data_hora, descricao, cor, foto, autor_id) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (PreparedStatement stmtBuscar = connection.prepareStatement(sqlBuscar)) {
            stmtBuscar.setInt(1, autorId);
            stmtBuscar.setString(2, tituloOriginal);
            try (ResultSet rs = stmtBuscar.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement stmtInserir = connection.prepareStatement(sqlInserir)) {
                        stmtInserir.setString(1, novoTitulo);
                        stmtInserir.setTimestamp(2, Timestamp.valueOf(rs.getTimestamp("data_hora").toLocalDateTime()));
                        stmtInserir.setString(3, rs.getString("descricao"));
                        stmtInserir.setString(4, rs.getString("cor"));
                        stmtInserir.setBytes(5, rs.getBytes("foto"));
                        stmtInserir.setInt(6, autorId);
                        stmtInserir.executeUpdate();
                    }
                } else {
                    throw new SQLException("Anotação original não encontrada.");
                }
            }
        }
    }



    
}