package modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Respuesta {

    private StringProperty respuesta;
    private boolean correcta;

    public Respuesta(String respuesta, boolean correcta){
        this.respuesta = new SimpleStringProperty(respuesta);
        this.correcta = correcta;
    }

    public String getRespuesta() {
        return respuesta.get();
    }

    public StringProperty respuestaProperty() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta.set(respuesta);
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }
}
