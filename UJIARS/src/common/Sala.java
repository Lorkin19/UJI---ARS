package common;

import users.Cliente;
import server.Pregunta;
import users.Sesion;

import java.util.List;
import java.util.Map;

public class Sala {
    private Map<String, Cliente> alumnos;
    private Sesion miSesion;

    public Sala(Sesion miSesion){
        this.miSesion = miSesion;
    }
}
