package modelo;

public class ExtraPoints extends Pregunta {
    private Pregunta p;

    public ExtraPoints(Pregunta p, int puntos) {
        this.p = p;
        this.puntos += puntos;
    }
}
