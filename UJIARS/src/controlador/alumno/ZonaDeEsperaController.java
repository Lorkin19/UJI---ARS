package controlador.alumno;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.Alumno;
import vista.Main;

import java.rmi.RemoteException;

public class ZonaDeEsperaController implements IController {

    private Main main;
    private Stage myStage;
    private Alumno alumno;

    @FXML
    public Label nombreAlumno;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    @FXML
    public void setNombreAlumno(String nombreAlumno){
        this.nombreAlumno.setText(nombreAlumno);
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
