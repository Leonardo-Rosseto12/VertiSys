package br.com.vertisys.controller;

import br.com.vertisys.model.Estante;
import br.com.vertisys.service.EstanteService;
import java.sql.SQLException;
import java.util.List;

public class EstanteController {
    private final EstanteService service;

    public EstanteController() {
        this.service = new EstanteService();
    }

    public int cadastrar(Estante estante) throws SQLException {
        return service.cadastrar(estante);
    }

    public Estante buscarPorId(int id) throws SQLException {
        return service.buscarPorId(id);
    }

    public List<Estante> listar() throws SQLException {
        return service.listar();
    }

    public boolean atualizar(Estante estante) throws SQLException {
        return service.atualizar(estante);
    }

    public boolean excluir(int id) throws SQLException {
        return service.excluir(id);
    }
}