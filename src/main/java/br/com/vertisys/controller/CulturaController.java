package br.com.vertisys.controller;

import br.com.vertisys.model.Cultura;
import br.com.vertisys.service.CulturaService;

import java.sql.SQLException;
import java.util.List;

public class CulturaController {

    private final CulturaService service;

    public CulturaController() {
        this.service = new CulturaService();
    }

    public int cadastrar(Cultura cultura) throws SQLException {
        return service.cadastrar(cultura);
    }

    public Cultura buscarPorId(int id) throws SQLException {
        return service.buscarPorId(id);
    }

    public List<Cultura> listar() throws SQLException {
        return service.listar();
    }

    public boolean atualizar(Cultura cultura) throws SQLException {
        return service.atualizar(cultura);
    }

    public boolean excluir(int id) throws SQLException {
        return service.excluir(id);
    }
}