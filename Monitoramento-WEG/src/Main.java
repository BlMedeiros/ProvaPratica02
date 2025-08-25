package main;

import service.SensorService;
import view.InterfaceUsuario;

public class Main {
    public static void main(String[] args) {

        SensorService service = new SensorService();

        InterfaceUsuario interfaceUsuario = new InterfaceUsuario(service);

        interfaceUsuario.exibirMenu();
    }
}
