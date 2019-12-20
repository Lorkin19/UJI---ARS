package modelo;

import java.util.Arrays;

public class FactoryPregunta {
    public Pregunta crearPregunta() {
        Pregunta p = new Pregunta();
        p.setEnunciado("");
        String respuestaCorrecta = "";
        p.setRespuestaCorrecta(respuestaCorrecta);
        p.setRespuestas(Arrays.asList("", "", "", respuestaCorrecta));
        // TODO Mirar lo de los distintos tipos de preguntas
        p = new ExtraPoints(p, 0);  // Provisional
        p = new ExtraTime(p, 0);  // Provisional
        return p;
    }

    public static FactoryPregunta getInstance() {
        return new FactoryPregunta();
    }
}
