package controlador.proyector;

import controlador.IController;
import javafx.stage.Stage;
import vista.Main;

public class ResultadoFinalController implements IController {

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

    

}
