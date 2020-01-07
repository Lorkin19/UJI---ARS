package controlador.profesor;

import controlador.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public Button borra;
    private Main main;
    private Stage myStage;
    private Stage modalPregunta;
    private Pregunta preguntaSeleccionada;
    private ObservableList<Pregunta> preguntas;

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


    /**
     * Se ejecuta automaticamente al iniciar la vista de creacion de cuestionario.
     *
     * Se establecen los botones correspondientes como deshabilitados y se prepara
     * la vista de la tabla de las preguntas.
     */
    @FXML
    public void initialize() {
        edita.setDisable(true);
        borra.setDisable(true);
        //crear.setDisable(true);
        preguntas = FXCollections.observableArrayList();
        // Inicializa las tablas.
        tablaPreguntas.setItems(preguntas);
        columnaPreguntas.setCellValueFactory(cellData -> cellData.getValue().getEnunciado());
        tablaPreguntas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccion(newValue));
    }

    /**
     * Guarda la pregunta que se ha seleccionado en la tabla y habilita los botones de
     * edicion y borrado de pregunta.
     * @param pregunta  Pregunta seleccionada de la tabla de preguntas del cuestionario.
     */
    private void seleccion(Pregunta pregunta) {
        this.preguntaSeleccionada = pregunta;
        edita.setDisable(false);
        borra.setDisable(false);
    }


    /**
     * Comprueba que el cuestionario tiene nombre y que tiene al menos 1 pregunta.
     * Si cumple las condiciones, crea el cuestionario. En caso contrario, lanza una
     * ventana de error indicando las condiciones de creacion de cuestionarios.
     */
    public void addCuestionario() {
        if (preguntas.size() == 0 || nombreCuestionario.getText().equals("")){
            main.error("Todo cuestionario debe tener nombre y un mínimo de 1 pregunta.");
        } else {
            main.addCuestionario(nombreCuestionario.getText(), preguntas);
        }
    }

    /**
     * Gestiona el logout de un profesor
     */
    public void cerrarSesion() {
        main.cierraSesion();
    }

    /**
     * Lanza la ventana de gestion de una pregunta.
     */
    public void nuevaPregunta() {
        ventanaEdicionPregunta();
        modalPregunta.showAndWait();
        modalPregunta.close();
    }

    private CreaPreguntaController ventanaEdicionPregunta() {
        try {
            FXMLLoader registroLoader = new FXMLLoader();
            registroLoader.setLocation(getClass().getResource("../../vista/profesor/creaPregunta.fxml"));

            modalPregunta = new Stage();
            modalPregunta.setTitle("Crear Pregunta");
            modalPregunta.initModality(Modality.WINDOW_MODAL);
            modalPregunta.initStyle(StageStyle.UTILITY);
            modalPregunta.initOwner(myStage);

            Scene scene = new Scene(registroLoader.load());
            modalPregunta.setScene(scene);
            modalPregunta.setResizable(false);

            CreaPreguntaController controller = registroLoader.getController();
            controller.setMain(this.main);
            controller.setMyStage(modalPregunta);
            controller.setPrevController(this);

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Lanza la ventana de edicion de una pregunta
     */
    public void editaPregunta() {
        CreaPreguntaController preguntaController = ventanaEdicionPregunta();
        preguntaController.setPregunta(preguntaSeleccionada);
        modalPregunta.showAndWait();
        modalPregunta.close();
    }

    /**
     * Cancela la creacion de un nuevo cuestionario y vuelve a la vista
     * principal del profesor.
     */
    public void cancelar() {
        main.ejecutaProfesorActual();
    }

    /**
     * Anyade una nueva pregunta a la lista provisional de preguntas
     * del nuevo cuestionario.
     * @param pregunta  Pregunta que se anyade.
     */
    public void anyadePregunta(Pregunta pregunta) {
        for (Pregunta preg : preguntas) {
            if (preg.getEnunciado().get().equals(pregunta.getEnunciado().get())){
                preguntas.remove(preg);
                break;
            }
        }
        preguntas.add(pregunta);
    }

    public void borra() {
        preguntas.remove(preguntaSeleccionada);
        borra.setDisable(true);
        edita.setDisable(true);
    }
}
