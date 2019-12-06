package common;

import users.Pregunta;
import users.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IProfesor extends Remote {
    void crearSesion(int numPreguntas) throws RemoteException;

    Pregunta crearPregunta() throws RemoteException;

    void crearPartida(Sesion sesion, IServidorInicio servidor) throws RemoteException;

    void empezarPartida() throws RemoteException;

    void pasarDePregunta() throws RemoteException;

    String getPassword()throws RemoteException;
}
