package users;

import java.util.ArrayList;
import java.util.List;

public class Sesion {
    private String nombre;
    private List<Pregunta> listaPreguntas;

    public Sesion(String nombre) {
        this.nombre = nombre;
        listaPreguntas = new ArrayList<>();
    }
    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void addPregunta(Pregunta pregunta) {
        listaPreguntas.add(pregunta);
    }
}
