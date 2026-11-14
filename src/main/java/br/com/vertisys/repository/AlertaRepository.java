package br.com.vertisys.repository;

import br.com.vertisys.model.Alerta;
import br.com.vertisys.model.Leitura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AlertaRepository {

    public int cadastrar(Alerta alerta) throws SQLException {
        String sql = """
                INSERT INTO alerta (criticidade, parametro, descricao, data_hora, id_leitura)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, alerta.getCriticidade());
            stmt.setString(2, alerta.getParametro());
            stmt.setString(3, alerta.getDescricao());
            stmt.setTimestamp(4, Timestamp.valueOf(alerta.getDataHora()));
            stmt.setInt(5, alerta.getLeitura().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    alerta.setId(rs.getInt(1));
                    return alerta.getId();
                }
            }
        }

        return 0;
    }

    public Alerta buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM alerta WHERE id = ?";

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return montarAlerta(rs);
            }
        }

        return null;
    }

    public List<Alerta> listar() throws SQLException {
        List<Alerta> alertas = new ArrayList<>();
        String sql = "SELECT * FROM alerta ORDER BY data_hora DESC";

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next())
                alertas.add(montarAlerta(rs));
        }

        return alertas;
    }

    private Alerta montarAlerta(ResultSet rs) throws SQLException {
        LeituraRepository leituraRepository = new LeituraRepository();
        Leitura leitura = leituraRepository.buscarPorId(rs.getInt("id_leitura"));

        Alerta alerta = new Alerta(
                rs.getString("criticidade"),
                rs.getString("parametro"),
                rs.getString("descricao"),
                rs.getTimestamp("data_hora").toLocalDateTime(),
                leitura
        );

        alerta.setId(rs.getInt("id"));

        return alerta;
    }
}