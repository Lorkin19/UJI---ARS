package common;

import users.Pregunta;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IGestionBBDD extends Remote {
    void registraProfesor(String usuario, String password) throws RemoteException;
    void registraPreguntas(Pregunta pregunta, String nombreConjunto, String usuarioProf) throws RemoteException;
    void registraRespuestas(String respuestaCorrecta, List<String> respuestas, int idPregunta) throws RemoteException;
    Map<String, List<Pregunta>> getProfessorCuestionarios(String usuario) throws RemoteException;
}
