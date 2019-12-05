package common;



import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz con los metodos que el alumno puede utilizar para comunicarse con el servidor de la sala
 * Separada de la interfaz del servidor para que solo tenga los metodos que el alumno puede utilizar
 */
public interface IAlumnoSala extends Remote {
    void alumnoResponde(String nombre, String respuesta) throws RemoteException;
}
