package vista;

import javafx.application.Platform;
import javafx.stage.Stage;

public class MainThreader {

    public static void main(String[] args) throws Exception {
        Main mainProfesor = new Main();
        Main mainAlumno1 = new Main();
        ThreadMain threadProfesor = new ThreadMain(mainProfesor);
        threadProfesor.start();
    }
}

class ThreadMain extends Thread {

    private Main main;

    public ThreadMain(Main main) {
        this.main = main;
    }

    @Override
    public void run(){
        Platform.runLater(() -> {
            try {
                main.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}