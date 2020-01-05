package controlador.profesor;

import controlador.IController;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Profesor;
import modelo.Proyector;
import vista.Main;

import java.rmi.RemoteException;

public class GestionaSalaController implements IController {

    private Main main;
    private Stage myStage;
    private Profesor profesor;
    private Proyector proyector;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }
    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setProyector(Proyector proyector){
        this.proyector = proyector;
    }


    /**
     * Avisa al proyector para que muestre la primera pregunta.
     */
    public void empezarPartida() {
        main.empezarPartida();
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
        main.ejecutaProfesorActual();
    }

}
