package controlador.profesor;

import controlador.IController;
import vista.Main;

public class HomeProfesorController implements IController {

    private Main main;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }
}
