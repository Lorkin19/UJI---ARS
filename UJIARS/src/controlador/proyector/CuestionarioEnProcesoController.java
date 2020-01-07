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
    @FXML
    public Label numR1;
    @FXML
    public Label numR2;
    @FXML
    public Label numR3;
    @FXML
    public Label numR4;



    public void setPreguntas(String enunciado, List<String> respuestas) {
        setStyleBasico();
        pregunta.setText(enunciado);
        respuesta1.setText(respuestas.get(0));
        respuesta2.setText(respuestas.get(1));
        respuesta3.setText(respuestas.get(2));
        respuesta4.setText(respuestas.get(3));
    }

    private void setStyleBasico() {
        // Quitamos el estilo del contador de respuestas
        String basicNumStyle = "-fx-padding: 0 0 0 0; -fx-background-radius: 10;-fx-background-color: transparent;-fx-font-size: 11";
        numR1.setText("");
        numR1.setStyle(basicNumStyle);
        numR2.setText("");
        numR2.setStyle(basicNumStyle);
        numR3.setText("");
        numR3.setStyle(basicNumStyle);
        numR4.setText("");
        numR4.setStyle(basicNumStyle);

        // Ponemos el estilo basico de las respuestas
        respuesta1.setStyle("-fx-background-color: #e03a3e");
        respuesta2.setStyle("-fx-background-color: #963d97");
        respuesta3.setStyle("-fx-background-color: #009ddc");
        respuesta4.setStyle("-fx-background-color: #f5821f");

        // Ponemos el contador de tiempo normal por si esta en zona roja
        tiempo.setStyle("-fx-text-fill: #2f2f2f;-fx-background-color: transparent;");

    }

    public void setTimer(String tiempo) {
        if (Integer.parseInt(tiempo) <= 3){
            this.tiempo.setStyle("-fx-text-fill: white;-fx-background-color: #e03a3e;-fx-background-radius: 20;");
        }
        this.tiempo.setText("Tiempo: " + tiempo);
    }

    public void setNumRespuestas(int numRespuestas, int alumnosTotales) {
        this.numRespuestas.setText(numRespuestas + "/" + alumnosTotales);
    }

    public void setRespuestasCorrectas(List<Boolean> correctas, List<Integer> cantRespuestas) {
        setBackground(respuesta1, correctas.get(0));
        setBackground(respuesta2, correctas.get(1));
        setBackground(respuesta3, correctas.get(2));
        setBackground(respuesta4, correctas.get(3));

        setStyleNumRespuestas(numR1);
        setStyleNumRespuestas(numR2);
        setStyleNumRespuestas(numR3);
        setStyleNumRespuestas(numR4);

        numR1.setText(cantRespuestas.get(0).toString());
        numR2.setText(cantRespuestas.get(1).toString());
        numR3.setText(cantRespuestas.get(2).toString());
        numR4.setText(cantRespuestas.get(3).toString());


    }

    private void setBackground(Label respuesta, Boolean correcta){
        if (correcta) {
            respuesta.setStyle("-fx-background-color: #61bb46");
        } else {
            respuesta.setStyle("-fx-background-color: #e03a3e");
        }
    }

    public void setStyleNumRespuestas(Label numRespuesta){
        numRespuesta.setStyle("-fx-padding: 5 5 5 5; -fx-background-radius: 10;-fx-background-color: #2B2B2B;-fx-font-size: 24");
    }
}
