package rmi;

import common.IAlumnoSala;
import common.IServidorInicio;
import common.IServidorSala;
import users.IAlumno;
import users.Profesor;
import users.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServidorInicio extends UnicastRemoteObject implements IServidorInicio {

    private Map<String, Profesor> profesores;
    private Map<Integer, IAlumnoSala> salas;

    ServidorInicio() throws RemoteException {
        super();
        // TODO Coger los datos de los profesores de la BBDD y guardarlos en un set
        salas = new HashMap<>(); // Temporal, puede que no sea asi
        profesores = new HashMap<>(); //Temporal
    }

    /**
     * Devuelve el profesor una vez iniciado sesion
     *
     * @param usuario nombre del profesor
     * @param password password del profesor
     * @return el profesor si la contrasena coincide con el usuario, null si no coincide
     * @throws RemoteException si algo peta
     */
    @Override
    public Profesor iniciaProfesor(String usuario, String password) throws RemoteException {
        Profesor p = profesores.get(usuario);
        if (p.getPassword().equals(password))
            return p;
        return null;
    }

    /**
     * Registra los profesores
     *
     * @param usuario nombre de usuario del profesor
     * @param password password
     * @return si se ha podido anyadir
     * @throws RemoteException si algo peta
     */
    @Override
    public synchronized boolean registraProfesor(String usuario, String password) throws RemoteException {
        // Si el profesor esta registrado no deja registrarse
        if (profesores.get(usuario) != null){
            System.out.println("Usuario ya registrado");
            return false;
        }
        Profesor p = new Profesor(usuario, password, this);
        profesores.put(usuario, p);
        return true;
    }

    /**
     * Comprueba que la sala existe antes de pedir el nombre del alumno.
     * @param codigoSala Codigo de la sala a la que pretende unirse.
     * @return true si la sala existe, false si no existe.
     * @throws RemoteException si falla la conexion.
     */
    @Override
    public boolean compruebaSala(int codigoSala) throws RemoteException {
        if (salas.get(codigoSala)!=null)
            return true;
        return false;
    }

    /**
     * en el servidor de inicio, entrar a la sala
     *
     * @param codigoSala codigo de la sala donde estara la partida
     * @return la sala, null si no existe
     * @throws RemoteException si algo peta
     */
    @Override
    public IAlumnoSala entrarSala(int codigoSala) throws RemoteException {
        return salas.get(codigoSala);
    }

    @Override
    public void nuevaSala(Sesion miSesion) throws RemoteException {
        // TODO Convertir sesion en sala, si el codigo lo genera el servidor --> return codSala
        // Si es el profesor el que genera el codigo, cambiar el parametro a uno de tipo Sala
    }
}
