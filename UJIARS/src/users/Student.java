package users;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Student extends UnicastRemoteObject implements IAlumno {

    protected Student() throws RemoteException {
        super();
    }

    @Override
    public void responderPregunta(String respuesta) throws RemoteException{

    }
}
