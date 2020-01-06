package controlador.proyector;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import vista.Main;

public class HomeProyectorController implements IController {

    private Main main;
    private Stage myStage;

    @FXML
    public Label codigoSala;
    @FXML
    public Label numAlumnos;
    @FXML
    public GridPane gridPane;

    private int cantAlumnos;


    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public synchronized void setAlumno(String nombre){
        int[] posicion = getPosicion();
        cantAlumnos++;
        Label alumno = new Label(nombre);
        setLabelStyle(alumno);
        gridPane.add(alumno, posicion[1], posicion[0]);
        numAlumnos.setText(cantAlumnos + "/20");
    }

    private void setLabelStyle(Label alumno) {
        String style = "-fx-font-size: 16pt;" +
                "    -fx-font-family: \"Segoe UI Semibold\";" +
                "    -fx-text-fill: #1c1c1c;";
        alumno.setStyle(style);
    }

    private int[] getPosicion(){
        int[] posicion = new int[2];
        posicion[0] = cantAlumnos/4;
        posicion[1] = (int)(( (float)cantAlumnos/4 - posicion[0] )*4);
        return posicion;
    }

    public void setCodSala(int codSala) {
        codigoSala.setText("" + codSala);
    }
}
