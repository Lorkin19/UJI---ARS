package controlador.profesor;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Pregunta;
import vista.Main;

import java.io.IOException;

public class CreaCuestionarioContoller implements IController {

    public TableView<Pregunta> tablaPreguntas;
    public TableColumn<Pregunta, String> columnaPreguntas;
    public Button edita;
    public Button crear;
    private Main main;
    private Stage myStage;
    private Pregunta preguntaSeleccionada;

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


    @FXML
    public void initialize() {
        edita.setDisable(true);
        crear.setDisable(true);
        // Inicializa las tablas.
        columnaPreguntas.setCellValueFactory(cellData -> cellData.getValue().getEnunciado());
        tablaPreguntas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccion(newValue));
    }

    private void seleccion(Pregunta pregunta) {
        this.preguntaSeleccionada = pregunta;
        edita.setDisable(false);
    }


    public void addCuestionario() {
        main.addCuestionario(nombreCuestionario.getText());
    }

    public void cerrarSesion() {
        main.cierraSesion();
    }

    public void nuevaPregunta() {
        try {
            FXMLLoader registroLoader = new FXMLLoader();
            registroLoader.setLocation(getClass().getResource("../../vista/profesor/creaPregunta.fxml"));

            Stage modalCrearPregunta = new Stage();
            modalCrearPregunta.setTitle("Crear Pregunta");
            modalCrearPregunta.initModality(Modality.WINDOW_MODAL);
            modalCrearPregunta.initStyle(StageStyle.UTILITY);
            modalCrearPregunta.initOwner(myStage);

            Scene scene = new Scene(registroLoader.load());
            modalCrearPregunta.setScene(scene);
            modalCrearPregunta.setResizable(false);

            CreaPreguntaController controller = registroLoader.getController();
            controller.setMain(this.main);
            controller.setPrevController(this);

            modalCrearPregunta.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editaPregunta() {
    }

    public void cancelar() {
        main.ejecutaProfesorActual();
    }
}
