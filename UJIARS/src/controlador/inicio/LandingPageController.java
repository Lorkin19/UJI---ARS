package controlador.inicio;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import vista.Main;

import java.io.IOException;

public class LandingPageController implements IController {

    private Main main;
    private Stage stageSesion;
    private Stage myStage;

    @Override
    public void setMain(Main main) {
        this.main=main;
    }
    @Override
    public void setMyStage(Stage stage) {
        this.stageSesion = stage;
    }

    /**
     * Se prepara la ventana de inicio de sesion.
     */
    @FXML
    public void iniciaSesion() {
        try {
            FXMLLoader inicioLoader = new FXMLLoader();
            inicioLoader.setLocation(getClass().getResource("../../vista/inicio/iniciaSesion.fxml"));

            stageSesion = new Stage();
            stageSesion.setTitle("Inicia Sesión");
            stageSesion.initStyle(StageStyle.UTILITY);
            stageSesion.initOwner(myStage);

            Scene scene = new Scene(inicioLoader.load());
            stageSesion.setScene(scene);
            stageSesion.setResizable(false);

            LoginController controller = inicioLoader.getController();
            controller.setMain(this.main);
            controller.setPrevController(this);

            stageSesion.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Se prepara la ventana de registro de profesor.
     */
    @FXML
    public void registraProfesor() {
        try {
            FXMLLoader registroLoader = new FXMLLoader();
            registroLoader.setLocation(getClass().getResource("../../vista/inicio/registra.fxml"));

            stageSesion = new Stage();
            stageSesion.setTitle("Registrate");
            stageSesion.initStyle(StageStyle.UTILITY);
            stageSesion.initOwner(myStage);

            Scene scene = new Scene(registroLoader.load());
            stageSesion.setScene(scene);
            stageSesion.initModality(Modality.WINDOW_MODAL);
            stageSesion.initOwner(this.myStage);
            stageSesion.setResizable(false);

            RegistraController controller = registroLoader.getController();
            controller.setMain(this.main);
            controller.setPrevController(this);

            stageSesion.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cierraInicio() { stageSesion.close();}
}
