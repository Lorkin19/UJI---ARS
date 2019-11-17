package rmi;

import common.IntServidorSala;
import common.Sala;
import users.IAlumno;
import users.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplServidorSala extends UnicastRemoteObject implements IntServidorSala {
    private Sala miSala;

    ImplServidorSala() throws RemoteException {
        super();
        this.miSala=null;
    }

    @Override
    public void nuevaSala(Sesion miSesion) throws RemoteException {
        miSala = new Sala(miSesion);
    }

    @Override
    public void addAlumno(IAlumno alumno) throws RemoteException {
        miSala.addAlumno(alumno);
    }
}
