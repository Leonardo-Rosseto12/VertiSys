package br.com.vertisys.service;

import br.com.vertisys.model.Receita;
import br.com.vertisys.repository.ReceitaRepository;
import java.sql.SQLException;
import java.util.List;

public class ReceitaService {

    private final ReceitaRepository repository;

    public ReceitaService() {
        this.repository = new ReceitaRepository();
    }

    public int cadastrar(Receita receita) throws SQLException {
        return repository.cadastrar(receita);
    }

    public Receita buscarPorId(int id) throws SQLException {
        return repository.buscarPorId(id);
    }

    public List<Receita> listar() throws SQLException {
        return repository.listar();
    }

    public boolean atualizar(Receita receita) throws SQLException {
        return repository.atualizar(receita);
    }

    public boolean excluir(int id) throws SQLException {
        return repository.excluir(id);
    }
}