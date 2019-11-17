package users;

import server.Pregunta;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Proyector extends UnicastRemoteObject implements IProyector {

    protected Proyector() throws RemoteException {
        super();
    }

    @Override
    public void verPregunta(Pregunta pregunta) throws RemoteException {

    }

    @Override
    public void verResultados(Pregunta pregunta) throws RemoteException {

    }
}
