package br.com.vertisys.controller;

import br.com.vertisys.model.Alerta;
import br.com.vertisys.model.Leitura;
import br.com.vertisys.service.AlertaService;
import java.sql.SQLException;
import java.util.List;

public class AlertaController {

    private final AlertaService service;

    public AlertaController() {
        this.service = new AlertaService();
    }

    public List<Alerta> analisarLeitura(Leitura leitura) throws SQLException {
        return service.analisarLeitura(leitura);
    }

    public Alerta buscarPorId(int id) throws SQLException {
        return service.buscarPorId(id);
    }

    public List<Alerta> listar() throws SQLException {
        return service.listar();
    }
}