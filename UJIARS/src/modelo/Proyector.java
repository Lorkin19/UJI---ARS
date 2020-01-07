package modelo;

import common.IProyector;
import controlador.proyector.CuestionarioEnProcesoController;
import controlador.proyector.HomeProyectorController;
import controlador.proyector.RankingController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vista.Main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class Proyector extends UnicastRemoteObject implements IProyector {

    private Main main;
    private Stage stageProyector;
    private Scene cuestionarioScene;
    private Scene rankingScene;
    private HomeProyectorController homeProyectorController;
    private CuestionarioEnProcesoController cuestionarioEnProcesoController;
    private RankingController rankingController;
    private int numRespuestas;
    private int numAlumnos = 0;

    public Proyector() throws RemoteException {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setHomeProyectorController(HomeProyectorController homeProyectorController) {
        this.homeProyectorController = homeProyectorController;
    }

    public void setCuestionarioEnProcesoController(CuestionarioEnProcesoController cuestionarioEnProcesoController) {
        this.cuestionarioEnProcesoController = cuestionarioEnProcesoController;
    }

    public void setRankingController(RankingController rankingController) {
        this.rankingController = rankingController;
    }

    public void setCuestionarioScene(Scene cuestionarioScene) {
        this.cuestionarioScene = cuestionarioScene;
    }

    public void setRankingScene(Scene rankingScene) {
        this.rankingScene = rankingScene;
    }

    public void setStage(Stage stageProyector) {
        this.stageProyector = stageProyector;
    }


    /**
     * Anyade el nombre del alumno al proyector
     *
     * @param nombreAlumno Nombre del alumno
     * @throws RemoteException Si hay algun error con la conexion remota.
     */
    @Override
    public void anyadeAlumno(String nombreAlumno) throws RemoteException {
        numAlumnos++;
        Platform.runLater(() -> homeProyectorController.setAlumno(nombreAlumno));
    }

    /**
     * Muestra la pregunta en el proyector junto con sus respuestas e inicializa el contador
     * de veces respondido a 0 pasando el numero de alumnos para tenerlo en cuenta.
     *
     * @param enunciado  Enunciado de la pregunta.
     * @param respuestas Listado con las respuestas.
     * @throws RemoteException Si hay algun error con la conexion remota.
     */
    @Override
    public void verPregunta(String enunciado, List<String> respuestas) throws RemoteException {
        System.out.println("Enunciado: " + enunciado);
        numRespuestas = 0;
        Platform.runLater(() -> preparaCuestionario(enunciado, respuestas));
    }

    private void preparaCuestionario(String enunciado, List<String> respuestas) {
        //stageProyector.setScene(cuestionarioScene);
        main.setScenaProyector(cuestionarioScene);
        cuestionarioEnProcesoController.setNumRespuestas(0, numAlumnos);
        cuestionarioEnProcesoController.setPreguntas(enunciado, respuestas);
    }

    /**
     * Muestra el ranking de alumnos por puntos.
     *
     * @param resultados Los puntos acumulados por cada alumno.
     * @throws RemoteException Si hay algun error con la conexion remota.
     */
    @Override
    public void verRanking(Map<String, Integer> resultados, Boolean finDePartida) throws RemoteException {
        Platform.runLater(() -> muestraRanking(resultados, finDePartida));
    }

    private void muestraRanking(Map<String, Integer> resultados, Boolean finPartida) {
        main.setScenaProyector(rankingScene);
        rankingController.setRanking(resultados);
        if (finPartida) {
            rankingController.finPartida();
        }
    }

    /**
     * Introduce el tiempo restante al contador.
     *
     * @param tiempo Tiempo restante para responder.
     * @throws RemoteException Si hay algun error con la conexion remota.
     */
    @Override
    public void setTimer(String tiempo) throws RemoteException {
        Platform.runLater(() -> cuestionarioEnProcesoController.setTimer(tiempo));
    }

    /**
     * Muestra el numero de respuestas realizadas en funcion con el numero de alumnos de la sala.
     *
     * @throws RemoteException Si hay algun error con la conexion remota.
     */
    @Override
    public void alumnoResponde() throws RemoteException {
        numRespuestas++;
        Platform.runLater(() -> cuestionarioEnProcesoController.setNumRespuestas(numRespuestas, numAlumnos));
    }

    /**
     * Muestra las preguntas correctas e incorrectas, junto con la cantidad de respuestas a cada pregunta.
     *
     * @param correctas      Lista de las respuestas correctas.
     * @param cantRespuestas Lista con la cantidad de respuestas a cada pregunta
     * @throws RemoteException Si hay algun problema con la conexion remota.
     */
    @Override
    public void muestraResultadoPregunta(List<Boolean> correctas, List<Integer> cantRespuestas) throws RemoteException {
        Platform.runLater(() -> cuestionarioEnProcesoController.setRespuestasCorrectas(correctas, cantRespuestas));
    }

}
