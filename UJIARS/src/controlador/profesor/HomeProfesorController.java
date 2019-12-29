package controlador.profesor;

import common.IProfesor;
import controlador.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Pregunta;
import modelo.Profesor;
import modelo.Sesion;
import vista.Main;

import java.io.IOException;

public class HomeProfesorController implements IController {

    public Button editar;
    public Button borrar;

    private Main main;
    private Profesor profesor;
    private Sesion sesionSeleccionada;
    private Stage stageCreaCuestionario;

    @FXML
    public TableView<Sesion> tablaCuestionarios;
    @FXML
    public TableColumn<Sesion, String> columnaCuestionario;

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
    }

    /**
     * Se inicializa por defecto y carga los nombres de todos los cuestionarios
     * que tiene el profesor en la tabla de cuestionarios.
     */
    @FXML
    public void initialize() {
        // Botones de edicion y borrado de cuestionario desactivados
        editar.setDisable(true);
        borrar.setDisable(true);
        // Inicializa las tablas.
        columnaCuestionario.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        tablaCuestionarios.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccion(newValue));
    }

    private void seleccion(Sesion sesion){
        this.sesionSeleccionada = sesion;
        editar.setDisable(false);
        borrar.setDisable(false);
    }

    /**
     * Lanza una nueva ventana en la que crea un nuevo cuestionario.
     */
    @FXML
    private void creaCuestionario(){
        main.profesorCreaCuestionario();
    }


    public void borraCuestionario() {
        String cuestionario = tablaCuestionarios.getSelectionModel().selectedItemProperty().toString();
        main.borraCuestionario(cuestionario);
    }

    public void cerrarSesion() {
        main.cierraSesion();
    }

    public void editaCuestionario(ActionEvent actionEvent) {

    }

    public void cierraCreaCuestionario() {
        stageCreaCuestionario.close();
    }
}
