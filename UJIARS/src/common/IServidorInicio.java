package common;

import modelo.Respuesta;
import modelo.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServidorInicio extends Remote {

    boolean iniciaProfesor(String usuario, String password) throws RemoteException;

    void profesorIniciaSesion(String usuario, IProfesor profesor) throws RemoteException;

    boolean registraProfesor(String usuario, String password) throws RemoteException;

    boolean compruebaSala(int codigoSala) throws RemoteException;

    IAlumnoSala entrarSala(int codigoSala) throws RemoteException;

    IProfesorSala nuevaSala(Sesion miSesion) throws RemoteException;

    void cerrarSesionProfesor(String nombreProfesor) throws RemoteException;

    void finalizarPartida(int codSala) throws RemoteException;

    void profesorCreaCuestionario(String usuario, Sesion sesion) throws RemoteException;

    int anyadePregunta(String usuario, String nombreCuestionario, String enunciado) throws RemoteException;

    void anyadeRespuesta(int idPregunta, String respuesta, boolean correcta) throws RemoteException;
}
