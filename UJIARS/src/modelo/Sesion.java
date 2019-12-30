package modelo;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Sesion {
    private StringProperty nombre;
    private ObservableList<Pregunta> listaPreguntas;
    //private ListProperty<Pregunta> listaPreguntas;

    public Sesion(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
        listaPreguntas = FXCollections.observableArrayList();
    }
    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void addPregunta(Pregunta pregunta) {
        listaPreguntas.add(pregunta);
    }

    public StringProperty getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.setValue(nombre);
    }

    public void setListaPreguntas(ObservableList<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }
}
