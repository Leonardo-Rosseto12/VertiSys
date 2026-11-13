package br.com.vertisys.service;

import br.com.vertisys.model.Estante;
import br.com.vertisys.repository.EstanteRepository;
import java.sql.SQLException;
import java.util.List;

public class EstanteService {

    private final EstanteRepository repository;

    public EstanteService() {
        this.repository = new EstanteRepository();
    }

    private void validarEstante(Estante estante) {

        if (estante == null)
            throw new IllegalArgumentException("A estante deve ser informada.");

        if (estante.getLocalizacao() == null || estante.getLocalizacao().isBlank())
            throw new IllegalArgumentException("A localização deve ser informada.");

        if (estante.getStatus() == null || estante.getStatus().isBlank())
            throw new IllegalArgumentException("O status deve ser informado.");
    }

    public int cadastrar(Estante estante) throws SQLException {
        validarEstante(estante);
        return repository.cadastrar(estante);
    }

    public Estante buscarPorId(int id) throws SQLException {
        return repository.buscarPorId(id);
    }

    public List<Estante> listar() throws SQLException {
        return repository.listar();
    }

    public boolean atualizar(Estante estante) throws SQLException {
        validarEstante(estante);
        return repository.atualizar(estante);
    }

    public boolean excluir(int id) throws SQLException {
        return repository.excluir(id);
    }
}