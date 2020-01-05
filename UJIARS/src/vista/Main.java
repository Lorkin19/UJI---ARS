package vista;

import common.IProfesor;
import common.IServidorInicio;
import controlador.inicio.LandingPageController;
import controlador.profesor.CreaCuestionarioContoller;
import controlador.profesor.GestionaSalaController;
import controlador.profesor.HomeProfesorController;
import controlador.proyector.CuestionarioEnProcesoController;
import controlador.proyector.HomeProyectorController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Main extends Application {

    private IServidorInicio servidorInicio = null;
    private Profesor profesor = null;
    private Proyector proyector = null;
    private Stage primaryStage;
    private Stage stageProyector;
    private Scene landingScene;
    private Scene profesorScene;
    private CuestionarioEnProcesoController proyectorController;

    @Override
    public void start(Stage primaryStage) throws Exception {

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
     *
     * @param mensaje Mensaje de error a mostrar
     */
    public void error(String mensaje) {
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
     * @param usuario  nombre de usuario del profesor a registrar.
     * @param password contrasenya del profesor a registrar.
     * @return (false) si el nombre de usuario ya existe,
     * (true)  si el profesor se ha registrado correctamente.
     */
    public boolean registraProfesor(String usuario, String password) {
        try {
            return servidorInicio.registraProfesor(usuario, password);
        } catch (RemoteException re) {
            error("Fallo en la conexión con la base de datos.\nIntentalo mas tarde");
        }
        return true;
    }


    /**
     * Inicia la sesion del profesor comprobando usuario y contrasenya. En caso de que
     * se inicie sesion correctamente, se crea un nuevo profesor con todos sus datos.
     *
     * @param usuario  nombre de usuario del profesor que inicia sesion
     * @param password contrasenya del profesor que inicia sesion
     * @return (profesor)  en caso de que el profesor se haya logeado correctamente.
     * (null)      en caso de error en el inicio de sesion.
     */
    public Profesor iniciaSesion(String usuario, String password) {
        try {
            boolean correcto = servidorInicio.iniciaProfesor(usuario, password);
            if (correcto) {
                profesor = new Profesor(usuario, password);
                servidorInicio.profesorIniciaSesion(usuario, (IProfesor) profesor);
                return profesor;
            }
            return null;
        } catch (RemoteException re) {
            error("Fallo en la conexión con la base de datos.\nIntentalo mas tarde");
            re.printStackTrace();
            return null;
        }
    }


    /**
     * Metodo con el cual se recibe la interfaz del servidor de inicio.
     *
     * @param registryURL url en la que se ejecuta el servidor.
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


        } catch (IOException e) {
            String mensaje = "Error al ejecutar la ventana del profesor.";
            System.out.println(mensaje);
            error(mensaje);
        }

    }

    /**
     * Ejecuta la interfaz de creacion de nuevo cuestionario
     */
    public void profesorCreaCuestionario() {
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
     *
     * @param nombreCuestionario nombre del cuestionario.
     * @param preguntas          Lista de preguntas del cuestionario.
     */
    public void addCuestionario(String nombreCuestionario, ObservableList<Pregunta> preguntas) {
        try {
            Sesion sesion = new Sesion(nombreCuestionario);
            sesion.setListaPreguntas(preguntas);
            profesor.addSesion(sesion);

            // Almacenamos en la bbdd el nuevo cuestionario.
            System.out.println("Almacenando el cuestionario nuevo en la bbdd");
            System.out.println("Nombre del cuestionario: " + sesion.getNombre().get());
            int idPregunta;
            for (Pregunta pregunta : preguntas) {
                idPregunta = servidorInicio.anyadePregunta(profesor.getUsuario(), nombreCuestionario, pregunta.getEnunciado().get());
                for (Respuesta respuesta : pregunta.getRespuestas()) {
                    servidorInicio.anyadeRespuesta(idPregunta, respuesta.getRespuesta(), respuesta.isCorrecta());
                }
            }

            // servidorInicio.profesorCreaCuestionario(profesor.getUsuario(), sesion);
            ejecutaProfesor(profesor);
        } catch (Exception e) {
            error("Ha ocurrido un error al crear el cuestionario.\nVuelve a intentarlo mas tarde.");
            e.printStackTrace();
        }
    }

    /**
     * Gestiona el borrado de un cuestionario.
     *
     * @param cuestionario nombre del cuestionario a borrar.
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

    public void crearSala(String nombreSesion) {
        try {
            proyector = profesor.crearPartida(nombreSesion, servidorInicio);
            proyector.setMain(this);
            ejecutaGestionaSala(proyector);
            ejecutaProyector(nombreSesion);

        } catch (RemoteException e) {
            System.out.println("Error al crear la sala");
            error("Error al crear la sala.\nIntentalo mas tarde.");
            e.printStackTrace();
        }
    }


    private void ejecutaGestionaSala(Proyector proyector) {
        try {
            FXMLLoader gestionaSalaLoader = new FXMLLoader();
            gestionaSalaLoader.setLocation(getClass().getResource("profesor/gestionaSala.fxml"));

            primaryStage.setTitle("UJI ARS - Profesor - Gestion de sala");
            primaryStage.setResizable(true);

            Scene scene = new Scene(gestionaSalaLoader.load());
            primaryStage.setScene(scene);

            GestionaSalaController controller = gestionaSalaLoader.getController();
            controller.setMain(this);
            controller.setMyStage(primaryStage);
            controller.setProyector(proyector);

        } catch (IOException e) {
            error("No se ha podido ejecutar el gestor de la sala.");
            e.printStackTrace();
        }
    }

    private void ejecutaProyector(String tituloCuestionario) throws RemoteException {
        try {
            stageProyector = new Stage();
            FXMLLoader homeProyectorLoader = new FXMLLoader();
            homeProyectorLoader.setLocation(getClass().getResource("proyector/proyectorHome.fxml"));

            stageProyector.setTitle("UJI ARS - Proyector");
            stageProyector.setResizable(true);

            Scene scene = new Scene(homeProyectorLoader.load());
            stageProyector.setScene(scene);
            HomeProyectorController homeController = homeProyectorLoader.getController();
            homeController.setMain(this);
            homeController.setMyStage(stageProyector);
            homeController.setTituloCuestionario(tituloCuestionario);

            stageProyector.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void empezarPartida() {
        try {
            empiezaCuestionario();
            profesor.empezarPartida();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta la escena del cuestionario para empezar con la primera pregunta.
     */
    private void empiezaCuestionario() {
        try {
            FXMLLoader cuestionarioLoader = new FXMLLoader();
            cuestionarioLoader.setLocation(getClass().getResource("proyector/cuestionarioEnProceso.fxml"));

            Scene scene = new Scene(cuestionarioLoader.load());
            stageProyector.setScene(scene);
            this.proyectorController = cuestionarioLoader.getController();
        } catch (IOException e) {
            System.out.println("Problema al empezar el cuestionario.");
            e.printStackTrace();
        }

    }

    public void cierraProyector() {
        stageProyector.close();
    }

    public void proyectorMuestraPregunta(String enunciado, List<String> respuestas) {
        proyectorController.setPreguntas(enunciado, respuestas);
    }

    /**
     * Busca si la sala existe o no
     *
     * @param codSala Codigo de la sala a la que se desea conectar.
     * @return Si la sala existe o no
     */
    public boolean compruebaSala(String codSala) {
        try {
            System.out.println("Comprobando la existencia de la sala");
            return servidorInicio.compruebaSala(Integer.parseInt(codSala));
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param nombreAlumno
     * @return
     */
    public boolean registraAlumnoEnSala(String nombreAlumno) {
        return false;
    }

    public void ejecutaSalaEspera() {

    }

}