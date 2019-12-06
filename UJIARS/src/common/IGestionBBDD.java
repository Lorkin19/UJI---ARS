package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGestionBBDD extends Remote {
    void registraProfesor(String usuario, String password) throws RemoteException;
}
