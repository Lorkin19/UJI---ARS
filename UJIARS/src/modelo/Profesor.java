package modelo;

import common.IProfesor;
import common.IServidorInicio;
import common.IServidorSala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Profesor extends UnicastRemoteObject implements IProfesor {
    private String usuario;
    private String password;
    private IServidorSala sala;
    private ObservableList<Sesion> misSesiones;

    public Profesor(String usuario, String password) throws RemoteException {
        this.usuario = usuario;
        this.password = password;
        misSesiones = FXCollections.observableArrayList();
    }

    public Profesor() throws RemoteException {}

    public String getPassword() {
        return password;
    }

    public String getUsuario(){ return usuario; }

    public List<Sesion> getMisSesiones(){ return misSesiones; }

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
     * @return la sesion ya creada
     */
    @Override
    public void crearSesion(int numPreguntas) {
        String nombre = ""; // TODO Se sacara de JavaFX supongo
        Sesion s = new Sesion(nombre);
        for (int i = 0; i < numPreguntas; i++) {
            s.addPregunta(crearPregunta());
        }
        misSesiones.add(s);
    }

    /**
     * Usado para crear la pregunta
     *
     * @return la pregunta ya creada
     */
    @Override
    public Pregunta crearPregunta() {
        // Datos del enunciado
        // Datos de las respuestas
        Pregunta p = new Pregunta();
        p.setEnunciado("");
        p.setRespuestaCorrecta("");
        p.setRespuestas(Arrays.asList("", "", ""));
        return p;
    }

    @Override
    public void crearPartida(Sesion sesion, IServidorInicio servidor) throws RemoteException {
        // TODO ¿Como hacemos lo del codigo de la sala?
        // TODO ¿Lo pone el profesor? ¿El sistema automaticamente genera uno aleatorio? <--
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
    public void finalizarPartida() throws RemoteException {
        sala = null;
    }


}
