package vista;

import common.IProfesor;
import common.IServidorInicio;
import controlador.inicio.LandingPageController;
import controlador.profesor.CreaCuestionarioContoller;
import controlador.profesor.HomeProfesorController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Pregunta;
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

    /**
     * Gestiona el registro del profesor en el servidor.
     *
     * @param usuario   nombre de usuario del profesor a registrar.
     * @param password  contrasenya del profesor a registrar.
     * @return  (false) si el nombre de usuario ya existe,
     *          (true)  si el profesor se ha registrado correctamente.
     */
    public boolean registraProfesor(String usuario, String password){
        try {
            return servidorInicio.registraProfesor(usuario, password);
        }catch (RemoteException re){
            error("Fallo en la conexión con la base de datos.\nIntentalo mas tarde");
        }
        return true;
    }


    /**
     * Inicia la sesion del profesor comprobando usuario y contrasenya. En caso de que
     * se inicie sesion correctamente, se crea un nuevo profesor con todos sus datos.
     *
     * @param usuario   nombre de usuario del profesor que inicia sesion
     * @param password  contrasenya del profesor que inicia sesion
     * @return  (profesor)  en caso de que el profesor se haya logeado correctamente.
     *          (null)      en caso de error en el inicio de sesion.
     */
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

    /**
     * Ejecuta la interfaz de creacion de nuevo cuestionario
     */
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

    /**
     * Gestiona la creacion de un nuevo cuestionario.
     * @param nombreCuestionario    nombre del cuestionario.
     * @param preguntas             Lista de preguntas del cuestionario.
     */
    public void addCuestionario(String nombreCuestionario, ObservableList<Pregunta> preguntas) {
        Sesion sesion = new Sesion(nombreCuestionario);
        sesion.setListaPreguntas(preguntas);
        profesor.getMisSesiones().add(sesion);
        // TODO almacenar los datos del nuevo cuestionario (sesion) en la base de datos.
        try {
            servidorInicio.profesorCreaCuestionario(profesor.getUsuario(), sesion);
        } catch (RemoteException e) {
            error("Ha ocurrido un error al crear el cuestionario.\nVuelve a intentarlo mas tarde.");
        }
        ejecutaProfesor(profesor);
    }

    /**
     * Gestiona el borrado de un cuestionario.
     * @param cuestionario  nombre del cuestionario a borrar.
     */
    public void borraCuestionario(String cuestionario) {
        // TODO borrar el cuestionario tanto de la lista del profesor como de la base de datos
    }

    /**
     * Gestiona el logout de un profesor.
     */
    public void cierraSesion() {
        try {
            servidorInicio.cerrarSesionProfesor(profesor.getUsuario());
            primaryStage.setScene(landingScene);
            primaryStage.setResizable(false);
        } catch (RemoteException e) {
            error("Error al cerrar sesion");
        }
    }

    /**
     * Ejecuta la ventana inicial del profesor que actualmente esta logeado.
     * Se utiliza para poder ejecutar directamente la ventana home del profesor
     * a la hora de cancelar la creacion de un nuevo cuestionario o cuando se
     * finaliza una sala.
     */
    public void ejecutaProfesorActual() {
        ejecutaProfesor(profesor);
    }

}
