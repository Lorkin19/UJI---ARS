package users;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Profesor extends UnicastRemoteObject implements IProfesor {
    private String usuario;
    private String password;
    private IProyector proyector;

    public String getPassword() {
        return password;
    }

    public Profesor(String usuario, String password) throws RemoteException {
        this.usuario = usuario;
        this.password = password;
    }

    public IProyector getProyector() {
        return proyector;
    }

    public void setProyector(IProyector proyector) {
        this.proyector = proyector;
    }
}
