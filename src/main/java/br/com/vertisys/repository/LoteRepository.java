package br.com.vertisys.repository;

import br.com.vertisys.model.Cultura;
import br.com.vertisys.model.Estante;
import br.com.vertisys.model.Lote;
import br.com.vertisys.model.Receita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteRepository {

    public int cadastrar(Lote lote) throws SQLException {
        String sql = """
                INSERT INTO lote (
                    data_semeadura,
                    status,
                    data_colheita_estimada,
                    qtd_bandejas,
                    area_ocupada,
                    id_cultura,
                    id_estante
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(lote.getDataSemeadura()));
            stmt.setString(2, lote.getStatus());
            stmt.setDate(3, Date.valueOf(lote.getDataColheitaEstimada()));
            stmt.setInt(4, lote.getQtdBandejas());
            stmt.setDouble(5, lote.getAreaOcupada());
            stmt.setInt(6, lote.getCultura().getIdCultura());
            stmt.setInt(7, lote.getEstante().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }

        return 0;
    }

    public Lote buscarPorId(int id) throws SQLException {
        String sql = """
                SELECT
                    l.id AS lote_id,
                    l.data_semeadura,
                    l.status AS lote_status,
                    l.data_colheita_estimada,
                    l.qtd_bandejas,
                    l.area_ocupada,

                    c.id_cultura,
                    c.nome_popular,
                    c.nome_cientifico,

                    r.id_receita,
                    r.densidade_semeadura,
                    r.ph_medio,
                    r.ec_medio,
                    r.ciclo_dias,
                    r.temp_ar_alvo,
                    r.fotoperiodo,

                    e.id AS estante_id,
                    e.localizacao,
                    e.status AS estante_status

                FROM lote l
                JOIN cultura c ON l.id_cultura = c.id_cultura
                JOIN receita r ON c.id_receita = r.id_receita
                JOIN estante e ON l.id_estante = e.id

                WHERE l.id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montarLote(rs);
            }
        }

        return null;
    }

    public List<Lote> listar() throws SQLException {
        String sql = """
                SELECT
                    l.id AS lote_id,
                    l.data_semeadura,
                    l.status AS lote_status,
                    l.data_colheita_estimada,
                    l.qtd_bandejas,
                    l.area_ocupada,

                    c.id_cultura,
                    c.nome_popular,
                    c.nome_cientifico,

                    r.id_receita,
                    r.densidade_semeadura,
                    r.ph_medio,
                    r.ec_medio,
                    r.ciclo_dias,
                    r.temp_ar_alvo,
                    r.fotoperiodo,

                    e.id AS estante_id,
                    e.localizacao,
                    e.status AS estante_status

                FROM lote l
                JOIN cultura c ON l.id_cultura = c.id_cultura
                JOIN receita r ON c.id_receita = r.id_receita
                JOIN estante e ON l.id_estante = e.id
                """;

        List<Lote> lotes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next())
                lotes.add(montarLote(rs));
        }

        return lotes;
    }

    public boolean atualizar(Lote lote) throws SQLException {
        String sql = """
                UPDATE lote
                SET data_semeadura = ?,
                    status = ?,
                    data_colheita_estimada = ?,
                    qtd_bandejas = ?,
                    area_ocupada = ?,
                    id_cultura = ?,
                    id_estante = ?
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(lote.getDataSemeadura()));
            stmt.setString(2, lote.getStatus());
            stmt.setDate(3, Date.valueOf(lote.getDataColheitaEstimada()));
            stmt.setInt(4, lote.getQtdBandejas());
            stmt.setDouble(5, lote.getAreaOcupada());
            stmt.setInt(6, lote.getCultura().getIdCultura());
            stmt.setInt(7, lote.getEstante().getId());
            stmt.setInt(8, lote.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean cancelar(int id) throws SQLException {
        String sql = "UPDATE lote SET status = 'CANCELADO' WHERE id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

    private Lote montarLote(ResultSet rs) throws SQLException {
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

        Estante estante = new Estante(
                rs.getString("localizacao"),
                rs.getString("estante_status")
        );

        estante.setId(rs.getInt("estante_id"));

        Lote lote = new Lote(
                rs.getDate("data_semeadura").toLocalDate(),
                rs.getString("lote_status"),
                rs.getDate("data_colheita_estimada").toLocalDate(),
                rs.getInt("qtd_bandejas"),
                rs.getDouble("area_ocupada"),
                cultura,
                estante
        );

        lote.setId(rs.getInt("lote_id"));

        return lote;
    }
}