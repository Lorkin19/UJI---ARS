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
        Pregunta p = new Pregunta();
        p.setEnunciado("");
        String respuestaCorrecta = "";
        //p.setRespuestaCorrecta(respuestaCorrecta);
        //p.setRespuestas(Arrays.asList("", "", "", respuestaCorrecta));
        // TODO Mirar lo de los distintos tipos de preguntas
        p = new ExtraPoints(p, 0);  // Provisional
        p = new ExtraTime(p, 0);  // Provisional
        return p;
    }


}
