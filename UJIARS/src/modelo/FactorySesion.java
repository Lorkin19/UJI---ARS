package modelo;

public class FactorySesion {

    /**
     * Usado para obtener una instancia de la fabrica de sesiones
     *
     * @return la nueva fabrica de sesiones
     */
    public static FactorySesion getInstance() {
        return new FactorySesion();
    }

    /**
     * Usado para crear la sesion
     * Se crearan tantas preguntas como el profesor desee
     *
     * @return la sesion con sus preguntas
     */
    public Sesion crearSesion(String nombre, int numPreguntas, FactoryPregunta factory) {
        Sesion s = new Sesion(nombre);

        for (int i = 0; i < numPreguntas; i++) {
            s.addPregunta(factory.crearPregunta());
        }

        return s;
    }
}
