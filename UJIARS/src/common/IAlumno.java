package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IAlumno extends Remote {

    String getNombre() throws RemoteException;

    boolean unirseASala(int codSala) throws RemoteException;

    void muestraPregunta(List<String> respuestas) throws RemoteException;

    void respondePregunta(String respuestaSeleccionada) throws RemoteException;

    void verResultadoPregunta(boolean acierto) throws RemoteException;

    void setTimer(String toString) throws RemoteException;
}
