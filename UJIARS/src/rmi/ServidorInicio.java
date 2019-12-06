package rmi;

import common.IAlumnoSala;
import common.IProfesor;
import common.IServidorInicio;
import users.Profesor;
import users.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServidorInicio extends UnicastRemoteObject implements IServidorInicio {

    private Map<String, IProfesor> profesores;
    private Map<Integer, IAlumnoSala> salas;

    ServidorInicio() throws RemoteException {
        super();
        // TODO Coger los datos de los profesores de la BBDD y guardarlos en un set
        profesores = new HashMap<>(); // Temporal hasta que pongamos  la BBDD

        salas = new HashMap<>(); // Temporal, puede que no sea asi
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
    public IProfesor iniciaProfesor(String usuario, String password) throws RemoteException {
        // Comprobar el el profesor exista
        if (!profesores.containsKey(usuario)) {
            System.out.println("El nombre de usuario no existe");  // TODO Cambiar a "Nombre de usuario o contrasenya incorrecto" (Razones de seguridad)
            return null;
        }

        // Comprobar que la contrasenya coincida con la dada
        IProfesor p = profesores.get(usuario);
        if (p.getPassword().equals(password)) {
            return p;
        }

        // La contrasenya no coincide
        System.out.println("Contrasenya incorrecta");
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
    public boolean registraProfesor(String usuario, String password) throws RemoteException {
        // Si el profesor esta registrado no deja registrarse
        System.out.println("Comprobando profesor " + usuario);
        if (profesores.containsKey(usuario)) {
            return false;
        }

        System.out.println("El nombre de usuario esta disponible");
        IProfesor p = new Profesor(usuario, password);
        System.out.println("Profesor creado");
        profesores.put(usuario, p);
        System.out.println("Profesor anyadido");
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
        return salas.get(codigoSala) != null;
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
