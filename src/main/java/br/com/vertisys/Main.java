package br.com.vertisys;

import br.com.vertisys.controller.CulturaController;
import br.com.vertisys.controller.ReceitaController;
import br.com.vertisys.controller.EstanteController;
import br.com.vertisys.controller.LoteController;
import br.com.vertisys.model.Cultura;
import br.com.vertisys.model.Receita;
import br.com.vertisys.model.Estante;
import br.com.vertisys.model.Lote;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    private static final ReceitaController receitaController = new ReceitaController();
    private static final CulturaController culturaController = new CulturaController();
    private static final EstanteController estanteController = new EstanteController();
    private static final LoteController loteController = new LoteController();

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n---------- VERTISYS ----------");
            System.out.println("1 - Gerenciar Receitas");
            System.out.println("2 - Gerenciar Culturas");
            System.out.println("3 - Gerenciar Estantes");
            System.out.println("4 - Gerenciar Lotes");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(input.nextLine());

            switch (opcao) {
                case 1 -> menuReceita();
                case 2 -> menuCultura();
                case 3 -> menuEstante();
                case 4 -> menuLote();
                case 0 -> System.out.println("Encerrando o VertiSys...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        input.close();
    }

    private static void menuReceita() {

        int opcao;

        do {
            System.out.println("\n---------- RECEITAS ----------");
            System.out.println("1 - Cadastrar receita");
            System.out.println("2 - Consultar receita");
            System.out.println("3 - Listar receitas");
            System.out.println("4 - Atualizar receita");
            System.out.println("5 - Excluir receita");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(input.nextLine());

            try {
                switch (opcao) {
                    case 1 -> cadastrarReceita();
                    case 2 -> consultarReceita();
                    case 3 -> listarReceitas();
                    case 4 -> atualizarReceita();
                    case 5 -> excluirReceita();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao executar operação: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private static void menuCultura() {

        int opcao;

        do {
            System.out.println("\n---------- CULTURAS ----------");
            System.out.println("1 - Cadastrar cultura");
            System.out.println("2 - Consultar cultura");
            System.out.println("3 - Listar culturas");
            System.out.println("4 - Atualizar cultura");
            System.out.println("5 - Excluir cultura");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(input.nextLine());

            try {
                switch (opcao) {
                    case 1 -> cadastrarCultura();
                    case 2 -> consultarCultura();
                    case 3 -> listarCulturas();
                    case 4 -> atualizarCultura();
                    case 5 -> excluirCultura();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao executar operação: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private static void menuEstante() {

        int opcao;

        do {
            System.out.println("\n---------- ESTANTES ----------");
            System.out.println("1 - Cadastrar estante");
            System.out.println("2 - Consultar estante");
            System.out.println("3 - Listar estantes");
            System.out.println("4 - Atualizar estante");
            System.out.println("5 - Excluir estante");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(input.nextLine());

            try {
                switch (opcao) {
                    case 1 -> cadastrarEstante();
                    case 2 -> consultarEstante();
                    case 3 -> listarEstantes();
                    case 4 -> atualizarEstante();
                    case 5 -> excluirEstante();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao executar operação: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Dados inválidos: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private static void menuLote() {

        int opcao;

        do {
            System.out.println("\n---------- LOTES ----------");
            System.out.println("1 - Cadastrar lote");
            System.out.println("2 - Consultar lote");
            System.out.println("3 - Listar lotes");
            System.out.println("4 - Atualizar lote");
            System.out.println("5 - Cancelar lote");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(input.nextLine());

            try {
                switch (opcao) {
                    case 1 -> cadastrarLote();
                    case 2 -> consultarLote();
                    case 3 -> listarLotes();
                    case 4 -> atualizarLote();
                    case 5 -> cancelarLote();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao executar operação: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Dados inválidos: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private static void cadastrarReceita() throws SQLException {

        System.out.println("\n---------- CADASTRAR RECEITA ----------");

        Receita receita = lerDadosReceita();

        int id = receitaController.cadastrar(receita);

        if (id > 0) System.out.println("Receita cadastrada! ID: " + id);
        else System.out.println("Não foi possível cadastrar a receita.");
    }

    private static void consultarReceita() throws SQLException {

        System.out.println("\n---------- CONSULTAR RECEITA ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        Receita receita = receitaController.buscarPorId(id);

        if (receita == null) {
            System.out.println("Receita não encontrada!");
            return;
        }

        exibirReceita(receita);
    }

    private static void listarReceitas() throws SQLException {

        System.out.println("\n---------- LISTAR RECEITAS ----------");

        List<Receita> receitas = receitaController.listar();

        if (receitas.isEmpty()) {
            System.out.println("Nenhuma receita cadastrada.");
            return;
        }

        for (Receita receita : receitas) {
            System.out.println("\nID: " + receita.getIdReceita());
            exibirReceita(receita);
        }
    }

    private static void atualizarReceita() throws SQLException {

        System.out.println("\n---------- ATUALIZAR RECEITA ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        Receita receita = receitaController.buscarPorId(id);

        if (receita == null) {
            System.out.println("Receita não encontrada!");
            return;
        }

        Receita novosDados = lerDadosReceita();

        receita.setDensidadeSemeadura(novosDados.getDensidadeSemeadura());
        receita.setPhMedio(novosDados.getPhMedio());
        receita.setEcMedio(novosDados.getEcMedio());
        receita.setCicloDias(novosDados.getCicloDias());
        receita.setTempArAlvo(novosDados.getTempArAlvo());
        receita.setFotoperiodo(novosDados.getFotoperiodo());

        if (receitaController.atualizar(receita))
            System.out.println("Receita atualizada!");
        else
            System.out.println("Não foi possível atualizar a receita.");
    }

    private static void excluirReceita() throws SQLException {

        System.out.println("\n---------- EXCLUIR RECEITA ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        if (receitaController.excluir(id))
            System.out.println("Receita excluída!");
        else
            System.out.println("Receita não encontrada ou não foi possível excluí-la.");
    }

    private static Receita lerDadosReceita() {

        System.out.print("Densidade de semeadura (plantas/m²): ");
        double densidade = Double.parseDouble(input.nextLine());

        System.out.print("pH médio: ");
        double ph = Double.parseDouble(input.nextLine());

        System.out.print("EC médio: ");
        double ec = Double.parseDouble(input.nextLine());

        System.out.print("Ciclo (dias): ");
        int ciclo = Integer.parseInt(input.nextLine());

        System.out.print("Temperatura do ar alvo: ");
        double temperatura = Double.parseDouble(input.nextLine());

        System.out.print("Fotoperíodo: ");
        double fotoperiodo = Double.parseDouble(input.nextLine());

        return new Receita(densidade, ph, ec, ciclo, temperatura, fotoperiodo);
    }

    private static void exibirReceita(Receita receita) {

        System.out.println("Densidade: " + receita.getDensidadeSemeadura());
        System.out.println("pH médio: " + receita.getPhMedio());
        System.out.println("EC médio: " + receita.getEcMedio());
        System.out.println("Ciclo: " + receita.getCicloDias() + " dias");
        System.out.println("Temperatura alvo: " + receita.getTempArAlvo());
        System.out.println("Fotoperíodo: " + receita.getFotoperiodo());
    }

    private static void cadastrarCultura() throws SQLException {

        System.out.println("\n---------- CADASTRAR CULTURA ----------");

        System.out.print("Nome popular: ");
        String nomePopular = input.nextLine();

        System.out.print("Nome científico: ");
        String nomeCientifico = input.nextLine();

        System.out.print("ID da receita vinculada: ");
        int idReceita = Integer.parseInt(input.nextLine());

        Receita receita = receitaController.buscarPorId(idReceita);

        if (receita == null) {
            System.out.println("Receita não encontrada!");
            return;
        }

        Cultura cultura = new Cultura(nomePopular, nomeCientifico, receita);

        int id = culturaController.cadastrar(cultura);

        if (id > 0) System.out.println("Cultura cadastrada! ID: " + id);
        else System.out.println("Não foi possível cadastrar a cultura.");
    }

    private static void consultarCultura() throws SQLException {

        System.out.println("\n---------- CONSULTAR CULTURA ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        Cultura cultura = culturaController.buscarPorId(id);

        if (cultura == null) {
            System.out.println("Cultura não encontrada!");
            return;
        }

        exibirCultura(cultura);
    }

    private static void listarCulturas() throws SQLException {

        System.out.println("\n---------- LISTAR CULTURAS ----------");

        List<Cultura> culturas = culturaController.listar();

        if (culturas.isEmpty()) {
            System.out.println("Nenhuma cultura cadastrada.");
            return;
        }

        for (Cultura cultura : culturas) {
            System.out.println();
            exibirCultura(cultura);
        }
    }

    private static void atualizarCultura() throws SQLException {

        System.out.println("\n---------- ATUALIZAR CULTURA ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        Cultura cultura = culturaController.buscarPorId(id);

        if (cultura == null) {
            System.out.println("Cultura não encontrada!");
            return;
        }

        System.out.print("Novo nome popular: ");
        cultura.setNomePopular(input.nextLine());

        System.out.print("Novo nome científico: ");
        cultura.setNomeCientifico(input.nextLine());

        System.out.print("Novo ID da receita: ");
        int idReceita = Integer.parseInt(input.nextLine());

        Receita receita = receitaController.buscarPorId(idReceita);

        if (receita == null) {
            System.out.println("Receita não encontrada!");
            return;
        }

        cultura.setReceita(receita);

        if (culturaController.atualizar(cultura))
            System.out.println("Cultura atualizada!");
        else
            System.out.println("Não foi possível atualizar a cultura.");
    }

    private static void excluirCultura() throws SQLException {

        System.out.println("\n---------- EXCLUIR CULTURA ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        if (culturaController.excluir(id))
            System.out.println("Cultura excluída!");
        else
            System.out.println("Cultura não encontrada ou não foi possível excluí-la.");
    }

    private static void exibirCultura(Cultura cultura) {

        System.out.println("ID: " + cultura.getIdCultura());
        System.out.println("Nome popular: " + cultura.getNomePopular());
        System.out.println("Nome científico: " + cultura.getNomeCientifico());
        System.out.println("Receita vinculada: " + cultura.getReceita().getIdReceita());
        System.out.println("pH médio: " + cultura.getReceita().getPhMedio());
        System.out.println("EC médio: " + cultura.getReceita().getEcMedio());
    }

    private static void cadastrarEstante() throws SQLException {

        System.out.println("\n---------- CADASTRAR ESTANTE ----------");

        System.out.print("Localização: ");
        String localizacao = input.nextLine();

        System.out.print("Status: ");
        String status = input.nextLine();

        Estante estante = new Estante(localizacao, status);

        int id = estanteController.cadastrar(estante);

        if (id > 0) System.out.println("Estante cadastrada! ID: " + id);
        else System.out.println("Não foi possível cadastrar a estante.");
    }

    private static void consultarEstante() throws SQLException {

        System.out.println("\n---------- CONSULTAR ESTANTE ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        Estante estante = estanteController.buscarPorId(id);

        if (estante == null) {
            System.out.println("Estante não encontrada!");
            return;
        }

        exibirEstante(estante);
    }

    private static void listarEstantes() throws SQLException {

        System.out.println("\n---------- LISTAR ESTANTES ----------");

        List<Estante> estantes = estanteController.listar();

        if (estantes.isEmpty()) {
            System.out.println("Nenhuma estante cadastrada.");
            return;
        }

        for (Estante estante : estantes) {
            System.out.println();
            exibirEstante(estante);
        }
    }

    private static void atualizarEstante() throws SQLException {

        System.out.println("\n---------- ATUALIZAR ESTANTE ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        Estante estante = estanteController.buscarPorId(id);

        if (estante == null) {
            System.out.println("Estante não encontrada!");
            return;
        }

        System.out.print("Nova localização: ");
        estante.setLocalizacao(input.nextLine());

        System.out.print("Novo status: ");
        estante.setStatus(input.nextLine());

        if (estanteController.atualizar(estante))
            System.out.println("Estante atualizada!");
        else
            System.out.println("Não foi possível atualizar a estante.");
    }

    private static void excluirEstante() throws SQLException {

        System.out.println("\n---------- EXCLUIR ESTANTE ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        if (estanteController.excluir(id))
            System.out.println("Estante excluída!");
        else
            System.out.println("Estante não encontrada ou não foi possível excluí-la.");
    }

    private static void exibirEstante(Estante estante) {

        System.out.println("ID: " + estante.getId());
        System.out.println("Localização: " + estante.getLocalizacao());
        System.out.println("Status: " + estante.getStatus());
    }

    private static void cadastrarLote() throws SQLException {

        System.out.println("\n---------- CADASTRAR LOTE ----------");

        System.out.print("ID da cultura: ");
        int idCultura = Integer.parseInt(input.nextLine());

        Cultura cultura = culturaController.buscarPorId(idCultura);

        if (cultura == null) {
            System.out.println("Cultura não encontrada!");
            return;
        }

        System.out.print("ID da estante: ");
        int idEstante = Integer.parseInt(input.nextLine());

        Estante estante = estanteController.buscarPorId(idEstante);

        if (estante == null) {
            System.out.println("Estante não encontrada!");
            return;
        }

        System.out.print("Data de semeadura (AAAA-MM-DD): ");
        LocalDate dataSemeadura = LocalDate.parse(input.nextLine());

        System.out.print("Quantidade de bandejas: ");
        int qtdBandejas = Integer.parseInt(input.nextLine());

        System.out.print("Área ocupada: ");
        double areaOcupada = Double.parseDouble(input.nextLine());

        Lote lote = new Lote(
                dataSemeadura,
                "EM_GERMINACAO",
                null,
                qtdBandejas,
                areaOcupada,
                cultura,
                estante
        );

        int id = loteController.cadastrar(lote);

        if (id > 0) {
            System.out.println("Lote cadastrado! ID: " + id);
            System.out.println("Status: " + lote.getStatus());
            System.out.println("Previsão de colheita: " + lote.getDataColheitaEstimada());
        } else
            System.out.println("Não foi possível cadastrar o lote.");
    }

    private static void consultarLote() throws SQLException {

        System.out.println("\n---------- CONSULTAR LOTE ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        Lote lote = loteController.buscarPorId(id);

        if (lote == null) {
            System.out.println("Lote não encontrado!");
            return;
        }

        exibirLote(lote);
    }

    private static void listarLotes() throws SQLException {

        System.out.println("\n---------- LISTAR LOTES ----------");

        List<Lote> lotes = loteController.listar();

        if (lotes.isEmpty()) {
            System.out.println("Nenhum lote cadastrado.");
            return;
        }

        for (Lote lote : lotes) {
            System.out.println();
            exibirLote(lote);
        }
    }

    private static void atualizarLote() throws SQLException {

        System.out.println("\n---------- ATUALIZAR LOTE ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        Lote lote = loteController.buscarPorId(id);

        if (lote == null) {
            System.out.println("Lote não encontrado!");
            return;
        }

        System.out.print("Novo ID da cultura: ");
        int idCultura = Integer.parseInt(input.nextLine());

        Cultura cultura = culturaController.buscarPorId(idCultura);

        if (cultura == null) {
            System.out.println("Cultura não encontrada!");
            return;
        }

        System.out.print("Novo ID da estante: ");
        int idEstante = Integer.parseInt(input.nextLine());

        Estante estante = estanteController.buscarPorId(idEstante);

        if (estante == null) {
            System.out.println("Estante não encontrada!");
            return;
        }

        System.out.print("Nova data de semeadura (AAAA-MM-DD): ");
        LocalDate dataSemeadura = LocalDate.parse(input.nextLine());

        System.out.print("Nova quantidade de bandejas: ");
        int qtdBandejas = Integer.parseInt(input.nextLine());

        System.out.print("Nova área ocupada: ");
        double areaOcupada = Double.parseDouble(input.nextLine());

        System.out.print("Novo status: ");
        String status = input.nextLine();

        lote.setCultura(cultura);
        lote.setEstante(estante);
        lote.setDataSemeadura(dataSemeadura);
        lote.setQtdBandejas(qtdBandejas);
        lote.setAreaOcupada(areaOcupada);
        lote.setStatus(status);

        if (loteController.atualizar(lote)) {
            System.out.println("Lote atualizado!");
            System.out.println("Nova previsão de colheita: " + lote.getDataColheitaEstimada());
        } else
            System.out.println("Não foi possível atualizar o lote.");
    }

    private static void cancelarLote() throws SQLException {

        System.out.println("\n---------- CANCELAR LOTE ----------");
        System.out.print("Informe o ID: ");

        int id = Integer.parseInt(input.nextLine());

        if (loteController.cancelar(id))
            System.out.println("Lote cancelado!");
        else
            System.out.println("Lote não encontrado ou não foi possível cancelá-lo.");
    }

    private static void exibirLote(Lote lote) {

        System.out.println("ID: " + lote.getId());
        System.out.println("Cultura: " + lote.getCultura().getNomePopular());
        System.out.println("Estante: " + lote.getEstante().getLocalizacao());
        System.out.println("Data de semeadura: " + lote.getDataSemeadura());
        System.out.println("Status: " + lote.getStatus());
        System.out.println("Previsão de colheita: " + lote.getDataColheitaEstimada());
        System.out.println("Quantidade de bandejas: " + lote.getQtdBandejas());
        System.out.println("Área ocupada: " + lote.getAreaOcupada());
    }
}