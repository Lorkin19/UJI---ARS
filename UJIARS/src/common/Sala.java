package common;

import users.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Sala extends UnicastRemoteObject implements IServidorSala, IAlumnoSala {
    private int codSala;
    private Map<String, IAlumno> alumnos;
    private Sesion miSesion;
    private int numPreguntaActual;
    private IProyector proyector; // TODO ¿Como pasarselo a la sala?
    // Guardar los datos de las respuestas

    public Sala(Sesion miSesion, int codSala) throws RemoteException {
        super();
        this.miSesion = miSesion;
        alumnos = new HashMap<>();
        this.codSala = codSala;
    }

    /**
     * Un alumno entra a la sala. Si el nombre del alumno ya esta en la sala, no le deja unirse (Solo si los alumnos no tienen que registrarse)
     *
     * @param alumno alumno que se quiere unir a la sala
     * @return Si se ha podido anyadir
     * @throws RemoteException si algo peta
     */
    @Override
    public boolean addAlumno(IAlumno alumno) throws RemoteException {
        if (alumnos.containsKey(alumno.getNombre())) {
            return false;
        }
        alumnos.put(alumno.getNombre(), alumno);
        return true;
    }

    /**
     * La sala analiza la respuesta del alumno y le indica si ha acertado o no
     *
     * @param nombreAlumno nombre del alumno que realiza la respuesta
     * @param respuesta respuesta seleccionada por el alumno
     * @throws RemoteException si algo peta
     */
    @Override
    public void alumnoResponde(String nombreAlumno, String respuesta) throws RemoteException {
        IAlumno alumno = alumnos.get(nombreAlumno);
        boolean acierto = miSesion.getListaPreguntas().get(numPreguntaActual).getRespuestaCorrecta().equals(respuesta);
        alumno.verResultadoPregunta(acierto);
    }

    /**
     * Usado por el profesor para empezar la partida
     * Envia a los alumnos que esten en la sala la primera pregunta
     */
    @Override
    public void empezarPartida() throws RemoteException {
        numPreguntaActual = 0;
        notificaAlumnos();
        notificaProyector();
    }



    @Override
    public void pasarDePregunta() throws RemoteException {
        numPreguntaActual++;
        notificaAlumnos();
        notificaProyector();
    }

    /**
     * Notifica a los alumnos que hay una nueva pregunta y se la envia
     *
     * @throws RemoteException si algo peta
     */
    private void notificaAlumnos() throws RemoteException {
        for (IAlumno alumno : alumnos.values()) {
            alumno.responderPregunta(miSesion.getListaPreguntas().get(numPreguntaActual));
        }
    }

    /**
     * Se notifica al proyector de la siguiente pregunta de la partida
     *
     * @throws RemoteException si algo peta
     */
    private void notificaProyector() throws RemoteException{
        proyector.verPregunta(miSesion.getListaPreguntas().get(numPreguntaActual));
    }
}
