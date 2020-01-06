package controlador.alumno;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Alumno;
import vista.Main;

import java.rmi.RemoteException;

public class ZonaRespondeController implements IController {

    @FXML
    public Label tiempo;
    @FXML
    public Label respuesta1;
    @FXML
    public Label respuesta2;
    @FXML
    public Label respuesta3;
    @FXML
    public Label respuesta4;


    private Main main;
    private Stage myStage;
    private Alumno alumno;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void responde1() {
        try {
            alumno.respondePregunta(respuesta1.getText());
            main.ejecutaSalaEspera("Esperando resultado...");
        } catch (RemoteException e) {
            main.error("Error: algo ha fallado al responder");
            e.printStackTrace();
        }
    }


    public void responde2() {
        try {
            alumno.respondePregunta(respuesta2.getText());
            main.ejecutaSalaEspera("Esperando resultado...");
        } catch (RemoteException e) {
            main.error("Error: algo ha fallado al responder");
            e.printStackTrace();
        }
    }


    public void responde3() {
        try {
            alumno.respondePregunta(respuesta3.getText());
            main.ejecutaSalaEspera("Esperando resultado...");
        } catch (RemoteException e) {
            main.error("Error: algo ha fallado al responder");
            e.printStackTrace();
        }
    }


    public void responde4() {
        try {
            alumno.respondePregunta(respuesta4.getText());
            main.ejecutaSalaEspera("Esperando resultado...");
        } catch (RemoteException e) {
            main.error("Error: algo ha fallado al responder");
            e.printStackTrace();
        }
    }

    public void setTimer(String timer) {
        if (Integer.parseInt(timer) <= 3){
            this.tiempo.setStyle("-fx-text-fill: white;-fx-background-color: red;-fx-background-radius: 20;");
        }
        this.tiempo.setText(timer);
    }
}
