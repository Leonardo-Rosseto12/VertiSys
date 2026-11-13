package br.com.vertisys.repository;

import br.com.vertisys.model.Leitura;
import br.com.vertisys.model.LeituraAmbiental;
import br.com.vertisys.model.LeituraHidroponica;
import br.com.vertisys.model.Sensor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LeituraRepository {

    public int cadastrar(Leitura leitura) throws SQLException {
        String sqlLeitura = "INSERT INTO leitura (data_hora, id_sensor) VALUES (?, ?)";

        try (Connection conexao = ConnectionFactory.conectar()) {
            conexao.setAutoCommit(false);

            try {
                int idLeitura;

                try (PreparedStatement stmt = conexao.prepareStatement(sqlLeitura, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setTimestamp(1, Timestamp.valueOf(leitura.getDataHora()));
                    stmt.setInt(2, leitura.getSensor().getId());

                    stmt.executeUpdate();

                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (!rs.next())
                            throw new SQLException("Não foi possível gerar o ID da leitura.");

                        idLeitura = rs.getInt(1);
                    }
                }

                if (leitura instanceof LeituraHidroponica hidroponica) {
                    String sql = "INSERT INTO leitura_hidroponica (id_leitura, ph, ec) VALUES (?, ?, ?)";

                    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                        stmt.setInt(1, idLeitura);
                        stmt.setDouble(2, hidroponica.getPh());
                        stmt.setDouble(3, hidroponica.getEc());
                        stmt.executeUpdate();
                    }

                } else if (leitura instanceof LeituraAmbiental ambiental) {
                    String sql = "INSERT INTO leitura_ambiental (id_leitura, temp_ar, umidade, co2) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                        stmt.setInt(1, idLeitura);
                        stmt.setDouble(2, ambiental.getTempAr());
                        stmt.setDouble(3, ambiental.getUmidade());
                        stmt.setDouble(4, ambiental.getCo2());
                        stmt.executeUpdate();
                    }

                } else {
                    throw new SQLException("Tipo de leitura não reconhecido.");
                }

                conexao.commit();
                leitura.setId(idLeitura);

                return idLeitura;

            } catch (SQLException e) {
                conexao.rollback();
                throw e;
            }
        }
    }

    public Leitura buscarPorId(int id) throws SQLException {
        String sql = """
                SELECT
                    l.id AS id_leitura,
                    l.data_hora,
                    s.id AS id_sensor,
                    s.nome,
                    s.localizacao,
                    s.tipo,
                    s.id_lote,
                    lh.id_leitura AS id_hidroponica,
                    lh.ph,
                    lh.ec,
                    la.id_leitura AS id_ambiental,
                    la.temp_ar,
                    la.umidade,
                    la.co2
                FROM leitura l
                INNER JOIN sensor s ON l.id_sensor = s.id
                LEFT JOIN leitura_hidroponica lh ON l.id = lh.id_leitura
                LEFT JOIN leitura_ambiental la ON l.id = la.id_leitura
                WHERE l.id = ?
                """;

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return montarLeitura(rs);
            }
        }

        return null;
    }

    public List<Leitura> listar() throws SQLException {
        List<Leitura> leituras = new ArrayList<>();

        String sql = """
                SELECT
                    l.id AS id_leitura,
                    l.data_hora,
                    s.id AS id_sensor,
                    s.nome,
                    s.localizacao,
                    s.tipo,
                    s.id_lote,
                    lh.id_leitura AS id_hidroponica,
                    lh.ph,
                    lh.ec,
                    la.id_leitura AS id_ambiental,
                    la.temp_ar,
                    la.umidade,
                    la.co2
                FROM leitura l
                INNER JOIN sensor s ON l.id_sensor = s.id
                LEFT JOIN leitura_hidroponica lh ON l.id = lh.id_leitura
                LEFT JOIN leitura_ambiental la ON l.id = la.id_leitura
                ORDER BY l.data_hora DESC
                """;

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next())
                leituras.add(montarLeitura(rs));
        }

        return leituras;
    }

    private Leitura montarLeitura(ResultSet rs) throws SQLException {
        Sensor sensor = new Sensor(
                rs.getString("nome"),
                rs.getString("localizacao"),
                rs.getString("tipo"),
                rs.getInt("id_lote")
        );

        sensor.setId(rs.getInt("id_sensor"));

        Timestamp dataHora = rs.getTimestamp("data_hora");

        Leitura leitura;

        if (rs.getObject("id_hidroponica") != null) {
            leitura = new LeituraHidroponica(
                    dataHora.toLocalDateTime(),
                    sensor,
                    rs.getDouble("ph"),
                    rs.getDouble("ec")
            );

        } else if (rs.getObject("id_ambiental") != null) {
            leitura = new LeituraAmbiental(
                    dataHora.toLocalDateTime(),
                    sensor,
                    rs.getDouble("temp_ar"),
                    rs.getDouble("umidade"),
                    rs.getDouble("co2")
            );

        } else {
            throw new SQLException("Leitura sem especialização encontrada.");
        }

        leitura.setId(rs.getInt("id_leitura"));

        return leitura;
    }
}