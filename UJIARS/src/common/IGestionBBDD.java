package common;

import users.Pregunta;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGestionBBDD extends Remote {
    void registraProfesor(String usuario, String password) throws RemoteException;
    void registraPreguntas(Pregunta pregunta, String nombreConjunto) throws RemoteException;
    void registraRespuestas(String respuestaCorrecta, List<String> respuestas, int idPregunta) throws RemoteException;
}
