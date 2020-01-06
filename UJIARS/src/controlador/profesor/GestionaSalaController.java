package controlador.profesor;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelo.Profesor;
import modelo.Proyector;
import vista.Main;

import java.rmi.RemoteException;


public class GestionaSalaController implements IController {

    private Main main;
    private Stage myStage;
    private Profesor profesor;

    @FXML
    public HBox zonaEmpiezaPartida;
    @FXML
    public Label pregunta;
    @FXML
    public Button botonSiguiente;


    @Override
    public void setMain(Main main) {
        this.main = main;
    }
    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setProfesor(Profesor profesor){
        this.profesor = profesor;
    }

    /**
     * Avisa al proyector para que muestre la primera pregunta.
     */
    public void empezarPartida() {
        zonaEmpiezaPartida.setDisable(true);
        botonSiguiente.setDisable(false);
        main.empezarPartida();
    }

    public void pasaPregunta() {
        try {
            profesor.pasarDePregunta();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Cierra la sesion del profesor
     */
    public void cerrarSesion() {
        main.cierraProyector();
        main.cierraSesion();
    }
    /**
     * Cancela el cuestionario y vuelve a la vista principal del profesor.
     */
    public void cancelar() {
        main.cierraProyector();
        try {
            profesor.finalizarPartida();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        main.ejecutaProfesorActual();
    }

}
