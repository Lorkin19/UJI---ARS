package modelo;

import common.IProyector;
import controlador.proyector.CuestionarioEnProcesoController;
import controlador.proyector.HomeProyectorController;
import javafx.application.Platform;
import vista.Main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class Proyector extends UnicastRemoteObject implements IProyector {

    private Main main;
    private HomeProyectorController homeProyectorController;
    private CuestionarioEnProcesoController cuestionarioEnProcesoController;

    public Proyector() throws RemoteException {
    }

    public void setHomeProyectorController(HomeProyectorController homeProyectorController) {
        this.homeProyectorController = homeProyectorController;
    }

    public void setCuestionarioEnProcesoController(CuestionarioEnProcesoController cuestionarioEnProcesoController) {
        this.cuestionarioEnProcesoController = cuestionarioEnProcesoController;
    }


    @Override
    public void anyadeAlumno(String nombreAlumno) throws RemoteException {
        Platform.runLater(() -> homeProyectorController.setAlumno(nombreAlumno));
    }

    @Override
    public void verPregunta(String enunciado, List<String> respuestas) throws RemoteException{
        // TODO Falta mirar el tiempo disponible para contestar
        System.out.println("Enunciado: " + enunciado);
        Platform.runLater(() -> cuestionarioEnProcesoController.setPreguntas(enunciado, respuestas));
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
