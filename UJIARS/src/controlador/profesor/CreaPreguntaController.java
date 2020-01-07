package controlador.profesor;

import controlador.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import modelo.FactoryPregunta;
import modelo.Pregunta;
import modelo.Respuesta;
import vista.Main;

import java.util.ArrayList;
import java.util.List;

public class CreaPreguntaController implements IController {


    private Main main;
    private Stage myStage;
    private int tiempoDoble=15;
    private int puntosDobles=1;

    @FXML
    public RadioButton doblePuntuacion;
    @FXML
    public RadioButton dobleTiempo;
    @FXML
    public TextArea enunciado;
    @FXML
    public TextArea respuesta1;
    @FXML
    public TextArea respuesta2;
    @FXML
    public TextArea respuesta3;
    @FXML
    public TextArea respuesta4;
    @FXML
    public RadioButton correcta1;
    @FXML
    public RadioButton correcta2;
    @FXML
    public RadioButton correcta3;
    @FXML
    public RadioButton correcta4;


    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setPrevController(CreaCuestionarioContoller prevContoller) {
        this.prevController = prevContoller;
    }

    private CreaCuestionarioContoller prevController;
    private FactoryPregunta factoryPregunta = FactoryPregunta.getInstance();


    /**
     * Recoge los datos de la pregunta junto con sus respuestas.
     *
     * Una pregunta puede no tener enunciado y sus respuestas pueden no tener texto.
     *
     * Se aceptan tanto varias como ninguna respuesta correcta.
     */
    public void crearPregunta() {
        Pregunta pregunta;
        if (doblePuntuacion.isSelected() && dobleTiempo.isSelected()) {
            pregunta = factoryPregunta.crearPreguntaDecorada(puntosDobles, tiempoDoble);
        } else {
            if (dobleTiempo.isSelected()) {
                pregunta = factoryPregunta.crearPreguntaDecorada(0, tiempoDoble);
            } else {
                if (doblePuntuacion.isSelected()) {
                    pregunta = factoryPregunta.crearPreguntaDecorada(puntosDobles, 0);
                } else {
                    pregunta = factoryPregunta.crearPregunta();
                }
            }
        }

        pregunta.setEnunciado(enunciado.getText());
        Respuesta aRespuesta = new Respuesta(respuesta1.getText(),correcta1.isSelected());
        Respuesta bRespuesta = new Respuesta(respuesta2.getText(),correcta2.isSelected());
        Respuesta cRespuesta = new Respuesta(respuesta3.getText(),correcta3.isSelected());
        Respuesta dRespuesta = new Respuesta(respuesta4.getText(),correcta4.isSelected());
        List<Respuesta> respuestas = new ArrayList<>();
        respuestas.add(aRespuesta);
        respuestas.add(bRespuesta);
        respuestas.add(cRespuesta);
        respuestas.add(dRespuesta);
        pregunta.setRespuestas(respuestas);
        prevController.anyadePregunta(pregunta);
        myStage.close();


    }


}
