package controlador.alumno;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vista.Main;

public class ZonaResultadoPreguntaController implements IController {

    @FXML
    public AnchorPane background;
    @FXML
    public Label textoResultado;
    @FXML
    public Label textoExtra;
    @FXML
    public ImageView gif;

    private Main main;
    private Stage myStage;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setResultado(boolean acierto) {
        if(acierto) {
            textoResultado.setText("¡Correcto!");
            textoExtra.setText("Punto pa ti");
            background.setStyle("-fx-background-color: #61BB46");
            gif.setImage(new Image(getClass().getResourceAsStream("../../vista/style/acierto.gif")));
        } else {
            textoResultado.setText("¡Incorrecto!");
            textoExtra.setText("Haber estudiao");
            background.setStyle("-fx-background-color: #E03A3E");
            gif.setImage(new Image(getClass().getResourceAsStream("../../vista/style/incorrecto.gif")));
        }
        gif.setFitWidth(400);
        gif.setFitHeight(300);
    }
}
