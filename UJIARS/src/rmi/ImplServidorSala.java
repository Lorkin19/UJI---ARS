package rmi;

import common.IntServidorSala;
import common.Sala;
import users.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ImplServidorSala extends UnicastRemoteObject implements IntServidorSala {
    Sala miSala;

    ImplServidorSala() throws RemoteException {
        super();
        this.miSala=null;
    }

    @Override
    public void nuevaSala(Sesion miSesion) throws RemoteException {
        miSala = new Sala(miSesion);
    }

    @Override
    public void addStudent(Student student) throws RemoteException {
    }
}
