package controlador.proyector;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class HomeProyectorController {

    @FXML
    public Label nombreCuestionario;
    @FXML
    public Label numAlumnos;
    @FXML
    public GridPane gridPane;

    private int cantAlumnos;


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

}
