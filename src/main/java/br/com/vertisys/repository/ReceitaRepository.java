package br.com.vertisys.repository;

import br.com.vertisys.model.Receita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaRepository {

    public int cadastrar(Receita receita) throws SQLException {
        String sql = "INSERT INTO receita (densidade_semeadura, ph_medio, ec_medio, ciclo_dias, temp_ar_alvo, fotoperiodo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, receita.getDensidadeSemeadura());
            stmt.setDouble(2, receita.getPhMedio());
            stmt.setDouble(3, receita.getEcMedio());
            stmt.setInt(4, receita.getCicloDias());
            stmt.setDouble(5, receita.getTempArAlvo());
            stmt.setDouble(6, receita.getFotoperiodo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    receita.setIdReceita(id);
                    return id;
                }
            }
        }

        return 0;
    }

    public Receita buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM receita WHERE id_receita = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Receita receita = new Receita(
                            rs.getDouble("densidade_semeadura"),
                            rs.getDouble("ph_medio"),
                            rs.getDouble("ec_medio"),
                            rs.getInt("ciclo_dias"),
                            rs.getDouble("temp_ar_alvo"),
                            rs.getDouble("fotoperiodo")
                    );

                    receita.setIdReceita(rs.getInt("id_receita"));

                    return receita;
                }
            }
        }

        return null;
    }

    public List<Receita> listar() throws SQLException {
        String sql = "SELECT * FROM receita";
        List<Receita> receitas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Receita receita = new Receita(
                        rs.getDouble("densidade_semeadura"),
                        rs.getDouble("ph_medio"),
                        rs.getDouble("ec_medio"),
                        rs.getInt("ciclo_dias"),
                        rs.getDouble("temp_ar_alvo"),
                        rs.getDouble("fotoperiodo")
                );

                receita.setIdReceita(rs.getInt("id_receita"));

                receitas.add(receita);
            }
        }

        return receitas;
    }

    public boolean atualizar(Receita receita) throws SQLException {
        String sql = "UPDATE receita SET densidade_semeadura = ?, ph_medio = ?, ec_medio = ?, ciclo_dias = ?, temp_ar_alvo = ?, fotoperiodo = ? WHERE id_receita = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, receita.getDensidadeSemeadura());
            stmt.setDouble(2, receita.getPhMedio());
            stmt.setDouble(3, receita.getEcMedio());
            stmt.setInt(4, receita.getCicloDias());
            stmt.setDouble(5, receita.getTempArAlvo());
            stmt.setDouble(6, receita.getFotoperiodo());
            stmt.setInt(7, receita.getIdReceita());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM receita WHERE id_receita = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }
}