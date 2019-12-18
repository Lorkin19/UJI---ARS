package vista;

import common.IProfesor;
import common.IServidorInicio;
import controlador.inicio.LandingPageController;
import controlador.profesor.HomeProfesorController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main extends Application {

    private IServidorInicio servidorInicio = null;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        // Obtenemos la interfaz del servidor para poder ejecutar los metodos necesarios.
        String registryURL = "rmi://localhost:1099/UJIARS";
        getServerFromUrl(registryURL);



        // Creamos el loader de la pagina inicial.
        FXMLLoader landingPage = new FXMLLoader();
        landingPage.setLocation(Main.class.getResource("inicio/landingPage.fxml"));

        // Ajustamos parametros de la ventana inicial.
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setResizable(false);
        primaryStage.setTitle("UJI ARS");

        // Cargamos la escena.
        Scene landingScene = new Scene(landingPage.load());

        // Anyadimos la escena a la ventana de inicio.
        primaryStage.setScene(landingScene);

        // Pasamos la referencia del main al controlador.
        LandingPageController landingController = landingPage.getController();
        landingController.setMain(this);

        // Mostramos la ventana.
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metodo que muestra un pop-up de error
     * @param mensaje   Mensaje de error a mostrar
     */
    public void error(String mensaje){
        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
        dialogoAlerta.setTitle("Error en el registro");
        dialogoAlerta.setHeaderText(null);
        dialogoAlerta.setContentText(mensaje);
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
    }

    public boolean registraProfesor(String usuario, String password){
        try {
            return servidorInicio.registraProfesor(usuario, password);
        }catch (RemoteException re){
            error("Fallo en la conexión con la base de datos.\nIntentalo mas tarde");
        }
        return true;
    }


    public IProfesor iniciaSesion(String usuario, String password){
        try {
            return servidorInicio.iniciaProfesor(usuario, password);
        } catch (RemoteException re){
            error("Fallo en la conexión con la base de datos.\nIntentalo mas tarde");
            return null;
        }
    }


    /**
     * Metodo con el cual se recibe la interfaz del servidor de inicio.
     *
     * @param registryURL   url en la que se ejecuta el servidor.
     */
    private void getServerFromUrl(String registryURL) {
        try {
            this.servidorInicio = (IServidorInicio) Naming.lookup(registryURL);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta la interfaz del profesor.
     */
    public void ejecutaProfesor(IProfesor intProfesor) {
        try {
            // Creamos el loader del profesor
            FXMLLoader profesorLoader = new FXMLLoader();
            profesorLoader.setLocation(getClass().getResource("profesor/homeProfesor.fxml"));

            // Cambiamos el titulo de la ventana.
            primaryStage.setTitle("UJI ARS - Profesor");

            Scene profesor = new Scene(profesorLoader.load());
            primaryStage.setScene(profesor);

            HomeProfesorController controller = profesorLoader.getController();
            controller.setMain(this);

        } catch (IOException e){
            String mensaje = "Error al ejecutar la ventana del profesor.";
            System.out.println(mensaje);
            error(mensaje);
        }

    }
}
