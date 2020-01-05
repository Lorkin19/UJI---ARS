package controlador.profesor;

import controlador.IController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modelo.Profesor;
import modelo.Proyector;
import modelo.Sesion;
import vista.Main;

import java.rmi.RemoteException;


public class HomeProfesorController implements IController {

    public Button editar;
    public Button borrar;

    private Main main;
    private Profesor profesor;
    private Sesion sesionSeleccionada;

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
        if (!profesor.getMisSesiones().isEmpty()){
            tablaCuestionarios.setItems(profesor.getMisSesiones());
        }
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

    /**
     * Configura los botones para activarlos y guarda la sesion seleccionada
     * para futuras comprobaciones.
     *
     * @param sesion Sesion seleccionada en la tabla de cuestionarios del profesor.
     */
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
        editar.setDisable(true);
        borrar.setDisable(true);
    }


    /**
     * borra el cuestionario seleccionada.
     */
    public void borraCuestionario() {
        String cuestionario = tablaCuestionarios.getSelectionModel().selectedItemProperty().toString();
        main.borraCuestionario(cuestionario);
    }

    /**
     * Gestiona el cierre de sesion del profesor.
     */
    public void cerrarSesion() {
        main.cierraSesion();
    }

    /**
     * Edita el cuestionario seleccionado.
     */
    public void editaCuestionario() {
        // TODO gestionar la edicion de cuestionarios.
    }

    /**
     * Ejecuta el cuestionario seleccionado.
     */
    public void ejecutaCuestionario() {
        // TODO gestionar la ejecucion de cuestionarios.
        main.crearSala(sesionSeleccionada.getNombre().get());
    }

}
