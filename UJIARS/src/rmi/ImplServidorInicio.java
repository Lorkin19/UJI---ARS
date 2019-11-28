package rmi;

import common.IntServidorInicio;
import common.IntServidorSala;
import users.Profesor;
import users.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ImplServidorInicio extends UnicastRemoteObject implements IntServidorInicio {

    private Map<String, Profesor> profesores;
    private Map<Integer,IntServidorSala> salas;

    ImplServidorInicio() throws RemoteException{
        super();
        //coger los datos de los profesores de la bbdd y guardarlos en un set
        salas = new HashMap<>(); // Temporal, puede que no sea asi
    }

    /**
     * Devuelve el profesor una vez iniciado sesion
     *
     * @param usuario nombre del profesor
     * @param password password del profesor
     * @return el profesor si la contrasena coincide con el usuario, null si no coincide
     * @throws RemoteException
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
     * @throws RemoteException
     */
    @Override
    public synchronized boolean registraProfesor(String usuario, String password) throws RemoteException {
        // Siel profesor esta registrado no deja registrarse
        if (profesores.containsKey(usuario)) {
            System.out.println("Usuario ya registrado");
            return false;
        }

        Profesor p = new Profesor(usuario, password);
        profesores.put(usuario, p);
        return true;
    }

    /**
     * en el servidor de inicio, entrar a la sala
     *
     * @param codigoSala codigo de la sala donde estara la partida
     * @return la sala, null si no existe
     * @throws RemoteException
     */
    @Override
    public IntServidorSala entrarSala(int codigoSala) throws RemoteException {
        return salas.get(codigoSala);
    }

    @Override
    public void nuevaSala(Sesion miSesion) throws RemoteException {

    }
}
