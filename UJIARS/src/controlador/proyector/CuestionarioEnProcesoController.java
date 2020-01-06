package controlador.proyector;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class CuestionarioEnProcesoController {

    @FXML
    public Label numRespuestas;
    @FXML
    public Label tiempo;
    @FXML
    public Label pregunta;
    @FXML
    public Label respuesta1;
    @FXML
    public Label respuesta2;
    @FXML
    public Label respuesta3;
    @FXML
    public Label respuesta4;



    public void setPreguntas(String enunciado, List<String> respuestas) {
        pregunta.setText(enunciado);
        respuesta1.setText(respuestas.get(0));
        respuesta2.setText(respuestas.get(1));
        respuesta3.setText(respuestas.get(2));
        respuesta4.setText(respuestas.get(3));
    }

    public void setTimer(String tiempo) {
        if (Integer.parseInt(tiempo) <= 3){
            this.tiempo.setStyle("-fx-text-fill: white;-fx-background-color: red;-fx-background-radius: 20;");
        }
        this.tiempo.setText("Tiempo: " + tiempo);
    }
}
