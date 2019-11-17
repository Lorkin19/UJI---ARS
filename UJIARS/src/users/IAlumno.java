package users;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAlumno extends Remote {
    void unirseASala(int codSala) throws RemoteException;
    void responderPregunta(String respuesta) throws RemoteException;
}
