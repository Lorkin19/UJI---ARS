package users;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Alumno extends UnicastRemoteObject implements IAlumno {

    private String nombre;

    protected Alumno(String nombre) throws RemoteException {
        super();
        this.nombre = nombre;
    }

    @Override
    public void unirseASala(int codSala) throws RemoteException {

    }

    @Override
    public void responderPregunta(String respuesta) throws RemoteException{

    }

    @Override
    public String getNombre() throws RemoteException {
        return nombre;
    }
}
