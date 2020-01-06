package controlador.alumno;

import controlador.IController;
import javafx.stage.Stage;
import modelo.Alumno;
import vista.Main;

public class ZonaDeEsperaController implements IController {

    private Main main;
    private Stage myStage;
    private Alumno alumno;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
