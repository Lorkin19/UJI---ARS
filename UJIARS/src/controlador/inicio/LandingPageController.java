package controlador.inicio;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vista.Main;

import java.io.IOException;

public class LandingPageController implements IController {

    private Main main;
    private Stage stageRegistro;
    private Stage stageInicio;

    @Override
    public void setMain(Main main) {
        this.main=main;
    }

    /**
     * Se prepara la ventana de inicio de sesion.
     */
    public void iniciaSesion() {
        try {
            FXMLLoader inicioLoader = new FXMLLoader();
            inicioLoader.setLocation(getClass().getResource("../../vista/inicio/iniciaSesion.fxml"));

            stageInicio = new Stage();
            stageInicio.setTitle("Inicia Sesión");
            stageInicio.initStyle(StageStyle.UTILITY);

            Scene scene = new Scene(inicioLoader.load());
            stageInicio.setScene(scene);
            stageInicio.setResizable(false);

            LoginController controller = inicioLoader.getController();
            controller.setMain(this.main);

            stageInicio.show();
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

            stageRegistro = new Stage();
            stageRegistro.setTitle("Registrate");
            stageRegistro.initStyle(StageStyle.UTILITY);

            Scene scene = new Scene(registroLoader.load());
            stageRegistro.setScene(scene);
            stageRegistro.setResizable(false);

            RegistraController controller = registroLoader.getController();
            controller.setMain(this.main);
            controller.setPrevController(this);

            stageRegistro.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cierraRegistro() {
        stageRegistro.close();
    }
    public void cierraInicio() { stageInicio.close();}
}
