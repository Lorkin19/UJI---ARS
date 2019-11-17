package common;

import users.Alumno;
import users.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorSala extends Remote {

    public void nuevaSala(Sesion miSesion) throws RemoteException;
    public void addStudent(Alumno student) throws RemoteException;
}
