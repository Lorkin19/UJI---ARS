package users;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Alumno extends UnicastRemoteObject implements IAlumno {

    protected Alumno() throws RemoteException {
        super();
    }

    @Override
    public void responderPregunta(String respuesta) throws RemoteException{

    }
}
