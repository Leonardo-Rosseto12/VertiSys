package br.com.vertisys.controller;

import br.com.vertisys.model.Receita;
import br.com.vertisys.service.ReceitaService;
import java.sql.SQLException;
import java.util.List;

public class ReceitaController {

    private final ReceitaService service;

    public ReceitaController() {
        this.service = new ReceitaService();
    }

    public int cadastrar(Receita receita) throws SQLException {
        return service.cadastrar(receita);
    }

    public Receita buscarPorId(int id) throws SQLException {
        return service.buscarPorId(id);
    }

    public List<Receita> listar() throws SQLException {
        return service.listar();
    }

    public boolean atualizar(Receita receita) throws SQLException {
        return service.atualizar(receita);
    }

    public boolean excluir(int id) throws SQLException {
        return service.excluir(id);
    }
}