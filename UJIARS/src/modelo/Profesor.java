package modelo;

import common.IProfesor;
import common.IServidorInicio;
import common.IProfesorSala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
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
        misSesiones = FXCollections.observableArrayList();
        misSesiones.add(new Sesion("Prueba"));
        factorySesion = FactorySesion.getInstance();
        factoryPregunta = FactoryPregunta.getInstance();
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

    public void setMisSesiones(List<Sesion> misSesiones){
        this.misSesiones = FXCollections.observableArrayList(misSesiones);
    }


    /**
     * Usado para crear una sesion
     * Las preguntas solo se pueden crear cuando creas una sesion (Provisional)
     * Las sesiones tienen inicialmente un numero fijo de preguntas (Provisional)
     *
     * @param numPreguntas numero de preguntas de la sesion a crear
     */
    @Override
    public void crearSesion(int numPreguntas) {
        String nombre = ""; // TODO Se sacara de JavaFX supongo
        Sesion s = new Sesion(nombre);
        for (int i = 0; i < numPreguntas; i++) {
            s.addPregunta(crearPregunta());
        }
        misSesiones.add(s);
        // TODO usar FactorySesion
        // TODO añadir la sesion a la BBDD tambien
    }

    /**
     * Usado para crear la pregunta
     *
     * @return la pregunta ya creada
     */
    @Override
    public Pregunta crearPregunta() {
        return factoryPregunta.crearPregunta();
    }

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

    @Override
    public void verResultadosPartida() throws RemoteException {
        sala.verResultadosPartida();
    }

    @Override
    public void finalizarPartida(IServidorInicio servidor) throws RemoteException {
        servidor.finalizarPartida(sala.getCodSala());
        sala = null;
    }

}
