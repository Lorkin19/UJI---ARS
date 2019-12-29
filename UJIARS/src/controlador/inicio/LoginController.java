package controlador.inicio;

import common.IProfesor;
import controlador.IController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Profesor;
import vista.Main;

public class LoginController implements IController {

    public LandingPageController prevController;
    public TextField user;
    public PasswordField password;
    private Main main;
    private Stage myStage;

    @Override
    public void setMain(Main main) {
        this.main=main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage=myStage;
    }

    public void setPrevController(LandingPageController prevController){
        this.prevController = prevController;
    }

    public void iniciaSesion() {
        Profesor profesor = main.iniciaSesion(user.getText(), password.getText());
        if (profesor == null){
            main.error("Usuario o clave incorrecta.");
        } else {
            prevController.cierraInicio();
            main.ejecutaProfesor(profesor);
        }
    }
}
