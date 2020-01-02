package rmi;

import common.*;
import javafx.beans.property.StringProperty;
import modelo.Profesor;
import modelo.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ServidorInicio extends UnicastRemoteObject implements IServidorInicio {

    private Map<String, IProfesor> profesoresConectados;
    private Map<Integer, IServidorSala> salas;
    private GestionBBDD conexionBBDD;

    ServidorInicio() throws RemoteException {
        super();
        profesoresConectados = new HashMap<>(); // Temporal hasta que pongamos la BBDD

        salas = new HashMap<>(); // Temporal, puede que no sea asi

        conexionBBDD = new GestionBBDD();
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
    public boolean iniciaProfesor(String usuario, String password) throws RemoteException {
        return conexionBBDD.compruebaProfesor(usuario, password);
    }

    @Override
    public boolean profesorIniciaSesion(String usuario, IProfesor profesor){
        // Si el profesor ya esta conectado, no dejarle volver a iniciar sesion
        if (!profesoresConectados.containsKey(usuario)) {
            // TODO cargar en la instancia del profesor todo lo que tiene.
            List<Sesion> sesionesProfesor = conexionBBDD.getSesionesProfesor(usuario);
            profesor.cargarSesiones(sesionesProfesor);
            profesoresConectados.put(usuario, profesor);
            return true;
        }
        return false;
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
//        // Si el profesor esta registrado no deja registrarse
//        // v1.0
//        System.out.println("Comprobando profesor " + usuario);
//        if (profesores.containsKey(usuario)) {
//            return false;
//        }
//
//        System.out.println("El nombre de usuario esta disponible");
//        IProfesor p = new Profesor(usuario, password);
//        System.out.println("Profesor creado");
//        profesores.put(usuario, p);
//        System.out.println("Profesor anyadido");
//        return true;
//
        // v2.0
        return conexionBBDD.registraProfesor(usuario, password);
    }

    /**
     * Comprueba que la sala existe antes de pedir el nombre del alumno.
     *
     * @param codigoSala Codigo de la sala a la que pretende unirse.
     * @return true si la sala existe, false si no existe.
     * @throws RemoteException si falla la conexion.
     */
    @Override
    public boolean compruebaSala(int codigoSala) throws RemoteException {
        return salas.get(codigoSala) != null;
    }

    /**
     * Entrar a la sala
     *
     * @param codigoSala codigo de la sala de la partida
     * @return la sala, null si no existe
     * @throws RemoteException si algo peta
     */
    @Override
    public IAlumnoSala entrarSala(int codigoSala) throws RemoteException {
        return salas.get(codigoSala);
    }

    @Override
    public IProfesorSala nuevaSala(Sesion miSesion) throws RemoteException {
        int codSala = generateCodSala();
        IServidorSala sala = new Sala(miSesion, codSala);
        salas.put(codSala, sala);
        return sala;
    }


    /**
     * Usado para generar un codigo para la sala
     * Solo generara codigos que actualmente no esten usandose
     *
     * @return codigo de la sala
     */
    private int generateCodSala() {
        int codSala = (int) (Math.random() * 1000000);
        while (salas.containsKey(codSala)) {
            codSala = (int) (Math.random() * 1000000);
        }
        return codSala;
    }

    @Override
    public void cerrarSesionProfesor(String nombreProfesor) throws RemoteException {
        profesoresConectados.remove(nombreProfesor);
        System.out.println("Profesor " + nombreProfesor + " ha cerrado su sesion.");
    }

    @Override
    public void finalizarPartida(int codSala) throws RemoteException {
        salas.remove(codSala);
    }

    @Override
    public void profesorCreaCuestionario(String usuario, Sesion sesion) {

    }
}
