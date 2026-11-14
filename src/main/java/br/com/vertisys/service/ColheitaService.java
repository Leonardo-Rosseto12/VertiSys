package br.com.vertisys.service;

import br.com.vertisys.model.Colheita;
import br.com.vertisys.model.Estante;
import br.com.vertisys.model.Lote;
import br.com.vertisys.repository.ColheitaRepository;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ColheitaService {

    private final ColheitaRepository colheitaRepository = new ColheitaRepository();
    private final LoteService loteService = new LoteService();
    private final EstanteService estanteService = new EstanteService();

    public boolean cadastrar(Colheita colheita) throws SQLException {
        validar(colheita);

        Lote lote = colheita.getLote();

        if (!"PRONTO_PARA_COLHEITA".equals(lote.getStatus()))
            throw new IllegalArgumentException("O lote não está pronto para colheita.");

        if (colheitaRepository.buscarPorLote(lote.getId()) != null)
            throw new IllegalArgumentException("Este lote já possui uma colheita registrada.");

        boolean cadastrada = colheitaRepository.cadastrar(colheita);

        if (cadastrada) {
            lote.setStatus("COLHIDO");
            loteService.atualizar(lote);

            Estante estante = lote.getEstante();
            estante.setStatus("DISPONIVEL");
            estanteService.atualizar(estante);
        }

        return cadastrada;
    }

    public Colheita buscarPorLote(int idLote) throws SQLException {
        if (idLote <= 0)
            throw new IllegalArgumentException("ID do lote inválido.");

        return colheitaRepository.buscarPorLote(idLote);
    }

    public ArrayList<Colheita> listar() throws SQLException {
        return colheitaRepository.listar();
    }

    public void validar(Colheita colheita) {
        if (colheita == null)
            throw new IllegalArgumentException("A colheita deve ser informada.");

        if (colheita.getData() == null)
            throw new IllegalArgumentException("A data da colheita deve ser informada.");

        if (colheita.getData().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("A data da colheita não pode ser futura.");

        if (colheita.getPesoBiomassa() <= 0 || !Double.isFinite(colheita.getPesoBiomassa()))
            throw new IllegalArgumentException("O peso da biomassa deve ser maior que zero.");

        if (colheita.getPesoDescarte() < 0 || !Double.isFinite(colheita.getPesoDescarte()))
            throw new IllegalArgumentException("O peso de descarte não pode ser negativo.");

        if (colheita.getPesoDescarte() > colheita.getPesoBiomassa())
            throw new IllegalArgumentException("O peso de descarte não pode superar o peso da biomassa.");

        if (colheita.getLote() == null || colheita.getLote().getId() <= 0)
            throw new IllegalArgumentException("Um lote válido deve ser informado.");

        if (colheita.getData().isBefore(colheita.getLote().getDataSemeadura()))
            throw new IllegalArgumentException("A data da colheita não pode ser anterior à semeadura.");
    }
}