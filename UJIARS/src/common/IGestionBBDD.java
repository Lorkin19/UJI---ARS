package common;

import modelo.Pregunta;
import modelo.Profesor;
import modelo.Respuesta;
import modelo.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGestionBBDD extends Remote {
    boolean registraProfesor(String usuario, String password) throws RemoteException;

    boolean compruebaProfesor(String usuario, String password) throws RemoteException;

    void darDeBajaProgesor(String usuario) throws  RemoteException;

    void registraPreguntas(Pregunta pregunta, String nombreConjunto, String usuarioProf) throws RemoteException;

    void registraRespuestas(List<Respuesta> respuestas, int idPregunta) throws RemoteException;

    List<Sesion> getSesionesProfesor(String usuario) throws RemoteException;

    Profesor getProfesor(String usuario) throws RemoteException;

    void editaPregunta(Pregunta pregunta, String usuarioProf, String cuestionario) throws  RemoteException;

    void eliminaPregunta(Pregunta pregunta, String usuarioProf, String nombreCuestionario) throws RemoteException;
}
