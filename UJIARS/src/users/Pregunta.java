package users;

import java.util.List;

public class Pregunta {
    private String enunciado;
    private String respuestaCorrecta;
    private List<String> respuestas;
    private double tiempo; // Tiempo de la pregunta
    private int puntos; // Puntos que da la pregunta por acertar

    public Pregunta() {
    }

    public String getEnunciado() {
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
        this.enunciado = enunciado;
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
