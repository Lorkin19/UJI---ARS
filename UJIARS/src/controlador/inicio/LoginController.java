package controlador.inicio;

import common.IProfesor;
import controlador.IController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import vista.Main;

import java.awt.event.ActionEvent;

public class LoginController implements IController {

    public LandingPageController prevController;
    public TextField user;
    public PasswordField password;
    private Main main;

    @Override
    public void setMain(Main main) {
        this.main=main;
    }

    private void setPrevController(LandingPageController prevController){
        this.prevController = prevController;
    }

    public void iniciaSesion() {
        IProfesor profesor = main.iniciaSesion(user.getText(), password.getText());
        if (profesor == null){
            main.error("Usuario o clave incorrecta.");
        } else {
            main.ejecutaProfesor(profesor);
        }
    }
}
