package service;

import model.Medicao;
import model.Sensor;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class SensorService {

    private List<Sensor> sensores = new ArrayList<>();
    private Map<String, List<Medicao>> historicoMedicoes = new HashMap<>();
    private Map<String, Integer> alertasPorSensor = new HashMap<>();

    public void cadastrarSensor(Sensor sensor) {
        sensores.add(sensor);
        historicoMedicoes.put(sensor.getCodigo(), new ArrayList<>());
        alertasPorSensor.put(sensor.getCodigo(), 0);
    }

    public List<Sensor> listarSensores() {
        return sensores;
    }

    public boolean registrarMedicao(String codigo, Medicao medicao) {
        Sensor sensor = buscarSensor(codigo);
        if (sensor != null) {
            historicoMedicoes.get(codigo).add(medicao);

            boolean alerta = sensor.verificarAlerta(medicao);
            if (alerta) {
                alertasPorSensor.put(codigo, alertasPorSensor.get(codigo) + 1);
            }
            return alerta;
        }
        return false; // sensor não encontrado
    }

    public List<Medicao> listarMedicoes(String codigo) {
        return historicoMedicoes.getOrDefault(codigo, new ArrayList<>());
    }

    public Map<String, Integer> verificarAlertas() {
        return alertasPorSensor;
    }

    public List<Sensor> listarSensoresCriticos() {
        List<Sensor> criticos = new ArrayList<>();
        for (Sensor s : sensores) {
            if (alertasPorSensor.getOrDefault(s.getCodigo(), 0) > 3) {
                criticos.add(s);
            }
        }
        return criticos;
    }

    private Sensor buscarSensor(String codigo) {
        for (Sensor s : sensores) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                return s;
            }
        }
        return null;
    }
}
