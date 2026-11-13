package br.com.vertisys.controller;

import br.com.vertisys.model.Lote;
import br.com.vertisys.service.LoteService;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoteController {
    private final LoteService service;

    public LoteController() {
        this.service = new LoteService();
    }

    public int cadastrar(Lote lote) throws SQLException {
        return service.cadastrar(lote);
    }

    public Lote buscarPorId(int id) throws SQLException {
        return service.buscarPorId(id);
    }

    public List<Lote> listar() throws SQLException {
        return service.listar();
    }

    public boolean atualizar(Lote lote) throws SQLException {
        return service.atualizar(lote);
    }

    public boolean cancelar(int id) throws SQLException {
        return service.cancelar(id);
    }

    public LocalDate calcularDataEstimadaColheita(Lote lote) {
        return service.calcularDataEstimadaColheita(lote);
    }
}