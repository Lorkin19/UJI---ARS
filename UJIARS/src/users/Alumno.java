package users;

import common.IAlumno;
import common.IAlumnoSala;
import common.IServidorInicio;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Alumno extends UnicastRemoteObject implements IAlumno {

    private String nombre;
    private IServidorInicio servidor;
    private IAlumnoSala sala;

    protected Alumno(String nombre, IServidorInicio servidor) throws RemoteException {
        super();
        this.nombre = nombre;
        this.servidor = servidor;
    }

    @Override
    public String getNombre() throws RemoteException {
        return nombre;
    }

    @Override
    public void unirseASala(int codSala) throws RemoteException {
        sala = servidor.entrarSala(codSala);
        System.out.println("Has entrado en la sala. Esperando a que empiece la partida");
    }

    @Override
    public void responderPregunta(Pregunta pregunta) throws RemoteException {
        // TODO El orden en el que salen las respuestas tiene que ser aleatorio
        // TODO Falta mirar el tiempo disponible para contestar
        System.out.println("Enunciado de la pregunta: " + pregunta.getEnunciado());
        System.out.println("Respuesta 0: " + pregunta.getRespuestaCorrecta());
        for (int i = 1; i < pregunta.getRespuestas().size(); i++) {
            System.out.println("Respuesta " + i + ": " + pregunta.getRespuestas().get(i));
        }
        // TODO ¿Como hacer que el alumno seleccione una pregunta? Supongo que con JavaFX se hara facilmente
        // De momento, pongo que automaticamente contesta la respuesta correcta (Provisional)
        sala.alumnoResponde(nombre, pregunta.getRespuestaCorrecta());
    }

    @Override
    public void verResultadoPregunta(boolean acierto) throws RemoteException {
        if (acierto) {
            System.out.println("Respuesta acertada");
        } else {
            System.out.println("Haber estudiao");
        }
    }


}
