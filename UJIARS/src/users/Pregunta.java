package users;

import java.util.List;

public class Pregunta {
    private String enunciado;
    private String respuestaCorrecta;
    private List<String> respuestasIncorrectas;
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

    public List<String> getRespuestasIncorrectas() {
        return respuestasIncorrectas;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public void setRespuestasIncorrectas(List<String> respuestasIncorrectas) {
        this.respuestasIncorrectas = respuestasIncorrectas;
    }
}
