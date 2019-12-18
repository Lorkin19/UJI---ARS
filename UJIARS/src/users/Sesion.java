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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setListaPreguntas(List<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }
}
