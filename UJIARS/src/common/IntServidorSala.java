package common;

import users.IAlumno;
import users.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorSala extends Remote {

    void addAlumno(IAlumno alumno) throws RemoteException;
}
