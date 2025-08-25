package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Medicao {
    private Double valor;
    private LocalDateTime dataHora;

    public Medicao(Double valor, LocalDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public void setDataHora() { this.dataHora = LocalDateTime.now(); }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Valor: " + valor + " | Data: " + dataHora.format(formatter);
    }
}
