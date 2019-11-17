package common;

import users.Student;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorSala extends Remote {

    public void nuevaSala(Sesion miSesion) throws RemoteException;
    public void addStudent(Student student) throws RemoteException;
}
