package controlador.profesor;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vista.Main;

public class CreaCuestionarioContoller implements IController {

    private Main main;
    private Stage myStage;

    @FXML
    public TextField nombreCuestionario;


    @Override
    public void setMain(Main main) {
        this.main = main;
    }
    @Override
    public void setMyStage(Stage myStage) {
        this.myStage=myStage;
    }



    public void addCuestionario() {
        main.addCuestionario(nombreCuestionario.getText(), myStage);
    }
}
