package common;

import modelo.Pregunta;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IProyector extends Remote {
    void anyadeAlumno(String nombreAlumno) throws RemoteException;

    void verPregunta(String enunciado, List<String> respuestas) throws RemoteException;
    void verResultados(Map<String, Integer> pregunta) throws RemoteException;

    void setTimer(String tiempo) throws RemoteException;
}
