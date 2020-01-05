package common;

import modelo.Pregunta;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IProyector extends Remote {
    void verPregunta(String enunciado, List<String> respuestas) throws RemoteException;
    void verResultados(Map<String, Integer> pregunta) throws RemoteException;
}
