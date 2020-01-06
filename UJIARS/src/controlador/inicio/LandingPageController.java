package controlador.inicio;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vista.Main;

import java.io.IOException;

public class LandingPageController implements IController {

    private Main main;
    private Stage stageSesion;
    private Stage myStage;

    @FXML
    public TextField codSala;


    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage stage) {
        this.myStage = stage;
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
            stageSesion.initModality(Modality.WINDOW_MODAL);
            stageSesion.initStyle(StageStyle.UTILITY);
            stageSesion.initOwner(myStage);

            Scene scene = new Scene(inicioLoader.load());
            stageSesion.setScene(scene);
            stageSesion.setResizable(false);

            LoginController controller = inicioLoader.getController();
            controller.setMain(this.main);
            controller.setPrevController(this);

            stageSesion.showAndWait();
        } catch (IOException e) {
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
            stageSesion.initModality(Modality.WINDOW_MODAL);
            stageSesion.initStyle(StageStyle.UTILITY);
            stageSesion.initOwner(myStage);

            Scene scene = new Scene(registroLoader.load());
            stageSesion.setScene(scene);
            stageSesion.setResizable(false);

            RegistraController controller = registroLoader.getController();
            controller.setMain(this.main);
            controller.setPrevController(this);

            stageSesion.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra la ventana de registro o inicio de sesion (Dependiendo de cual este abierta).
     */
    public void cierraInicio() {
        stageSesion.close();
    }


    /**
     * Comprueba que la sala existe.
     * Si la sala existe, lanza la ventana para introducir el nombre del alumno.
     * Si no existe, informa al alumno de que ha introducido mal el codigo o la sala no existe.
     */
    public void compruebaSala() {
        int codigoSala = -1;
        try {
            codigoSala = Integer.parseInt(codSala.getText());
        } catch (Exception e){
            System.out.println("Codigo invalido");
            main.error("Codigo de sala invalido");
            return;
        }
        if (main.compruebaSala(codigoSala)) {
            introduceNombreAlumno();
        } else {
            main.error("Error al entrar en la sala:\nEl codigo es incorrecto o la sala no existe");
        }
    }

    /**
     * Lanza la ventana para introducir el nombre del alumno y entrar en la sala.
     */
    public void introduceNombreAlumno() {
        try {
            FXMLLoader nombreLoader = new FXMLLoader();
            nombreLoader.setLocation(getClass().getResource("../../vista/inicio/nombreAlumno.fxml"));

            stageSesion = new Stage();
            stageSesion.setTitle("Nombre del alumno");
            stageSesion.initModality(Modality.WINDOW_MODAL);
            stageSesion.initStyle(StageStyle.UTILITY);
            stageSesion.initOwner(myStage);

            Scene scene = new Scene(nombreLoader.load());
            stageSesion.setScene(scene);
            stageSesion.setResizable(false);

            NombreAlumnoController controller = nombreLoader.getController();
            controller.setMain(this.main);
            controller.setPrevController(this);

            stageSesion.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean entraEnSala(String nombreAlumno){
        return main.registraAlumnoEnSala(nombreAlumno, Integer.parseInt(codSala.getText()));
    }
}
