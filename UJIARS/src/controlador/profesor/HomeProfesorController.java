package controlador.profesor;

import controlador.IController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.Sesion;
import vista.Main;

public class HomeProfesorController implements IController {


    private Main main;

    @FXML
    public TableView<Sesion> tablaCuestionarios;
    @FXML
    public TableColumn<Sesion, String> columnaCuestionario;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void initialize() {
        // Inicializa las tablas.
        columnaCuestionario.setCellValueFactory(cellData -> cellData.getValue().getNombre());
    }
}
