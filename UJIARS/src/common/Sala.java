package common;

import users.Alumno;
import users.Cliente;
import users.IAlumno;
import users.Sesion;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class Sala {
    private int codSala;
    private Map<String, IAlumno> alumnos;
    private Sesion miSesion;

    public Sala(Sesion miSesion, int codSala) {
        this.miSesion = miSesion;
        alumnos = new HashMap<>();
        this.codSala = codSala;
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

        return false;
    }
}
