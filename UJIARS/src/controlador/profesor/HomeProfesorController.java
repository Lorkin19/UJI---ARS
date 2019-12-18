package controlador.profesor;

import common.IProfesor;
import controlador.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Pregunta;
import modelo.Profesor;
import modelo.Sesion;
import vista.Main;

import java.io.IOException;

public class HomeProfesorController implements IController {

    private Main main;
    private Profesor profesor;

    @FXML
    public TableView<Sesion> tablaCuestionarios;
    @FXML
    public TableColumn<Sesion, String> columnaCuestionario;

    @FXML
    public TableView<Pregunta> tablaPreguntas;
    @FXML
    public TableColumn<Pregunta, String> columnaPreguntas;
    @FXML
    public TableColumn<Pregunta, String> columnaRespuestas;
    private Stage myStage;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }
    @Override
    public void setMyStage(Stage myStage) {
        this.myStage=myStage;
    }


    public void setProfesor(Profesor profesor) {
        this.profesor=profesor;
        tablaCuestionarios.setItems(profesor.getMisSesiones());
        tablaPreguntas.setItems(null);
    }

    /**
     * Se inicializa por defecto y carga los nombres de todos los cuestionarios
     * que tiene el profesor en la tabla de cuestionarios.
     */
    @FXML
    public void initialize() {
        // Inicializa las tablas.
        columnaCuestionario.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        muestraPreguntas(null);
        columnaRespuestas.setCellValueFactory(null);
        tablaCuestionarios.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> muestraPreguntas(newValue));
    }

    /**
     * Lanza una nueva ventana en la que crea un nuevo cuestionario.
     */
    @FXML
    private void creaCuestionario(){
        main.creaCuestionario();
    }

    /**
     * Muestra las preguntas del cuestionario seleccionado en la tabla de preguntas.
     *
     * @param cuestionario  El cuestionario que se ha seleccionado en la tabla.
     */
    @FXML
    private void muestraPreguntas(Sesion cuestionario){
        if (cuestionario == null) {
            columnaPreguntas.setCellValueFactory(null);
        } else
            columnaPreguntas.setCellValueFactory(cellData -> cellData.getValue().getEnunciado());
    }

    public void borraCuestionario() {
        String cuestionario = tablaCuestionarios.getSelectionModel().selectedItemProperty().toString();
        main.borraCuestionario(cuestionario);
    }

    public void cerrarSesion() {
        main.cierraSesion();
    }
}
