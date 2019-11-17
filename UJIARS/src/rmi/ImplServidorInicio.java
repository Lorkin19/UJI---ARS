package rmi;

import common.IntServidorInicio;
import common.IntServidorSala;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplServidorInicio extends UnicastRemoteObject implements IntServidorInicio {

    ImplServidorInicio() throws RemoteException{
        super();
    }

    @Override
    public boolean iniciaProfesor(String usuario, String password) throws RemoteException {
        return false;
    }

    @Override
    public boolean registraProfesor(String usuario, String password) throws RemoteException {
        return false;
    }

    @Override
    public IntServidorSala entrarSala(int codigoSala) throws RemoteException {
        return null;
    }
}
