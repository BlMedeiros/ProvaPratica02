package model;

import model.enums.TipoSensor;

public class SensorTemperatura extends Sensor {

    public SensorTemperatura(String codigo, String nomeEquipamento, TipoSensor tipoSensor) {
        super(codigo, nomeEquipamento, tipoSensor);
    }

    @Override
    public boolean verificarAlerta(Medicao medicao) {
        return medicao.getValor() > 80.0;
    }
}
