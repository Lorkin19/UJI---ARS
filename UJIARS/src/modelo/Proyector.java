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
    private int numRespuestas;
    private int numAlumnos = 0;

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
        numAlumnos++;
        Platform.runLater(() -> homeProyectorController.setAlumno(nombreAlumno));
    }

    @Override
    public void verPregunta(String enunciado, List<String> respuestas) throws RemoteException{
        System.out.println("Enunciado: " + enunciado);
        numRespuestas = 0;
        Platform.runLater(() -> cuestionarioEnProcesoController.setNumRespuestas(0, numAlumnos));
        Platform.runLater(() -> cuestionarioEnProcesoController.setPreguntas(enunciado, respuestas));
    }

    @Override
    public void verResultados(Map<String, Integer> resultados) throws RemoteException {
        // TODO Mostrar los resultados de una pregunta
    }

    @Override
    public void setTimer(String tiempo) throws RemoteException {
        Platform.runLater(() -> cuestionarioEnProcesoController.setTimer(tiempo));
    }

    @Override
    public void alumnoResponde() throws RemoteException {
        numRespuestas++;
        Platform.runLater(() -> cuestionarioEnProcesoController.setNumRespuestas(numRespuestas, numAlumnos));
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
