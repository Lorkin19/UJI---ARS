package rmi;

import common.IntServidorSala;
import common.Sala;
import users.Sesion;
import users.Alumno;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    public void addStudent(Alumno alumno) throws RemoteException {
    }
}
