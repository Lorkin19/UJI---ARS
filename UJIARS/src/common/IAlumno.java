package common;

import users.Pregunta;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAlumno extends Remote {
    void unirseASala(int codSala) throws RemoteException;
    void responderPregunta(Pregunta pregunta) throws RemoteException;

    void verResultadoPregunta(boolean acierto) throws RemoteException;

    String getNombre() throws RemoteException;
}
