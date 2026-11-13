package br.com.vertisys.service;

import br.com.vertisys.model.Leitura;
import br.com.vertisys.model.LeituraAmbiental;
import br.com.vertisys.model.LeituraHidroponica;
import br.com.vertisys.repository.LeituraRepository;
import br.com.vertisys.repository.SensorRepository;

import java.sql.SQLException;
import java.util.List;

public class LeituraService {

    private final LeituraRepository leituraRepository = new LeituraRepository();
    private final SensorRepository sensorRepository = new SensorRepository();

    public int cadastrar(Leitura leitura) throws SQLException {
        validarLeitura(leitura);
        return leituraRepository.cadastrar(leitura);
    }

    public Leitura buscarPorId(int id) throws SQLException {
        if (id <= 0)
            throw new IllegalArgumentException("ID da leitura inválido.");

        return leituraRepository.buscarPorId(id);
    }

    public List<Leitura> listar() throws SQLException {
        return leituraRepository.listar();
    }

    private void validarLeitura(Leitura leitura) throws SQLException {
        if (leitura == null)
            throw new IllegalArgumentException("Leitura não informada.");

        if (leitura.getDataHora() == null)
            throw new IllegalArgumentException("Data e hora da leitura não informadas.");

        if (leitura.getSensor() == null || leitura.getSensor().getId() <= 0)
            throw new IllegalArgumentException("Sensor não informado.");

        var sensor = sensorRepository.buscarPorId(leitura.getSensor().getId());

        if (sensor == null)
            throw new IllegalArgumentException("Sensor não encontrado.");

        if (leitura instanceof LeituraHidroponica hidroponica) {
            if (!sensor.getTipo().equals("HIDROPONICO"))
                throw new IllegalArgumentException("A leitura hidropônica exige um sensor hidropônico.");

            if (!Double.isFinite(hidroponica.getPh()) || hidroponica.getPh() < 0 || hidroponica.getPh() > 14)
                throw new IllegalArgumentException("Valor de pH inválido.");

            if (!Double.isFinite(hidroponica.getEc()) || hidroponica.getEc() < 0)
                throw new IllegalArgumentException("Valor de EC inválido.");

        } else if (leitura instanceof LeituraAmbiental ambiental) {
            if (!sensor.getTipo().equals("AMBIENTAL"))
                throw new IllegalArgumentException("A leitura ambiental exige um sensor ambiental.");

            if (!Double.isFinite(ambiental.getTempAr()) || ambiental.getTempAr() < -50 || ambiental.getTempAr() > 100)
                throw new IllegalArgumentException("Temperatura do ar inválida.");

            if (!Double.isFinite(ambiental.getUmidade()) || ambiental.getUmidade() < 0 || ambiental.getUmidade() > 100)
                throw new IllegalArgumentException("Umidade inválida.");

            if (!Double.isFinite(ambiental.getCo2()) || ambiental.getCo2() < 0)
                throw new IllegalArgumentException("Valor de CO2 inválido.");

        } else {
            throw new IllegalArgumentException("Tipo de leitura não reconhecido.");
        }
    }
}