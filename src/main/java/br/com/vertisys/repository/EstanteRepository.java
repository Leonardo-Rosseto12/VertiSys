package br.com.vertisys.repository;

import br.com.vertisys.model.Estante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstanteRepository {

    public int cadastrar(Estante estante) throws SQLException {
        String sql = "INSERT INTO estante (localizacao, status) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, estante.getLocalizacao());
            stmt.setString(2, estante.getStatus());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }

        return 0;
    }

    public Estante buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM estante WHERE id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Estante estante = new Estante(
                            rs.getString("localizacao"),
                            rs.getString("status")
                    );

                    estante.setId(rs.getInt("id"));

                    return estante;
                }
            }
        }

        return null;
    }

    public List<Estante> listar() throws SQLException {
        String sql = "SELECT * FROM estante";
        List<Estante> estantes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estante estante = new Estante(
                        rs.getString("localizacao"),
                        rs.getString("status")
                );

                estante.setId(rs.getInt("id"));

                estantes.add(estante);
            }
        }

        return estantes;
    }

    public boolean atualizar(Estante estante) throws SQLException {
        String sql = "UPDATE estante SET localizacao = ?, status = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estante.getLocalizacao());
            stmt.setString(2, estante.getStatus());
            stmt.setInt(3, estante.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM estante WHERE id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }
}