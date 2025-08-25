package view;

import model.*;
import model.enums.TipoSensor;
import service.SensorService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InterfaceUsuario {

    private SensorService service;
    private Scanner scanner;

    public InterfaceUsuario(SensorService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;

        do {
            System.out.println("=========================================");
            System.out.println(" Sistema de Monitoramento WEG – Versão 1.0");
            System.out.println("=========================================");
            System.out.println("1 - Cadastrar Sensor");
            System.out.println("2 - Listar Sensores");
            System.out.println("3 - Registrar Medição");
            System.out.println("4 - Exibir Histórico de Medições");
            System.out.println("5 - Verificar Alertas");
            System.out.println("6 - Listar Sensores Críticos");
            System.out.println("0 - Sair");
            System.out.print("\nDigite a opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        cadastrarSensor();
                    break;
                    case 2:
                        listarSensores();
                    break;
                    case 3:
                        registrarMedicao();
                    break;
                    case 4:
                        exibirHistorico();
                    break;
                    case 5:
                        verificarAlertas();
                    break;
                    case 6:
                        listarSensoresCriticos();
                    break;
                    case 0:
                        System.out.println("\nEncerrando sistema... Obrigado por usar o Monitoramento WEG!");
                    break;
                    default:
                        System.out.println("❌ Opção inválida! Tente novamente.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Erro: entrada inválida. Digite um número!");
            }

        } while (opcao != 0);
    }
    private void cadastrarSensor() {
        try {
            System.out.print("Digite o código do sensor: ");
            String codigo = scanner.nextLine();

            System.out.print("Digite o nome do equipamento: ");
            String nome = scanner.nextLine();

            System.out.println("Escolha o tipo de sensor:");
            System.out.println("1 - Temperatura");
            System.out.println("2 - Vibração");
            System.out.print("Opção: ");
            int tipo = Integer.parseInt(scanner.nextLine());

            Sensor sensor;
            if (tipo == 1) {
                sensor = new SensorTemperatura(codigo, nome, TipoSensor.TEMPERATURA);
                service.cadastrarSensor(sensor);
                System.out.println("✅ Sensor cadastrado com sucesso!");
                System.out.println("Tipo: Temperatura | Limite de alerta: 80.0 °C");
            } else if (tipo == 2) {
                sensor = new SensorVibracao(codigo, nome, TipoSensor.VIBRACAO);
                service.cadastrarSensor(sensor);
                System.out.println("✅ Sensor cadastrado com sucesso!");
                System.out.println("Tipo: Vibração | Limite de alerta: 60.0 Hz");
            } else {
                System.out.println("❌ Tipo inválido!");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Erro ao cadastrar sensor: " + e.getMessage());
        }
    }

    private void listarSensores() {
        System.out.println("\nSensores Cadastrados:\n");
        for (Sensor s : service.listarSensores()) {
            System.out.println(s);
        }
    }

    private void registrarMedicao() {
        try {
            System.out.print("Digite o código do sensor: ");
            String codigo = scanner.nextLine();

            System.out.print("Digite o valor da medição: ");
            double valor = Double.parseDouble(scanner.nextLine());

            System.out.print("Digite a data e hora (formato dd/MM/yyyy HH:mm): ");
            String dataHoraStr = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

            Medicao medicao = new Medicao(valor, dataHora);
            boolean alerta = service.registrarMedicao(codigo, medicao);

            System.out.println("✅ Medição registrada com sucesso!");
            if (alerta) {
                System.out.println("⚠️ ALERTA: Medição fora do limite técnico!");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Erro ao registrar medição: " + e.getMessage());
        }
    }

    private void exibirHistorico() {
        System.out.print("Digite o código do sensor: ");
        String codigo = scanner.nextLine();

        List<Medicao> medicoes = service.listarMedicoes(codigo);
        if (medicoes.isEmpty()) {
            System.out.println("Nenhuma medição registrada para este sensor.");
        } else {
            System.out.println("\nHistórico de Medições do Sensor " + codigo + ":\n");
            int i = 1;
            for (Medicao m : medicoes) {
                boolean alerta = service.verificarAlertas().getOrDefault(codigo, 0) > 0 && m.getValor() > 80.0; // simplificado
                System.out.println(i++ + ". " + m + (alerta ? " ⚠️ ALERTA" : ""));
            }
        }
    }

    private void verificarAlertas() {
        System.out.println("\nVerificando sensores...\n");
        Map<String, Integer> alertas = service.verificarAlertas();
        for (Sensor s : service.listarSensores()) {
            int qtd = alertas.getOrDefault(s.getCodigo(), 0);
            if (qtd > 0) {
                System.out.println("Sensor " + s.getCodigo() + " (" + s.getTipoSensor() + ") – " + s.getNomeEquipamento() + ":");
                System.out.println("⚠️ " + qtd + " alerta(s) detectado(s)\n");
            } else {
                System.out.println("Sensor " + s.getCodigo() + " (" + s.getTipoSensor() + ") – " + s.getNomeEquipamento() + ":");
                System.out.println("✅ Nenhum alerta\n");
            }
        }
    }

    private void listarSensoresCriticos() {
        System.out.println("\nSensores com mais de 3 alertas:\n");
        List<Sensor> criticos = service.listarSensoresCriticos();
        if (criticos.isEmpty()) {
            System.out.println("Nenhum sensor crítico encontrado.");
        } else {
            for (Sensor s : criticos) {
                int qtd = service.verificarAlertas().getOrDefault(s.getCodigo(), 0);
                System.out.println("Código: " + s.getCodigo() + " | Tipo: " + s.getTipoSensor() +
                        " | Equipamento: " + s.getNomeEquipamento() + " | Alertas: " + qtd);
            }
            System.out.println("\n⚠️ ATENÇÃO: Inspeção imediata recomendada!");
        }
    }
}
