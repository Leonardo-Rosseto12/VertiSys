package br.com.vertisys.service;

import br.com.vertisys.model.Sensor;
import br.com.vertisys.repository.LoteRepository;
import br.com.vertisys.repository.SensorRepository;
import java.sql.SQLException;
import java.util.List;

public class SensorService {

    private final SensorRepository sensorRepository = new SensorRepository();
    private final LoteRepository loteRepository = new LoteRepository();

    public int cadastrar(Sensor sensor) throws SQLException {
        validarSensor(sensor);
        return sensorRepository.cadastrar(sensor);
    }

    public Sensor buscarPorId(int id) throws SQLException {
        if (id <= 0)
            throw new IllegalArgumentException("ID do sensor inválido.");

        return sensorRepository.buscarPorId(id);
    }

    public List<Sensor> listar() throws SQLException {
        return sensorRepository.listar();
    }

    public boolean atualizar(Sensor sensor) throws SQLException {
        validarSensor(sensor);

        if (sensor.getId() <= 0)
            throw new IllegalArgumentException("ID do sensor inválido.");

        return sensorRepository.atualizar(sensor);
    }

    public boolean excluir(int id) throws SQLException {
        if (id <= 0)
            throw new IllegalArgumentException("ID do sensor inválido.");

        return sensorRepository.excluir(id);
    }

    private void validarSensor(Sensor sensor) throws SQLException {
        if (sensor == null)
            throw new IllegalArgumentException("Sensor não informado.");

        if (sensor.getNome() == null || sensor.getNome().isBlank())
            throw new IllegalArgumentException("Nome do sensor não informado.");

        if (sensor.getLocalizacao() == null || sensor.getLocalizacao().isBlank())
            throw new IllegalArgumentException("Localização do sensor não informada.");

        if (sensor.getTipo() == null ||
                (!sensor.getTipo().equals("HIDROPONICO") && !sensor.getTipo().equals("AMBIENTAL")))
            throw new IllegalArgumentException("Tipo de sensor inválido.");

        if (sensor.getIdLote() <= 0 || loteRepository.buscarPorId(sensor.getIdLote()) == null)
            throw new IllegalArgumentException("Lote não encontrado.");
    }
}