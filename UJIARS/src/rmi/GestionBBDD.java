package rmi;

import common.IGestionBBDD;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
