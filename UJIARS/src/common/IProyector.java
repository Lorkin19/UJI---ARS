package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IProyector extends Remote {

    void anyadeAlumno(String nombreAlumno) throws RemoteException;

    void verPregunta(String enunciado, List<String> respuestas) throws RemoteException;

    void verRanking(Map<String, Integer> resultadosAlumnos, Boolean finDePartida) throws RemoteException;

    void setTimer(String tiempo) throws RemoteException;

    void alumnoResponde() throws RemoteException;

    void muestraResultadoPregunta(List<Boolean> correctas, List<Integer> cantRespuestas) throws RemoteException;

}
