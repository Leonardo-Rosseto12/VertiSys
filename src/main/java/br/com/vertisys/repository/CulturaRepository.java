package br.com.vertisys.repository;

import br.com.vertisys.model.Cultura;
import br.com.vertisys.model.Receita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CulturaRepository {

    public int cadastrar(Cultura cultura) throws SQLException {
        String sql = "INSERT INTO cultura (nome_popular, nome_cientifico, id_receita) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cultura.getNomePopular());
            stmt.setString(2, cultura.getNomeCientifico());
            stmt.setInt(3, cultura.getReceita().getIdReceita());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    cultura.setIdCultura(id);
                    return id;
                }
            }
        }

        return 0;
    }

    public Cultura buscarPorId(int id) throws SQLException {
        String sql = "SELECT c.id_cultura, c.nome_popular, c.nome_cientifico, " +
                "r.id_receita, r.densidade_semeadura, r.ph_medio, r.ec_medio, " +
                "r.ciclo_dias, r.temp_ar_alvo, r.fotoperiodo " +
                "FROM cultura c INNER JOIN receita r ON c.id_receita = r.id_receita " +
                "WHERE c.id_cultura = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapearCultura(rs);
            }
        }

        return null;
    }

    public List<Cultura> listar() throws SQLException {
        String sql = "SELECT c.id_cultura, c.nome_popular, c.nome_cientifico, " +
                "r.id_receita, r.densidade_semeadura, r.ph_medio, r.ec_medio, " +
                "r.ciclo_dias, r.temp_ar_alvo, r.fotoperiodo " +
                "FROM cultura c INNER JOIN receita r ON c.id_receita = r.id_receita";

        List<Cultura> culturas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next())
                culturas.add(mapearCultura(rs));
        }

        return culturas;
    }

    public boolean atualizar(Cultura cultura) throws SQLException {
        String sql = "UPDATE cultura SET nome_popular = ?, nome_cientifico = ?, id_receita = ? WHERE id_cultura = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cultura.getNomePopular());
            stmt.setString(2, cultura.getNomeCientifico());
            stmt.setInt(3, cultura.getReceita().getIdReceita());
            stmt.setInt(4, cultura.getIdCultura());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM cultura WHERE id_cultura = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

    private Cultura mapearCultura(ResultSet rs) throws SQLException {
        Receita receita = new Receita(
                rs.getDouble("densidade_semeadura"),
                rs.getDouble("ph_medio"),
                rs.getDouble("ec_medio"),
                rs.getInt("ciclo_dias"),
                rs.getDouble("temp_ar_alvo"),
                rs.getDouble("fotoperiodo")
        );

        receita.setIdReceita(rs.getInt("id_receita"));

        Cultura cultura = new Cultura(
                rs.getString("nome_popular"),
                rs.getString("nome_cientifico"),
                receita
        );

        cultura.setIdCultura(rs.getInt("id_cultura"));

        return cultura;
    }
}