package common;

import users.Pregunta;
import users.Profesor;
import users.Sesion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IGestionBBDD extends Remote {
    boolean registraProfesor(String usuario, String password) throws RemoteException;
    boolean compruebaProfesor(String usuario, String password) throws RemoteException;
    void registraPreguntas(Pregunta pregunta, String nombreConjunto, String usuarioProf) throws RemoteException;
    void registraRespuestas(String respuestaCorrecta, List<String> respuestas, int idPregunta) throws RemoteException;
    List<Sesion> getSesionesProfesor(String usuario) throws RemoteException;
    Profesor getProfesor(String usuario) throws RemoteException;
}
