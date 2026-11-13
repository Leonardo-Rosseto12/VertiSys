package br.com.vertisys.repository;

import br.com.vertisys.model.Sensor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SensorRepository {

    public int cadastrar(Sensor sensor) throws SQLException {
        String sql = "INSERT INTO sensor (nome, localizacao, tipo, id_lote) VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sensor.getNome());
            stmt.setString(2, sensor.getLocalizacao());
            stmt.setString(3, sensor.getTipo());
            stmt.setInt(4, sensor.getIdLote());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sensor.setId(rs.getInt(1));
                    return sensor.getId();
                }
            }
        }

        return 0;
    }

    public Sensor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM sensor WHERE id = ?";

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return montarSensor(rs);
            }
        }

        return null;
    }

    public List<Sensor> listar() throws SQLException {
        List<Sensor> sensores = new ArrayList<>();
        String sql = "SELECT * FROM sensor";

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next())
                sensores.add(montarSensor(rs));
        }

        return sensores;
    }

    public boolean atualizar(Sensor sensor) throws SQLException {
        String sql = "UPDATE sensor SET nome = ?, localizacao = ?, tipo = ?, id_lote = ? WHERE id = ?";

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, sensor.getNome());
            stmt.setString(2, sensor.getLocalizacao());
            stmt.setString(3, sensor.getTipo());
            stmt.setInt(4, sensor.getIdLote());
            stmt.setInt(5, sensor.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM sensor WHERE id = ?";

        try (Connection conexao = ConnectionFactory.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

    private Sensor montarSensor(ResultSet rs) throws SQLException {
        Sensor sensor = new Sensor(
                rs.getString("nome"),
                rs.getString("localizacao"),
                rs.getString("tipo"),
                rs.getInt("id_lote")
        );

        sensor.setId(rs.getInt("id"));

        return sensor;
    }
}