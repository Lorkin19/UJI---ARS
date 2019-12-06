package common;

import users.IAlumno;
import users.Profesor;
import users.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServidorInicio extends Remote {
    Profesor iniciaProfesor(String usuario, String password) throws RemoteException;
    boolean registraProfesor(String usuario, String password) throws RemoteException;
    boolean compruebaSala(int codigoSala) throws RemoteException;
    IAlumnoSala entrarSala(int codigoSala) throws RemoteException;
    void nuevaSala(Sesion miSesion) throws RemoteException;
}
