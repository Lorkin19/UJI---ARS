package users;

import server.Pregunta;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Profesor extends UnicastRemoteObject implements IProyector {
    private IProyector proyector;
    private String nombre;
    private String password;
    
    public Profesor() throws RemoteException {
        super();
    }
    @Override
    public void verPregunta(Pregunta pregunta) throws RemoteException {

    }

    @Override
    public void verResultados(Pregunta pregunta) throws RemoteException {

    }
}
