package rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ImplServidorUJIARS {
    public static void main(String[] args) {
        try{
            arrancarRegistro(1099);
            ImplServidorInicio exportedObj = new ImplServidorInicio();
            String registryURL = "rmi://localhost:1099/patata";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Callback Server Ready.");
        }catch (Exception re) {
            System.out.println("Exception in Server.main: " + re);
        }
    }

    public static void arrancarRegistro(int i) throws RemoteException {
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            registro.list();
        } catch (RemoteException e) {
            System.out.println("El registro no se pudo localizar en el puerto 1099");
            LocateRegistry.createRegistry(1099);
            System.out.println("Registro recien creado en el puerto 1099");
        }
    }
}
