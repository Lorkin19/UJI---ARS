package common;

import users.Alumno;
import users.Cliente;
import users.IAlumno;
import users.Sesion;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class Sala {
    private Map<String, IAlumno> alumnos;
    private Sesion miSesion;

    public Sala(Sesion miSesion) {
        this.miSesion = miSesion;
        alumnos = new HashMap<>();
    }

    public boolean addAlumno(IAlumno alumno) {
        try {
            if (alumnos.containsKey(alumno.getNombre())) {
                return false;
            }

            alumnos.put(alumno.getNombre(), alumno);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
