package persistencia;
import java.sql.*;
//import java.time.LocalDateTime;
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
    
}