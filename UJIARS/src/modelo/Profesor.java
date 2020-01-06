package modelo;

import common.IProfesor;
import common.IProyector;
import common.IServidorInicio;
import common.IProfesorSala;
import controlador.profesor.GestionaSalaController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Profesor extends UnicastRemoteObject implements IProfesor {
    private String usuario;
    private String password;
    private IProfesorSala sala;
    private IServidorInicio servidor;
    private ObservableList<Sesion> misSesiones;
    private FactorySesion factorySesion;
    private FactoryPregunta factoryPregunta;
    private Proyector proyector;
    private GestionaSalaController gestionaSalaController;

    public Profesor(String usuario, String password) throws RemoteException {
        this.usuario = usuario;
        this.password = password;
        this.misSesiones = FXCollections.observableArrayList();
        this.factorySesion = FactorySesion.getInstance();
        this.factoryPregunta = FactoryPregunta.getInstance();
    }

    public Profesor() throws RemoteException {}

    public String getPassword() {
        return password;
    }

    public String getUsuario(){ return usuario; }

    public ObservableList<Sesion> getMisSesiones() {
        return this.misSesiones;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public void setPassword(String password){
        this.password = password;
    }


    public void setServidor(IServidorInicio servidor){
        this.servidor = servidor;
    }

    public void setGestionaSalaController(GestionaSalaController controller) {
        this.gestionaSalaController = controller;
    }

    /**
     * Usado para crear una sesion
     *
     * @param numPreguntas numero de preguntas de la sesion a crear
     */
    @Override
    public void crearSesion(int numPreguntas) throws RemoteException {
        String nombre = "";
        Sesion s = factorySesion.crearSesion(nombre, numPreguntas, factoryPregunta);
        misSesiones.add(s);
    }

    /**
     * Usado para crear la pregunta
     *
     * @return la pregunta ya creada
     */
    @Override
    public Pregunta crearPregunta() throws RemoteException {
        return factoryPregunta.crearPregunta();
    }


    /**
     * Usado para crear la sala que alojara la partida
     *
     * @param nombreSesion nombre de la sesion que se utilizara para la partida
     * @param servidor servidor que se encargara de crear la partida
     * @throws RemoteException si algo peta
     */
    @Override
    public Proyector crearPartida(String nombreSesion, IServidorInicio servidor) throws RemoteException {
        proyector = new Proyector();
        System.out.println("(Profesor.crearPartida)NombreSesion: " + nombreSesion);
        sala = servidor.nuevaSala(this.usuario, nombreSesion, (IProyector) proyector);
        return proyector;
    }

    /**
     * Usado por el profesor para empezar una partida
     * Indica a la sala que tiene que avisar a los alumnos
     *
     * @throws RemoteException si algo peta
     */
    @Override
    public void empezarPartida() throws RemoteException {
        sala.empezarPartida();
    }

    /**
     * Usado por el profesor para pasar de pregunta
     * Una vez que la pregunta ha acabado, el proyector visualiza los resultados
     *
     * @throws RemoteException si algo peta
     */
    @Override
    public void pasarDePregunta() throws RemoteException {
        sala.pasarDePregunta();
    }


    /**
     * Usado por el profesor para indicarle a la sala que quiere ver los resultados de la partida una vez se han contestado todas las preguntas
     *
     * @throws RemoteException si algo peta
     */
    @Override
    public void verResultadosPartida() throws RemoteException {
        sala.verResultadosPartida();
    }


    /**
     * Usado por el profesor para indicar al servidor que la partida ha finalizado
     * Una vez llamado a este metodo, la sala y la partida se borran
     *
     * @throws RemoteException si algo peta
     */
    @Override
    public void finalizarPartida() throws RemoteException {
        servidor.finalizarPartida(sala.getCodSala());
        sala = null;
    }

    @Override
    public void cargarSesiones(List<String> sesionesProfesor) throws RemoteException {
        for (String nombreSesion : sesionesProfesor){
            misSesiones.add(new Sesion(nombreSesion));
        }
    }

    @Override
    public void muestraEnunciado(String enunciado) throws RemoteException {
        Platform.runLater(() -> gestionaSalaController.pregunta.setText(enunciado));
    }

    public void addSesion(Sesion sesion) {
        misSesiones.add(sesion);
    }

    /**
     * Obtiene el codigo de la sala que ha ejecutado para mostrarlo en el proyector.
     */
    public int getCodSala(){
        try {
            return sala.getCodSala();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
