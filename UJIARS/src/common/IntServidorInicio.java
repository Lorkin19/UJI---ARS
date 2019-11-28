package common;

import users.Profesor;
import users.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorInicio extends Remote {
    Profesor iniciaProfesor(String usuario, String password) throws RemoteException;
    boolean registraProfesor(String usuario, String password) throws RemoteException;
    IntServidorSala entrarSala(int codigoSala) throws RemoteException;
    void nuevaSala(Sesion miSesion) throws RemoteException;

}
