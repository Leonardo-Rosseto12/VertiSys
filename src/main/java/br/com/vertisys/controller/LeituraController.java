package br.com.vertisys.controller;

import br.com.vertisys.model.Leitura;
import br.com.vertisys.service.LeituraService;
import java.sql.SQLException;
import java.util.List;

public class LeituraController {

    private final LeituraService service;

    public LeituraController() {
        this.service = new LeituraService();
    }

    public int cadastrar(Leitura leitura) throws SQLException {
        return service.cadastrar(leitura);
    }

    public Leitura buscarPorId(int id) throws SQLException {
        return service.buscarPorId(id);
    }

    public List<Leitura> listar() throws SQLException {
        return service.listar();
    }
}