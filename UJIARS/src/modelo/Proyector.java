package modelo;

import common.IProyector;
import javafx.application.Platform;
import vista.Main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class Proyector extends UnicastRemoteObject implements IProyector {

    private Main main;

    public Proyector() throws RemoteException {
    }


    @Override
    public void verPregunta(String enunciado, List<String> respuestas) throws RemoteException{
        // TODO Falta mirar el tiempo disponible para contestar
        System.out.println("Enunciado: " + enunciado);
        Platform.runLater(() -> main.proyectorMuestraPregunta(enunciado, respuestas));
        //main.proyectorMuestraPregunta(enunciado, respuestas);

    }

    @Override
    public void verResultados(Map<String, Integer> resultados) throws RemoteException {
        // TODO Cambiarlo para pasarle tambien los resultados de la pregunta
        for (String respuesta : resultados.keySet()) {
            System.out.print(respuesta);
            System.out.print("-->");
            System.out.println(resultados.get(respuesta));
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
