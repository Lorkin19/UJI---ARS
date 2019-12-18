package common;

import modelo.Pregunta;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IProyector extends Remote {
    void verPregunta(Pregunta pregunta) throws RemoteException;
    void verResultados(Map<String, Integer> pregunta) throws RemoteException;
}
