package rmi;

import common.IGestionBBDD;
import users.Pregunta;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionBBDD extends UnicastRemoteObject implements IGestionBBDD {

    GestionBBDD() throws RemoteException {

    }

    /**
     * Se realiza una conexion con la base de datos.
     *
     * @return La conexion con la base de datos.
     */
    private Connection conecta() {
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
     *
     * @param usuario  Nombre de usuario del profesor
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

    /**
     * Se registran las preguntas creadas por el profesor sobre un cuestionario de nombre x.
     *
     * @param pregunta       La pregunta que se quiere registrar
     * @param nombreConjunto El nombre del cuestionario al que pertenece
     * @param usuarioProf    El usuario del profesor al que le corresponden las preguntas
     * @throws RemoteException Si hay algun error en la conexion
     */
    @Override
    public void registraPreguntas(Pregunta pregunta, String nombreConjunto, String usuarioProf) throws RemoteException {
        try {
            Connection connection = conecta();
            String sentencia = "INSERT INTO pregunta VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentencia);

            st.setString(1, pregunta.getEnunciado());
            st.setDouble(2, pregunta.getTiempo());
            st.setInt(3, pregunta.getPuntos());
            st.setString(4, nombreConjunto);
            st.setString(5, usuarioProf);

            st.executeUpdate();
            System.out.println("Pregunta guardada correctamente.");

            int idPregunta = getLastIdPregunta();
            registraRespuestas(pregunta.getRespuestaCorrecta(), pregunta.getRespuestas(), idPregunta);
        } catch (SQLException e) {
            System.out.println("A fatal error has ocurred --> registraPreguntas");
        }
    }

    /**
     * Registra las diferentes opciones de respuesta a una pregunta
     *
     * @param respuestaCorrecta La opcion correcta como respuesta a la pregunta
     * @param respuestas        Listado de opciones incorrectas
     * @param idPregunta        Identificador de la pregunta a la cual corresponden las opciones
     * @throws RemoteException Si hay algun error en la conexion
     */
    @Override
    public void registraRespuestas(String respuestaCorrecta, List<String> respuestas, int idPregunta) throws RemoteException {
        try {
            Connection connection = conecta();
            String sentencia = "INSERT INTO Respuesta VALUES (?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentencia);

            st.setInt(1, idPregunta);
            st.setString(2, respuestaCorrecta);
            st.setBoolean(3, true);

            st.executeUpdate();
            System.out.println("Respuesta correcta registrada correctamente.");

            for (String respuesta : respuestas) {
                st.setInt(1, idPregunta);
                st.setString(2, respuesta);
                st.setBoolean(3, false);

                st.executeUpdate();
                System.out.println("Respuesta registrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("A fatal error has ocurred --> registraRespuestas");
        }
    }

    /**
     * Se obtienen los cuestionarios creador por un profesor dado.
     *
     * @param usuario El nombre de usuario del profesor del cual se quieren obtener sus cuestionarios
     * @return Un mapa en el que se encuentran el nombre del cuestionario y el listado de preguntas asociadas al mismo.
     * @throws RemoteException En el caso de que haya algun error en la conexion con la base de datos
     */
    @Override
    public Map<String, List<Pregunta>> getProfessorCuestionarios(String usuario) {
        Map<String, List<Pregunta>> result = new HashMap<>();
        try {
            Connection connection = conecta();
            String sentence = "SELECT * FROM Preguntas WHERE usuario = ? GROUP BY nombreCuestionario";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setString(1, usuario);

            ResultSet rs = st.executeQuery();
            Pregunta p = new Pregunta();

            String cuestionario;
            int idPregunta;
            String respuestaCorrecta = "";
            List<String> respuestas = new ArrayList<>();
            List<Pregunta> listPreguntas;

            sentence = "SELECT opcion, correcta FROM Respuesta WHERE idPregunta = ?";
            st = connection.prepareStatement(sentence);

            while (rs.next()) {
                idPregunta = rs.getInt("idPregunta");
                cuestionario = rs.getString("nombreCuestionario");


                st.setInt(1, idPregunta);
                ResultSet rs2 = st.executeQuery();

                respuestaCorrecta = getRespuestas(respuestaCorrecta, respuestas, rs2);

                p.setEnunciado(rs.getString("enunciado"));
                p.setRespuestaCorrecta(respuestaCorrecta);
                p.setRespuestas(respuestas);
                p.setTiempo(rs.getDouble("tiempo"));
                p.setPuntos(rs.getInt("puntos"));

                result.computeIfAbsent(cuestionario, k -> new ArrayList<>());
                listPreguntas = result.get(cuestionario);
                listPreguntas.add(p);
                result.put(cuestionario, listPreguntas);
            }


        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al obtener los cuestionarios del profesor");
        }

        return result;
    }

    /**
     * Obtener las respuestas de una pregunta dada
     * @param respuestaCorrecta Respuesta correcta de la pregunta
     * @param respuestas Resto de respuestas incorrectas de la pregunta
     * @param rs2 Resultado de la ejecucion de la sentencia SQL
     * @return Respuesta correcta que se asigna a la pregunta
     * @throws SQLException En el caso de que haya algun error en la obtencion de los parametros a partir del resultado
     * de la sentencia SQL
     */
    private String getRespuestas(String respuestaCorrecta, List<String> respuestas, ResultSet rs2) throws SQLException {
        String respuesta;
        boolean correcta;
        while (rs2.next()) {
            respuesta = rs2.getString("opcion");
            correcta = rs2.getBoolean("correcta");

            if (correcta) {
                respuestaCorrecta = respuesta;
            } else {
                respuestas.add(respuesta);
            }
        }
        return respuestaCorrecta;
    }

    /**
     * Obtener el ultimo id de las preguntas que hay en la base de datos.
     *
     * @return el id de la ultima pregunta anyadida
     */
    private int getLastIdPregunta() {
        try {
            Connection connection = conecta();
            String sentencia = "SELECT TOP 1 idPregunta FROM Preguntas ORDER BY idPregunta DESC";
            PreparedStatement st = connection.prepareStatement(sentencia);

            ResultSet rs = st.executeQuery();
            int idPregunta = rs.getInt("idPregunta");

            return idPregunta;
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al obtener el último id de las preguntas");
            return -1;
        }
    }
}
