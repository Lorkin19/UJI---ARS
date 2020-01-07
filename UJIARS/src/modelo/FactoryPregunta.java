package modelo;

import java.util.Arrays;

public class FactoryPregunta {

    /**
     * Usado para obtener una instancia de la fabrica de preguntas
     * Solo debe ser accedida por la fabrica de sesiones
     *
     * @return la nueva fabrica de preguntas
     */
    public static FactoryPregunta getInstance() {
        return new FactoryPregunta();
    }


    /**
     * Usado para crear las preguntas
     *
     * @return la pregunta ya creada
     */
    public Pregunta crearPregunta() {
        return new Pregunta();
    }

    public Pregunta crearPreguntaDecorada(int extraPoints, double extraTime){
        return new ExtraPoints(new ExtraTime(new Pregunta(), extraTime), extraPoints);
    }


}
