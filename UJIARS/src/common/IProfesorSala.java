package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IProfesorSala extends Remote {

    boolean addAlumno(IAlumno alumno) throws RemoteException;

    void empezarPartida() throws RemoteException;

    void pasarDePregunta() throws RemoteException;

    void verResultadosPartida() throws RemoteException;

    int getCodSala() throws RemoteException;
}
