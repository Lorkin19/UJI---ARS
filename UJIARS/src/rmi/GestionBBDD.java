package rmi;

import common.IGestionBBDD;
import javafx.collections.FXCollections;
import modelo.Pregunta;
import modelo.Respuesta;
import modelo.Sesion;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionBBDD implements IGestionBBDD {

    GestionBBDD() {

    }

    /**
     * Se realiza una conexion con la base de datos.
     *
     * @return La conexion con la base de datos.
     */
    private Connection conecta() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:UJIARS\\UJIARSdb.db");
            return connection;

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
    public boolean registraProfesor(String usuario, String password) {
        try {
            Connection connection = conecta();
            String sentencia = "INSERT INTO profesor VALUES (?,?,?)";
            PreparedStatement st = connection.prepareStatement(sentencia);
            st.setString(1, usuario);
            st.setString(2, password);
            st.setString(3, "false");
            st.executeUpdate();
            System.out.println("Profesor creado correctamente");
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("El profesor ya existe");
            return false;
        }
    }

    /**
     * Comprueba que exista un usuario en el sistema con usuario y password indicados.
     *
     * @param usuario  nombre de usuario.
     * @param password password del usuario.
     * @return (true) si existe, (false) si no existe o los datos son erroneos.
     * @throws RemoteException Si hay un error en la conexion remota.
     */
    @Override
    public boolean compruebaProfesor(String usuario, String password) {
        try {
            Connection connection = conecta();
            String sentencia = "SELECT COUNT(*) as existe FROM profesor WHERE usuario=? and password=?";
            PreparedStatement st = connection.prepareStatement(sentencia);
            st.setString(1, usuario);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            boolean existe = rs.getInt("existe") == 1;
            rs.close();
            connection.close();
            return (existe);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dar de baja un profesor de la base de datos.
     *
     * @param usuario Usuario del profesor a dar de baja.
     * @throws RemoteException En caso de que se produzca algun error en la conexion con la base de datos.
     */
    @Override
    public void darDeBajaProgesor(String usuario) {
        try {
            Connection connection = conecta();
            String sentence = "UPDATE Profesor SET baja = ? WHERE usuario = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setBoolean(1, true);
            st.setString(2, usuario);
            st.executeQuery();
        } catch (SQLException e) {
            System.out.println("El profesor con usuario '" + usuario + "' no existe.");
        }
    }

    /**
     * Se registran las preguntas creadas por el profesor sobre un cuestionario de nombre x.
     *
     * @param pregunta           La pregunta que se quiere registrar
     * @param nombreCuestionario El nombre del cuestionario al que pertenece
     * @param usuarioProf        El usuario del profesor al que le corresponden las preguntas
     * @throws RemoteException Si hay algun error en la conexion
     */
    @Override
    public int registraPregunta(Pregunta pregunta, String nombreCuestionario, String usuarioProf) {
        try {
            Connection connection = conecta();
            String sentencia = "INSERT INTO pregunta VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentencia);

            int idPregunta = getNextIdPregunta();

            st.setInt(1, idPregunta);
            st.setString(2, pregunta.getEnunciado().get());
            st.setDouble(3, pregunta.getTiempo());
            st.setString(4, nombreCuestionario);
            st.setInt(5, pregunta.getPuntos());
            st.setString(6, usuarioProf);

            st.executeUpdate();
            System.out.println("\tPregunta guardada correctamente.");

            //registraRespuesta(pregunta.getRespuestas(), idPregunta);
            connection.close();
            return idPregunta;
        } catch (SQLException e) {
            System.out.println("\tA fatal error has ocurred --> registraPreguntas");
        }
        return -1;
    }

    /**
     * Registra las diferentes opciones de respuesta a una pregunta
     *
     * @param respuesta  Opcion de una pregunta
     * @param idPregunta Identificador de la pregunta a la cual corresponden las opciones
     */
    @Override
    public void registraRespuesta(Respuesta respuesta, int idPregunta) {
        try {
            Connection connection = conecta();
            String sentencia = "INSERT INTO Respuesta VALUES (?, ?, ?,?)";
            PreparedStatement st = connection.prepareStatement(sentencia);

            int idRespuesta;
            idRespuesta = getNextIdRespuesta();
            st.setInt(1, idRespuesta);
            st.setInt(2, idPregunta);
            st.setString(3, respuesta.getRespuesta());
            st.setBoolean(4, respuesta.isCorrecta());

            st.executeUpdate();
            System.out.println("\t\tRespuesta registrada correctamente.");
            connection.close();
        } catch (SQLException e) {
            System.out.println("\t\tA fatal error has ocurred --> registraRespuestas");
        }
    }

    /**
     * Obtiene una unica sesion del profesor. Se utiliza en caso de que se quiera ejecutar una
     * sesion recien creada, ya que el servidor no tiene cargada esta sesion puesto que solo
     * se tiene en local y en la bbdd.
     *
     * @param usuario      Nombre de usuario del profesor.
     * @param nombreSesion Nombre de la sesion que se desea consultar.
     * @return La sesion a ejecutar.
     */
    public Sesion getSesionProfesor(String usuario, String nombreSesion) {
        try {
            System.out.println("(GestionBBDD.getSesionProfesor) Llamada al metodo");
            Sesion sesion = new Sesion(nombreSesion);
            List<Pregunta> preguntas = new ArrayList<>();
            Connection connection = conecta();
            String sentencia = "SELECT * FROM Pregunta WHERE usuario = ? AND nombreCuestionario = ?";
            PreparedStatement st = connection.prepareStatement(sentencia);
            st.setString(1, usuario);
            st.setString(2, nombreSesion);
            ResultSet rs = st.executeQuery();

            Pregunta pregunta;
            List<Respuesta> respuestas;
            String sentencia2 = "SELECT opcion, correcta FROM Respuesta WHERE idPregunta = ?";
            st = connection.prepareStatement(sentencia2);
            ResultSet rs2;
            while (rs.next()) {
                pregunta = new Pregunta();
                int idPregunta = rs.getInt("idPregunta");
                pregunta.setEnunciado(rs.getString("enunciado"));
                st.setInt(1, idPregunta);
                rs2 = st.executeQuery();
                respuestas = new ArrayList<>();
                getRespuestas(respuestas, rs2);
                pregunta.setRespuestas(respuestas);
                pregunta.setPuntos(rs.getInt("puntos"));
                pregunta.setTiempo(rs.getDouble("tiempo"));
                preguntas.add(pregunta);
            }
            connection.close();
            return sesion;
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al consultar la sesion");
            return null;
        }
    }

    /**
     * Se obtienen los cuestionarios creador por un profesor dado.
     *
     * @param usuario El nombre de usuario del pro fesor del cual se quieren obtener sus cuestionarios
     * @return Un mapa en el que se encuentran el nombre del cuestionario y el listado de preguntas asociadas al mismo.
     */
    @Override
    public List<Sesion> getSesionesProfesor(String usuario) {
        List<Sesion> misSesiones = new ArrayList<>();
        Map<String, List<Pregunta>> result = new HashMap<>();
        try {
            getPreguntasProfesor(result, usuario);
            toSesion(misSesiones, result);
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al obtener los cuestionarios del profesor");
            e.printStackTrace();
        }
        System.out.println("Devolviendo las sesiones del profesor " + usuario);
        return misSesiones;
    }

    /*
    /**
     * Metodo que permite obtener un profesor a partir de un usuario
     * @param usuario el nombre de usuario dado para obtener el profesor de la base de datos
     * @return el profesor obtenido de la bbdd o null en el caso de que no se encuentre el profesor
     * @throws RemoteException En el caso de que algo falle.
     *
    @Override
    public Profesor getProfesor(String usuario) {
        try {
            Connection connection = conecta();
            String sentence = "SELECT * from Profesor WHERE usuario = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setString(1, usuario);

            ResultSet rs = st.executeQuery();
            Profesor p = new Profesor();

            p.setUsuario(rs.getString("usuario"));
            p.setPassword(rs.getString("password"));
            p.setMisSesiones(getSesionesProfesor(usuario));

            connection.close();
            return p;

        }catch (SQLException e){
            System.out.println("El profesor no existe.");
            return null;
        }
    }
    */

    /**
     * Permite editar una pregunta ya registrada.
     *
     * @param pregunta           Los datos de la pregunta modificada.
     * @param usuarioProf        El usuario al que pertenece la pregunta.
     * @param nombreCuestionario El cuestionario al que pertenece la pregunta.
     * @throws RemoteException En el caso de que haya algun error en la conexion.
     */
    @Override
    public void editaPregunta(Pregunta pregunta, String usuarioProf, String nombreCuestionario) {
        try {
            Connection connection = conecta();
            String sentence = "UPDATE Pregunta SET enunciado = ?, tiempo = ?, puntos = ? WHERE usario = ? AND nombreCuestionario = ? AND enunciado = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setString(1, pregunta.getEnunciado().get());
            st.setDouble(2, pregunta.getTiempo());
            st.setInt(3, pregunta.getPuntos());
            st.setString(4, usuarioProf);
            st.setString(5, nombreCuestionario);
            st.setString(6, pregunta.getEnunciado().get());

            st.executeQuery();
            connection.close();
        } catch (SQLException e) {
            System.out.println("La pregunta no fue encontrada.");
        }
    }

    /**
     * Permite eliminar una pregunta registrada.
     *
     * @param pregunta           Pregunta que quiere eliminar.
     * @param usuarioProf        Usuario al que pertenece la pregunta.
     * @param nombreCuestionario Cuestionario al que pertenece la pregunta.
     * @throws RemoteException En el caso de que haya algun error en la conexion con la base de datos.
     */
    @Override
    public void eliminaPregunta(Pregunta pregunta, String usuarioProf, String nombreCuestionario) {
        try {
            Connection connection = conecta();
            String sentence = "DELETE FROM Pregunta WHERE usario = ? AND nombreCuestionario = ? AND enunciado = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setString(1, usuarioProf);
            st.setString(2, nombreCuestionario);
            st.setString(3, pregunta.getEnunciado().get());

            st.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println("La pregunta no fue encontrada.");
        }
    }

    /**
     * Obtenemos la cantidad de sesiones que tiene un profesor para saber cuantas veces
     * hay que llamar a la funcion getSesionProfesor a la hora de cargar todas las sesiones.
     *
     * @param usuario Nombre de usuario del profesor.
     * @return Numero de sesiones (cuestionarios) que tiene un profesor.
     */
    @Override
    public int getNumSesionesProfesor(String usuario) {
        try {
            Connection connection = conecta();
            String sentence = "SELECT COUNT(DISTINCT nombreCuestionario) as numSesiones WHERE usuario = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setString(1, usuario);
            ResultSet rs = st.executeQuery();

            int numSesiones = rs.getInt("numSesiones");
            rs.close();
            connection.close();
            return numSesiones;
        } catch (SQLException e) {
            System.out.println("Error: No se ha podido obtener el dato de la BBDD.");
            return -1;
        }
    }

    /**
     * Obtener las respuestas de una pregunta dada
     *
     * @param respuestas Resto de respuestas incorrectas de la pregunta
     * @param rs2        Resultado de la ejecucion de la sentencia SQL
     * @return Respuesta correcta que se asigna a la pregunta
     * @throws SQLException En el caso de que haya algun error en la obtencion de los parametros a partir del resultado
     *                      de la sentencia SQL
     */
    private void getRespuestas(List<Respuesta> respuestas, ResultSet rs2) throws SQLException {
        String respuesta;
        boolean correcta;
        while (rs2.next()) {
            respuesta = rs2.getString("opcion");
            correcta = rs2.getBoolean("correcta");


            respuestas.add(new Respuesta(respuesta, correcta));
            /*
            if (correcta) {
                respuestaCorrecta = respuesta;
            } else {
                respuestas.add(respuesta);
            }
            */
        }
        //return respuestaCorrecta;
    }

    /**
     * Obtener el ultimo id de las preguntas que hay en la base de datos.
     *
     * @return el id de la ultima pregunta anyadida +1
     */
    private int getNextIdPregunta() {
        try {
            Connection connection = conecta();
            String sentencia = "SELECT idPregunta FROM Pregunta ORDER BY idPregunta DESC";
            PreparedStatement st = connection.prepareStatement(sentencia);

            ResultSet rs = st.executeQuery();
            int idPregunta = rs.getInt("idPregunta");
            rs.close();

            connection.close();
            return idPregunta + 1;
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al obtener el último id de las preguntas");
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Obtener el ultimo id de las respuestas que hay en la base de datos.
     *
     * @return el id de la ultima respuesta anyadida +1
     */
    private int getNextIdRespuesta() {
        try {
            Connection connection = conecta();
            String sentencia = "SELECT idRespuesta FROM Respuesta ORDER BY idRespuesta DESC";
            PreparedStatement st = connection.prepareStatement(sentencia);

            ResultSet rs = st.executeQuery();
            int idRespuesta = rs.getInt("idRespuesta");
            rs.close();

            connection.close();
            return idRespuesta + 1;
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al obtener el último id de las respuestas");
            return 0;
        }
    }

    /**
     * Se convierte del mapa obtenido del metodo getPreguntasProfesor a un listado de las sesiones del profesor.
     *
     * @param misSesiones Listado al cual se quieren anyadir el resultado de obtener el mapa de sesiones y preguntas
     * @param result      Mapa que contiene el nombre de las sesiones y las preguntas correspondientes a cada sesion
     */
    private void toSesion(List<Sesion> misSesiones, Map<String, List<Pregunta>> result) {
        for (String sesion : result.keySet()) {
            Sesion s = new Sesion(sesion);
            s.setListaPreguntas(FXCollections.observableArrayList(result.get(sesion)));

            misSesiones.add(s);
        }
    }

    /**
     * Obtiene el listado de preguntas separadas por el nombre de la sesion a la que pertenecen.
     *
     * @param result  Mapa donde se guarda el nombre  de la sesion y las preguntas correspondientes.
     * @param usuario Nombre del profesor del cual se quieren obtener las preguntas.
     * @throws SQLException En el caso de que haya algun error.
     */
    private void getPreguntasProfesor(Map<String, List<Pregunta>> result, String usuario) throws SQLException {
        Connection connection = conecta();
        String sentence = "SELECT * FROM Pregunta WHERE usuario = ? GROUP BY nombreCuestionario";
        PreparedStatement st = connection.prepareStatement(sentence);

        st.setString(1, usuario);

        ResultSet rs = st.executeQuery();
        Pregunta p;

        String cuestionario;
        int idPregunta;
        List<Respuesta> respuestas;
        List<Pregunta> listPreguntas;

        sentence = "SELECT opcion, correcta FROM Respuesta WHERE idPregunta = ?";
        st = connection.prepareStatement(sentence);

        while (rs.next()) {
            idPregunta = rs.getInt("idPregunta");
            cuestionario = rs.getString("nombreCuestionario");

            st.setInt(1, idPregunta);
            ResultSet rs2 = st.executeQuery();

            respuestas = new ArrayList<>();
            getRespuestas(respuestas, rs2);

            p = new Pregunta();
            p.setEnunciado(rs.getString("enunciado"));
            p.setRespuestas(respuestas);
            p.setTiempo(rs.getDouble("tiempo"));
            p.setPuntos(rs.getInt("puntos"));

            result.computeIfAbsent(cuestionario, k -> new ArrayList<>());
            listPreguntas = result.get(cuestionario);
            listPreguntas.add(p);
            result.put(cuestionario, listPreguntas);
        }
        connection.close();
    }
}
