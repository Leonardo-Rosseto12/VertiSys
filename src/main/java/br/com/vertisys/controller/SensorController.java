package br.com.vertisys.controller;

import br.com.vertisys.model.Sensor;
import br.com.vertisys.service.SensorService;
import java.sql.SQLException;
import java.util.List;

public class SensorController {

    private final SensorService service;

    public SensorController() {
        this.service = new SensorService();
    }

    public int cadastrar(Sensor sensor) throws SQLException {
        return service.cadastrar(sensor);
    }

    public Sensor buscarPorId(int id) throws SQLException {
        return service.buscarPorId(id);
    }

    public List<Sensor> listar() throws SQLException {
        return service.listar();
    }

    public boolean atualizar(Sensor sensor) throws SQLException {
        return service.atualizar(sensor);
    }

    public boolean excluir(int id) throws SQLException {
        return service.excluir(id);
    }
}