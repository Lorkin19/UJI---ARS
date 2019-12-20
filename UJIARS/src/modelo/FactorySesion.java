package modelo;

public class FactorySesion {
    public static FactorySesion getInstance() {
        return new FactorySesion();
    }

    public Sesion crearSesion(String nombre, int numPreguntas, FactoryPregunta factory) {
        Sesion s = new Sesion(nombre);

        for (int i = 0; i < numPreguntas; i++) {
            s.addPregunta(factory.crearPregunta());
        }

        return s;
    }
}
