package br.com.vertisys.repository;

import br.com.vertisys.model.Colheita;
import br.com.vertisys.model.Lote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ColheitaRepository {

    private final LoteRepository loteRepository = new LoteRepository();

    public boolean cadastrar(Colheita colheita) throws SQLException {
        String sql = """
                INSERT INTO colheita (id_lote, data_colheita, peso_biomassa, peso_descarte)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, colheita.getLote().getId());
            stmt.setDate(2, java.sql.Date.valueOf(colheita.getData()));
            stmt.setDouble(3, colheita.getPesoBiomassa());
            stmt.setDouble(4, colheita.getPesoDescarte());

            return stmt.executeUpdate() > 0;
        }
    }

    public Colheita buscarPorLote(int idLote) throws SQLException {
        String sql = """
                SELECT *
                FROM colheita
                WHERE id_lote = ?
                """;

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idLote);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarColheita(rs);
                }
            }
        }

        return null;
    }

    public ArrayList<Colheita> listar() throws SQLException {
        ArrayList<Colheita> colheitas = new ArrayList<>();

        String sql = """
                SELECT *
                FROM colheita
                ORDER BY data_colheita DESC
                """;

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next())
                colheitas.add(montarColheita(rs));
        }

        return colheitas;
    }

    private Colheita montarColheita(ResultSet rs) throws SQLException {
        int idLote = rs.getInt("id_lote");
        Lote lote = loteRepository.buscarPorId(idLote);

        return new Colheita(
                rs.getDate("data_colheita").toLocalDate(),
                rs.getDouble("peso_biomassa"),
                rs.getDouble("peso_descarte"),
                lote
        );
    }
}