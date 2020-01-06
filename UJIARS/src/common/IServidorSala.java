package common;

import java.rmi.RemoteException;

public interface IServidorSala extends IAlumnoSala, IProfesorSala {

    boolean addAlumno(IAlumno alumno) throws RemoteException;

}
