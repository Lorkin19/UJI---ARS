package rmi;

import common.IntServidorInicio;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplServidorInicio extends UnicastRemoteObject implements IntServidorInicio {

    ImplServidorInicio() throws RemoteException{
        super();
    }
}
