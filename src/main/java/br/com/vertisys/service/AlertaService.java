package br.com.vertisys.service;

import br.com.vertisys.model.Alerta;
import br.com.vertisys.model.Leitura;
import br.com.vertisys.model.LeituraAmbiental;
import br.com.vertisys.model.LeituraHidroponica;
import br.com.vertisys.model.Lote;
import br.com.vertisys.model.Receita;
import br.com.vertisys.repository.AlertaRepository;
import br.com.vertisys.repository.LoteRepository;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlertaService {

    private final AlertaRepository alertaRepository = new AlertaRepository();
    private final LoteRepository loteRepository = new LoteRepository();

    private static final double UMIDADE_MEDIA = 65;
    private static final double UMIDADE_TOLERANCIA = 5;

    private static final double CO2_MEDIA = 900;
    private static final double CO2_TOLERANCIA = 100;

    public List<Alerta> analisarLeitura(Leitura leitura) throws SQLException {
        if (leitura == null || leitura.getId() <= 0)
            throw new IllegalArgumentException("Leitura inválida ou ainda não cadastrada.");

        if (leitura.getSensor() == null)
            throw new IllegalArgumentException("Sensor da leitura não informado.");

        Lote lote = loteRepository.buscarPorId(leitura.getSensor().getIdLote());

        if (lote == null || lote.getCultura() == null || lote.getCultura().getReceita() == null)
            throw new IllegalArgumentException("Não foi possível obter a receita do lote.");

        Receita receita = lote.getCultura().getReceita();

        List<Alerta> alertas = new ArrayList<>();

        if (leitura instanceof LeituraHidroponica hidroponica) {
            verificarParametro(alertas, leitura, "pH", hidroponica.getPh(),
                    receita.getPhMedio(), 0.5);

            verificarParametro(alertas, leitura, "EC", hidroponica.getEc(),
                    receita.getEcMedio(), 0.3);

        } else if (leitura instanceof LeituraAmbiental ambiental) {
            verificarParametro(alertas, leitura, "Temperatura do ar", ambiental.getTempAr(),
                    receita.getTempArAlvo(), 1);

            verificarParametro(alertas, leitura, "Umidade", ambiental.getUmidade(),
                    UMIDADE_MEDIA, UMIDADE_TOLERANCIA);

            verificarParametro(alertas, leitura, "CO2", ambiental.getCo2(),
                    CO2_MEDIA, CO2_TOLERANCIA);
        }

        for (Alerta alerta : alertas)
            alertaRepository.cadastrar(alerta);

        return alertas;
    }

    private void verificarParametro(List<Alerta> alertas, Leitura leitura,
                                    String parametro, double valor,
                                    double media, double tolerancia) {

        double limiteInferior = media - tolerancia;
        double limiteSuperior = media + tolerancia;

        if (valor < limiteInferior || valor > limiteSuperior) {
            double desvio = valor < limiteInferior
                    ? limiteInferior - valor
                    : valor - limiteSuperior;

            String criticidade = desvio >= tolerancia * 2 ? "ALTA" : "MEDIA";

            String descricao = String.format(
                    "%s fora dos limites. Valor registrado: %.2f. Limites esperados: %.2f a %.2f.",
                    parametro, valor, limiteInferior, limiteSuperior
            );

            Alerta alerta = new Alerta(
                    criticidade,
                    parametro,
                    descricao,
                    LocalDateTime.now(),
                    leitura
            );

            alertas.add(alerta);
        }
    }

    public Alerta buscarPorId(int id) throws SQLException {
        if (id <= 0)
            throw new IllegalArgumentException("ID do alerta inválido.");

        return alertaRepository.buscarPorId(id);
    }

    public List<Alerta> listar() throws SQLException {
        return alertaRepository.listar();
    }
}