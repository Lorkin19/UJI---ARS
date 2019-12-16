package rmi;

import common.IGestionBBDD;
import users.Pregunta;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GestionBBDD extends UnicastRemoteObject implements IGestionBBDD {

    GestionBBDD() throws RemoteException {

    }

    /**
     * Se realiza una conexion con la base de datos.
     * @return La conexion con la base de datos.
     */
    private Connection conecta(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:C:dbPrueba.db");
            return conexion;

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error en la conexión de la base de datos");
            return null;
        }
    }

    /**
     * Registramos el profesor en la base de datos
     * @param usuario Nombre de usuario del profesor
     * @param password Contrasenya del profesor
     * @throws RemoteException Si hay un error en la conexion remota
     */
    @Override
    public void registraProfesor(String usuario, String password) throws RemoteException {
        try {
            Connection conexion = conecta();
            String sentencia = "INSERT INTO profesor VALUES (?,?)";
            PreparedStatement st = conexion.prepareStatement(sentencia);
            st.setString(1, usuario);
            st.setString(2, password);
            st.executeUpdate();
            System.out.println("Profesor creado correctamente");
        } catch (SQLException e) {
            System.out.println("El profesor ya existe");
        }
    }

    @Override
    public void registraPreguntas(Pregunta pregunta, String nombreConjunto) throws RemoteException {
        try{
            Connection connection = conecta();
            String sentencia = "INSERT INTO pregunta VALUES (?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentencia);

            st.setString(1, pregunta.getEnunciado());
            st.setDouble(2, pregunta.getTiempo());
            st.setInt(3, pregunta.getPuntos());
            st.setString(4, nombreConjunto);

            st.executeUpdate();
            System.out.println("Pregunta guardada correctamente.");

            //todo obtener el id de la pregunta recien guardada --> necesario para guardar las respuestas
            int idPregunta = 0;
            registraRespuestas(pregunta.getRespuestaCorrecta(), pregunta.getRespuestas(), idPregunta);
        }catch (SQLException e){
            System.out.println("A fatal error has ocurred --> registraPreguntas");
        }
    }

    @Override
    public void registraRespuestas(String respuestaCorrecta, List<String> respuestas, int idPregunta) throws RemoteException {
        try{
            Connection connection = conecta();
            String sentencia = "INSERT INTO Respuesta VALUES (?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentencia);

            st.setInt(1, idPregunta);
            st.setString(2, respuestaCorrecta);
            st.setBoolean(3, true);

            st.executeUpdate();
            System.out.println("Respuesta correcta registrada correctamente.");

            for (String respuesta : respuestas){
                st.setInt(1, idPregunta);
                st.setString(2, respuesta);
                st.setBoolean(3, false);

                st.executeUpdate();
                System.out.println("Respuesta registrada correctamente.");
            }
        }catch (SQLException e){
            System.out.println("A fatal error has ocurred --> registraRespuestas");
        }
    }
}
