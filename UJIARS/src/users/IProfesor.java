package users;

import java.rmi.RemoteException;

public interface IProfesor {
    void crearSesion(int numPreguntas);

    Pregunta crearPregunta();

    void crearPartida(Sesion sesion) throws RemoteException;

    void empezarPartida() throws RemoteException;

    void pasarDePregunta() throws RemoteException;
}
