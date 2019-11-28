package users;

import sockets.server.Pregunta;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IProyector extends Remote {
    void verPregunta(Pregunta pregunta) throws RemoteException;
    void verResultados(Pregunta pregunta) throws RemoteException;
}
