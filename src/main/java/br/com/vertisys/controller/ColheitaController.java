package br.com.vertisys.controller;

import br.com.vertisys.model.Colheita;
import br.com.vertisys.service.ColheitaService;
import java.sql.SQLException;
import java.util.ArrayList;

public class ColheitaController {

    private final ColheitaService colheitaService = new ColheitaService();

    public boolean cadastrar(Colheita colheita) throws SQLException {
        return colheitaService.cadastrar(colheita);
    }

    public Colheita buscarPorLote(int idLote) throws SQLException {
        return colheitaService.buscarPorLote(idLote);
    }

    public ArrayList<Colheita> listar() throws SQLException {
        return colheitaService.listar();
    }
}