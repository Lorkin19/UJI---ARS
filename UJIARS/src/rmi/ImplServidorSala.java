// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------
// Esta clase hay que borrarla, su funcionamiento la hacemos en la clase Sala
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------
package rmi;

import common.IServidorSala;
import users.IAlumno;
import users.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

public class ImplServidorSala extends UnicastRemoteObject implements IServidorSala {
    private Sesion sesion;
    private Set<IAlumno> alumnos;

    public ImplServidorSala(Sesion sesion) throws RemoteException {
        super();
        this.sesion = sesion;
    }

    @Override
    public boolean addAlumno(IAlumno alumno) throws RemoteException {
        alumnos.add(alumno);
        return false;
    }

    @Override
    public void empezarPartida() throws RemoteException {

    }

    @Override
    public void pasarDePregunta() throws RemoteException {

    }
}
