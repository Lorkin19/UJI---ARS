package controlador.inicio;

import common.IProfesor;
import controlador.IController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Profesor;
import vista.Main;

public class RegistraController implements IController {

    private Main main;
    public LandingPageController prevController;

    public TextField user;
    public PasswordField pass1;
    public PasswordField pass2;
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

    public void registro() {
        if (user.getText().equals("") || user.getText().length() > 20
                || pass1.getText().equals("") || pass1.getText().length() > 20
                || pass2.getText().equals("") || pass2.getText().length() > 20) {
            main.error("Debes rellenar todos los campos con un máximo de 20 caracteres.");
            return;
        }
        if (!pass1.getText().equals(pass2.getText())){
            main.error("Las claves no coinciden.");
        }
        else {
            if (main.registraProfesor(user.getText(),pass1.getText())){
                prevController.cierraInicio();
                Profesor profesor = main.iniciaSesion(user.getText(), pass1.getText());
                main.ejecutaProfesor(profesor);
            }
            else{
                main.error("El usuario ya existe o ha habido un error con la base de datos.");
            }
        }

    }

}
