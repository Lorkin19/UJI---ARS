package common;

import modelo.Pregunta;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAlumno extends Remote {

    boolean unirseASala(int codSala) throws RemoteException;

    void responderPregunta(Pregunta pregunta) throws RemoteException;

    void verResultadoPregunta(boolean acierto) throws RemoteException;

    String getNombre() throws RemoteException;

    void muestraPregunta() throws RemoteException;
}
