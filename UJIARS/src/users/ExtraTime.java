package users;

public class ExtraTime extends Pregunta {
    private Pregunta p;

    public ExtraTime(Pregunta p, double tiempo) {
        super();
        this.tiempo += tiempo;
    }

    public Pregunta getPregunta() {
        return p;
    }

    public void setPregunta(Pregunta p) {
        this.p = p;
    }

    @Override
    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }
}
