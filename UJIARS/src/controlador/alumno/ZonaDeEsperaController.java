package controlador.alumno;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import vista.Main;


public class ZonaDeEsperaController implements IController {

    private Main main;
    private Stage myStage;

    @FXML
    public Label textoDeEspera;

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

}
