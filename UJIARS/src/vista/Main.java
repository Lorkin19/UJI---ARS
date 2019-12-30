package vista;

import common.IProfesor;
import common.IServidorInicio;
import controlador.inicio.LandingPageController;
import controlador.profesor.CreaCuestionarioContoller;
import controlador.profesor.HomeProfesorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Profesor;
import modelo.Sesion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main extends Application {

    private IServidorInicio servidorInicio = null;
    private Profesor profesor = null;
    private Stage primaryStage;
    private Scene landingScene;
    private Scene profesorScene;

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
        this.primaryStage.initStyle(StageStyle.DECORATED);
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("UJI ARS");

        // Cargamos la escena.
        landingScene = new Scene(landingPage.load());

        // Anyadimos la escena a la ventana de inicio.
        this.primaryStage.setScene(landingScene);

        // Pasamos la referencia del main al controlador.
        LandingPageController landingController = landingPage.getController();
        landingController.setMain(this);
        landingController.setMyStage(this.primaryStage);

        // Mostramos la ventana.
        this.primaryStage.show();
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


    public Profesor iniciaSesion(String usuario, String password){
        try {
            if (servidorInicio.iniciaProfesor(usuario, password)) {
                profesor = new Profesor(usuario, password);
                servidorInicio.profesorIniciaSesion(usuario, (IProfesor) profesor);
                return  profesor;
            }
            return null;
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
    public void ejecutaProfesor(Profesor profesor) {
        try {

            this.profesor = profesor;

            // Creamos el loader del profesor
            FXMLLoader profesorLoader = new FXMLLoader();
            profesorLoader.setLocation(getClass().getResource("profesor/homeProfesor.fxml"));


            primaryStage.setTitle("UJI ARS - Profesor");
            primaryStage.setResizable(true);
            //primaryStage.initStyle(StageStyle.DECORATED);

            profesorScene = new Scene(profesorLoader.load());
            primaryStage.setScene(profesorScene);

            HomeProfesorController controller = profesorLoader.getController();
            controller.setMain(this);
            controller.setMyStage(primaryStage);
            controller.setProfesor(profesor);


        } catch (IOException e){
            String mensaje = "Error al ejecutar la ventana del profesor.";
            System.out.println(mensaje);
            error(mensaje);
        }

    }

    public void profesorCreaCuestionario(){
        try {
            FXMLLoader creaCuestionarioLoader = new FXMLLoader();
            creaCuestionarioLoader.setLocation(getClass().getResource("profesor/creaCuestionario.fxml"));

            primaryStage.setTitle("UJI ARS - Profesor - Crear cuestionario");
            primaryStage.setResizable(true);

            Scene scene = new Scene(creaCuestionarioLoader.load());
            primaryStage.setScene(scene);

            CreaCuestionarioContoller controller = creaCuestionarioLoader.getController();
            controller.setMain(this);
            controller.setMyStage(primaryStage);

        } catch (IOException e) {
            error("No se ha podido crear el cuestionario.");
        }
    }

    public void addCuestionario(String nombreCuestionario) {
        profesor.getMisSesiones().add(new Sesion(nombreCuestionario));
    }

    public void borraCuestionario(String cuestionario) {

    }

    public void cierraSesion() {
        try {
            servidorInicio.cerrarSesionProfesor(profesor.getUsuario());
            primaryStage.setScene(landingScene);
            primaryStage.setResizable(false);
        } catch (RemoteException e) {
            error("Error al cerrar sesion");
        }
    }

    public void ejecutaProfesorActual() {
        ejecutaProfesor(profesor);
    }
}
