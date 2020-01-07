package controlador.proyector;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import vista.Main;

import java.util.*;
import java.util.stream.Collectors;

public class RankingController implements IController {

    private Main main;
    private Stage myStage;

    @FXML
    public Label texto;
    @FXML
    public Label posicion1;
    @FXML
    public Label nombre1;
    @FXML
    public Label puntuacion1;
    @FXML
    public Label posicion2;
    @FXML
    public Label nombre2;
    @FXML
    public Label puntuacion2;
    @FXML
    public Label posicion3;
    @FXML
    public Label nombre3;
    @FXML
    public Label puntuacion3;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setRanking(Map<String, Integer> listaAlumnos) {


        Map<String, Integer> sortedByCount = listaAlumnos.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<String> topNombresOrdenado = new ArrayList<>(sortedByCount.keySet());

        System.out.println("Imprimiendo topNombresOrdenado");
        System.out.println(topNombresOrdenado);

        int cantAlumnos = topNombresOrdenado.size();

        String nom1, nom2, nom3;
        switch (cantAlumnos){
            case 0:
                return;
            case 1:
                System.out.println("Solo hay un alumno");
                nom1=topNombresOrdenado.get(0);
                nombre1.setText(nom1);
                puntuacion1.setText(listaAlumnos.get(nom1).toString());
                posicion2.setText("");
                posicion3.setText("");
                break;
            case 2:
                System.out.println("Solo hay dos alumnos");
                nom1=topNombresOrdenado.get(0);
                nombre1.setText(nom1);
                puntuacion1.setText(listaAlumnos.get(nom1).toString());
                nom2 = topNombresOrdenado.get(1);
                nombre2.setText(nom2);
                puntuacion2.setText(listaAlumnos.get(nom2).toString());
                posicion3.setText("");
                break;
            default:
                System.out.println("Hay tres o mas alumnos");
                nom1=topNombresOrdenado.get(0);
                nombre1.setText(nom1);
                puntuacion1.setText(listaAlumnos.get(nom1).toString());
                nom2 = topNombresOrdenado.get(1);
                nombre2.setText(nom2);
                puntuacion2.setText(listaAlumnos.get(nom2).toString());
                nom3 = topNombresOrdenado.get(2);
                nombre3.setText(nom3);
                puntuacion3.setText(listaAlumnos.get(nom3).toString());
                break;
        }

    }

    private void getTop3(Map<String, Integer> listaAlumnos, String[] top) {

    }

    public void finPartida() {
        texto.setText("Ranking final");
    }
}
