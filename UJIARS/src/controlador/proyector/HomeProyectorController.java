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
    public Label nombreCuestionario;
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
        gridPane.add(alumno, posicion[0], posicion[1]);
        numAlumnos.setText(cantAlumnos + "/20");
    }

    private int[] getPosicion(){
        int[] posicion = new int[2];
        posicion[0] = cantAlumnos/4;
        posicion[1] = (int)(( (float)cantAlumnos/4 - posicion[0] )*4);
        return posicion;
    }

    public void setTituloCuestionario(String tituloCuestionario) {
        nombreCuestionario.setText("Cuestionario: " + tituloCuestionario);
    }
}
