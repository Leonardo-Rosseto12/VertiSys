package br.com.vertisys.service;

import br.com.vertisys.model.Lote;
import br.com.vertisys.repository.LoteRepository;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoteService {

    private final LoteRepository repository;

    public LoteService() {
        this.repository = new LoteRepository();
    }

    private void validarLote(Lote lote) {

        if (lote == null)
            throw new IllegalArgumentException("O lote deve ser informado.");

        if (lote.getCultura() == null || lote.getCultura().getIdCultura() <= 0)
            throw new IllegalArgumentException("Uma cultura cadastrada deve ser informada.");

        if (lote.getCultura().getReceita() == null)
            throw new IllegalArgumentException("A cultura deve possuir uma receita.");

        if (lote.getCultura().getReceita().getCicloDias() <= 0)
            throw new IllegalArgumentException("O ciclo da receita deve ser maior que zero.");

        if (lote.getEstante() == null || lote.getEstante().getId() <= 0)
            throw new IllegalArgumentException("Uma estante cadastrada deve ser informada.");

        if (lote.getDataSemeadura() == null)
            throw new IllegalArgumentException("A data de semeadura deve ser informada.");

        if (lote.getQtdBandejas() <= 0)
            throw new IllegalArgumentException("A quantidade de bandejas deve ser maior que zero.");

        if (!Double.isFinite(lote.getAreaOcupada()) || lote.getAreaOcupada() <= 0)
            throw new IllegalArgumentException("A área ocupada deve ser maior que zero.");

    }

    private void validarStatus(String status) {

        if (status == null ||
                !List.of(
                        "EM_GERMINACAO",
                        "EM_CRESCIMENTO",
                        "PRONTO_PARA_COLHEITA",
                        "COLHIDO",
                        "CANCELADO"
                ).contains(status))
            throw new IllegalArgumentException("Status do lote inválido.");
    }

    public LocalDate calcularDataEstimadaColheita(Lote lote) {
        validarLote(lote);

        int cicloDias = lote.getCultura().getReceita().getCicloDias();

        return lote.getDataSemeadura().plusDays(cicloDias);
    }

    public int cadastrar(Lote lote) throws SQLException {

        validarLote(lote);

        lote.setStatus("EM_GERMINACAO");
        lote.setDataColheitaEstimada(calcularDataEstimadaColheita(lote));

        return repository.cadastrar(lote);
    }

    public Lote buscarPorId(int id) throws SQLException {
        return repository.buscarPorId(id);
    }

    public List<Lote> listar() throws SQLException {
        return repository.listar();
    }

    public boolean atualizar(Lote lote) throws SQLException {

        validarLote(lote);
        validarStatus(lote.getStatus());

        lote.setDataColheitaEstimada(calcularDataEstimadaColheita(lote));

        return repository.atualizar(lote);
    }

    public boolean cancelar(int id) throws SQLException {
        return repository.cancelar(id);
    }
}