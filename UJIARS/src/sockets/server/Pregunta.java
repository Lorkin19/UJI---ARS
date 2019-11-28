package sockets.server;

import java.util.List;

public class Pregunta {
    private String enunciado;
    private String respuestaCorrecta;
    private List<String> respuestasIncorrectas;
    private double tiempo; // Tiempo de la pregunta
    private int puntos; // Puntos que da la pregunta por acertar
}
