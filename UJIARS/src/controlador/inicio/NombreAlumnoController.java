package controlador.inicio;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vista.Main;

public class NombreAlumnoController implements IController {

    private Main main;
    private Stage myStage;
    private LandingPageController prevController;

    @FXML
    public TextField nombreAlumno;


    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setPrevController(LandingPageController landingPageController) {
        this.prevController = landingPageController;
    }


    public void entrarEnSala() {
        if (nombreAlumno.getText().length() > 15) {
            main.error("El nombre debe tener menos de 15 caracteres");
            return;
        }
        if (prevController.entraEnSala(nombreAlumno.getText())) {
            System.out.println("(NombreAlumnoController) Nombre de alumno disponible");
            prevController.cierraInicio();
            main.ejecutaSalaEspera();
        } else {
            main.error("Ya existe un alumno en la sala con ese nombre.\nIntroduce otro nombre distinto.");
        }
    }


}
