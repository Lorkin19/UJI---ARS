package common;

import modelo.Pregunta;
import modelo.Profesor;
import modelo.Respuesta;
import modelo.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGestionBBDD {
    boolean registraProfesor(String usuario, String password);

    boolean compruebaProfesor(String usuario, String password);

    void darDeBajaProgesor(String usuario);

    int registraPregunta(Pregunta pregunta, String nombreConjunto, String usuarioProf);

    void registraRespuesta(Respuesta respuestas, int idPregunta);

    List<Sesion> getSesionesProfesor(String usuario);

    //Profesor getProfesor(String usuario);

    void editaPregunta(Pregunta pregunta, String usuarioProf, String cuestionario);

    void eliminaPregunta(Pregunta pregunta, String usuarioProf, String nombreCuestionario);

    int getNumSesionesProfesor(String usuario);
}
