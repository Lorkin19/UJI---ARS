package modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class Pregunta {
    private StringProperty enunciado;
    private String respuestaCorrecta;
    private List<String> respuestas;
    protected double tiempo = 15;  // Tiempo de la pregunta (segundos)
    protected int puntos = 1;  // Puntos que da la pregunta por acertar (1 por defecto)

    public Pregunta() {
    }

    public StringProperty getEnunciado() {
        return enunciado;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public double getTiempo() {
        return tiempo;
    }

    public int getPuntos(){ return puntos; }

    public void setEnunciado(String enunciado) {
        this.enunciado = new SimpleStringProperty(enunciado);
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    public void setTiempo(double tiempo){
        this.tiempo = tiempo;
    }

    public void setPuntos(int puntos){
        this.puntos = puntos;
    }
}
