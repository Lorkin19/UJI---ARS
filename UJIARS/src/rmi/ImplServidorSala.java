package rmi;

import common.IntServidorSala;
import users.IAlumno;
import users.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

public class ImplServidorSala extends UnicastRemoteObject implements IntServidorSala {
    private Sesion sesion;
    private Set<IAlumno> alumnos;

    public ImplServidorSala(Sesion sesion) throws RemoteException {
        super();
        this.sesion = sesion;
    }

    @Override
    public void addAlumno(IAlumno alumno) throws RemoteException {
        alumnos.add(alumno);
    }
}
