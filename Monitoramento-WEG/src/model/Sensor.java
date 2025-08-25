package model;

import model.enums.TipoSensor;

public abstract class Sensor {
    private String codigo;
    private String nomeEquipamento;
    private TipoSensor tipoSensor;

    public Sensor(String codigo, String nomeEquipamento, TipoSensor tipoSensor) {
        this.codigo = codigo;
        this.nomeEquipamento = nomeEquipamento;
        this.tipoSensor = tipoSensor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNomeEquipamento() {
        return nomeEquipamento;
    }

    public void setNomeEquipamento(String nomeEquipamento) {
        this.nomeEquipamento = nomeEquipamento;
    }

    public TipoSensor getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(TipoSensor tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public abstract boolean verificarAlerta(Medicao medicao);

    @Override
    public String toString() {
        return "Código: " + codigo +
                " | Tipo: " + tipoSensor +
                " | Equipamento: " + nomeEquipamento;
    }
}
