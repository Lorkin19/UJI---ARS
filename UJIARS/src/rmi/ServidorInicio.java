package rmi;

import common.*;
import modelo.Pregunta;
import modelo.Respuesta;
import modelo.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorInicio extends UnicastRemoteObject implements IServidorInicio {

    private Map<String, IProfesor> profesoresConectados;
    private Map<Integer, IServidorSala> salas;
    private Map<String, Map<String, Sesion>> sesionesProfesores;
    private GestionBBDD conexionBBDD;

    ServidorInicio() throws RemoteException {
        super();
        profesoresConectados = new HashMap<>(); // Temporal hasta que pongamos la BBDD

        salas = new HashMap<>(); // Temporal, puede que no sea asi

        sesionesProfesores = new HashMap<>();

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

    /**
     * Cuando un profesor inicia sesion se comprueba que no este conectado previamente.
     * Si no esta conectado, entonces se carga en el servidor todas las salas del profesor
     * y se le envian al profesor los nombres de las salas para que construya objetos
     * de tipo sala basicos.
     * @param usuario   Nombre de usuario del profesor.
     * @param profesor  Interfaz del profesor.
     * @throws RemoteException  Si hay algun error.
     */
    @Override
    public void profesorIniciaSesion(String usuario, IProfesor profesor) throws RemoteException {
        // Si el profesor ya esta conectado, no dejarle volver a iniciar sesion
        if (!profesoresConectados.containsKey(usuario)) {
            // TODO cargar en la instancia del profesor todo lo que tiene.
            //int numSesiones = conexionBBDD.getNumSesionesProfesor(usuario);

            List<Sesion> sesionesProfesor = conexionBBDD.getSesionesProfesor(usuario);
            Map<String, Sesion> sesiones = new HashMap<>();
            for (Sesion miSesion : sesionesProfesor){
                sesiones.put(miSesion.getNombre().get(), miSesion);
            }
            sesionesProfesores.put(usuario, sesiones);

            List<String> nombreSesiones = new ArrayList<>();
            for (Sesion sesion : sesionesProfesor){
                nombreSesiones.add(sesion.getNombre().get());
            }
            System.out.println("Cargando sesiones del profesor: " + usuario + ".\n\tCantidad de sesiones: " + sesionesProfesor.size() + "\n");
            profesor.cargarSesiones(nombreSesiones);
            profesoresConectados.put(usuario, profesor);
        }
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
    public IProfesorSala nuevaSala(String usuario, String miSesion, IProyector proyector) throws RemoteException {
        int codSala = generateCodSala();
        System.out.println("(ServidorInicio.nuevaSala) Nombre de la sesion: " + miSesion);
        Sesion sesion = sesionesProfesores.get(usuario).get(miSesion);
        if (sesion == null) {
            System.out.println("(ServidorInicio.nuevaSala) La sesion es nula");
            sesion = conexionBBDD.getSesionProfesor(usuario, miSesion);
        }
        IServidorSala sala = new Sala(sesion, codSala, proyector);
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
        sesionesProfesores.remove(nombreProfesor);
        System.out.println("Profesor " + nombreProfesor + " ha cerrado su sesion.");
    }

    @Override
    public void finalizarPartida(int codSala) throws RemoteException {
        salas.remove(codSala);
    }

    /**
     * Registra una nueva pregunta de un cuestionario
     * @param usuario               Nombre de usuario del profesor.
     * @param nombreCuestionario    Nombre del cuestionario.
     * @param enunciado             Enunciado de la pregunta.
     * @return  Id de la pregunta.
     * @throws RemoteException  Si algo peta.
     */
    @Override
    public int anyadePregunta(String usuario, String nombreCuestionario, String enunciado) throws RemoteException {
        System.out.println("Anyadiendo pregunta del profesor " + usuario);
        Pregunta pregunta = new Pregunta();
        pregunta.setEnunciado(enunciado);
        return conexionBBDD.registraPregunta(pregunta, nombreCuestionario, usuario);
    }

    /**
     * Registra una respuesta de una pregunta.
     * @param idPregunta    Id de la pregunta a la que corresponde la respuesta.
     * @param opcion        Respuesta a la pregunta.
     * @param correcta      Si la pregunta es correcta o no.
     * @throws RemoteException si algo peta.
     */
    @Override
    public void anyadeRespuesta(int idPregunta, String opcion, boolean correcta) throws RemoteException {
        System.out.println("\tAnyadiendo respuesta a la pregunta");
        Respuesta respuesta = new Respuesta(opcion, correcta);
        conexionBBDD.registraRespuesta(respuesta, idPregunta);
    }
}
