package br.com.vertisys.service;

import br.com.vertisys.model.Cultura;
import br.com.vertisys.repository.CulturaRepository;
import java.sql.SQLException;
import java.util.List;

public class CulturaService {

    private final CulturaRepository repository;

    public CulturaService() {
        this.repository = new CulturaRepository();
    }

    public int cadastrar(Cultura cultura) throws SQLException {
        return repository.cadastrar(cultura);
    }

    public Cultura buscarPorId(int id) throws SQLException {
        return repository.buscarPorId(id);
    }

    public List<Cultura> listar() throws SQLException {
        return repository.listar();
    }

    public boolean atualizar(Cultura cultura) throws SQLException {
        return repository.atualizar(cultura);
    }

    public boolean excluir(int id) throws SQLException {
        return repository.excluir(id);
    }
}