package modelo;

import common.IProyector;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class Proyector extends UnicastRemoteObject implements IProyector {

    public Proyector() throws RemoteException {
        super();
    }

    @Override
    public void verPregunta(Pregunta pregunta) throws RemoteException {
        // TODO El orden en el que salen las respuestas tiene que ser aleatorio
        // TODO Falta mirar el tiempo disponible para contestar
        System.out.println("Enunciado de la pregunta: " + pregunta.getEnunciado());
        System.out.println("Respuesta 0: " + pregunta.getRespuestaCorrecta());
        for (int i = 1; i < pregunta.getRespuestas().size(); i++) {
            System.out.println("Respuesta " + i + ": " + pregunta.getRespuestas().get(i));
        }
    }

    @Override
    public void verResultados(Map<String, Integer> resultados) throws RemoteException { // TODO Cambiarlo para pasarle tambien los resultados de la pregunta
        for (String respuesta : resultados.keySet()) {
            System.out.print(respuesta);
            System.out.print("-->");
            System.out.println(resultados.get(respuesta));
        }
    }
}
