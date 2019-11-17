package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorInicio extends Remote {
    boolean iniciaProfesor(String usuario, String password) throws RemoteException;
    boolean registraProfesor(String usuario, String password) throws RemoteException;
    IntServidorSala entrarSala(int codigoSala) throws RemoteException;
}
