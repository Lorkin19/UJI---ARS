package common;

import modelo.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServidorInicio extends Remote {

    boolean iniciaProfesor(String usuario, String password) throws RemoteException;

    boolean profesorIniciaSesion(String usuario, IProfesor profesor) throws RemoteException;

    boolean registraProfesor(String usuario, String password) throws RemoteException;

    boolean compruebaSala(int codigoSala) throws RemoteException;

    IAlumnoSala entrarSala(int codigoSala) throws RemoteException;

    IProfesorSala nuevaSala(Sesion miSesion) throws RemoteException;

    void cerrarSesionProfesor(String nombreProfesor) throws RemoteException;

    void finalizarPartida(int codSala) throws RemoteException;
}
