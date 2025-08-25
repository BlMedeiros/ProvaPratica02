package model;

import model.enums.TipoSensor;

public class SensorVibracao extends Sensor {

    public SensorVibracao(String codigo, String nomeEquipamento, TipoSensor tipoSensor) {
        super(codigo, nomeEquipamento, tipoSensor);
    }

    @Override
    public boolean verificarAlerta(Medicao medicao) {
        return medicao.getValor() != 60.0;
    }
}
