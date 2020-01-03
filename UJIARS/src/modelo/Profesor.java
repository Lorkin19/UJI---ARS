package modelo;

import common.IProfesor;
import common.IServidorInicio;
import common.IProfesorSala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Profesor extends UnicastRemoteObject implements IProfesor {
    private String usuario;
    private String password;
    private IProfesorSala sala;
    private ObservableList<Sesion> misSesiones;
    private FactorySesion factorySesion;
    private FactoryPregunta factoryPregunta;

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


    /**
     * Usado para crear una sesion
     * Las preguntas solo se pueden crear cuando creas una sesion (Provisional)
     * Las sesiones tienen inicialmente un numero fijo de preguntas (Provisional)
     *
     * @param numPreguntas numero de preguntas de la sesion a crear
     */
    @Override
    public void crearSesion(int numPreguntas) throws RemoteException {
        String nombre = ""; // TODO Se sacara de JavaFX supongo
        Sesion s = factorySesion.crearSesion(nombre, numPreguntas, factoryPregunta);
        misSesiones.add(s);
        // TODO añadir la sesion a la BBDD tambien
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
     * @param sesion sesion que se utilizara para la partida
     * @param servidor servidor que se encargara de crear la partida
     * @throws RemoteException si algo peta
     */
    @Override
    public void crearPartida(Sesion sesion, IServidorInicio servidor) throws RemoteException {
        sala = servidor.nuevaSala(sesion);
    }

    /**
     * Usado por el profesor para empezar una partida
     * Indica a la sala que tiene que avisar a los alumno
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
     * @param servidor servidor encargado de finalizar la partida
     * @throws RemoteException si algo peta
     */
    @Override
    public void finalizarPartida(IServidorInicio servidor) throws RemoteException {
        servidor.finalizarPartida(sala.getCodSala());
        sala = null;
    }

    @Override
    public void cargarSesiones(List<String> sesionesProfesor) throws RemoteException {
        for (String nombreSesion : sesionesProfesor){
            misSesiones.add(new Sesion(nombreSesion));
        }
    }

    public void addSesion(Sesion sesion) {
        misSesiones.add(sesion);
    }
}
