package server;


import users.Cliente;
import utils.Constants;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSession;
    private HashMap<String, Cliente> usuarios;

    public ServerThread(Socket clienteSocket, int idSession) {
        this.clientSocket = clienteSocket;
        this.idSession = idSession;
        //this.usuarios = usuarios;

        try {
            dos = new DataOutputStream(clientSocket.getOutputStream());
            dis = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String mensaje = "";
        String[] opciones;
        int opcion;

        try {
            mensaje = dis.readUTF();
            opciones = mensaje.split("#");
            opcion = Integer.parseInt(opciones[0]);
            switch (opcion) { // Switch-case base
                case (Constants.iniciaSesion): // profesor que inicia sesion
                    iniciaSesion();
                    break;
                case (Constants.registrar): // profesor que se registra
                    registrar();
                    break;
                case (3): // este seria un alumno que va a entrar en una sala
                    //coger el numero de la sala

                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registrar() throws IOException {
        String user;
        String password;
        dos.writeUTF("Nombre de usuario: ");
        user = dis.readUTF();
        dos.writeUTF("Contraseña: ");
        password = dis.readUTF();
        //TODO consulta a la base de datos en lugar de hashMap
    }

    private void iniciaSesion() throws IOException {
        String user;
        String password;
        dos.writeUTF("Nombre de usuario: ");
        user = dis.readUTF();
        dos.writeUTF("Contraseña: ");
        password = dis.readUTF();
        //TODO consulta a la base de datos en lugar de hashMap
        if (!usuarios.get(user).getPassword().equals(password)){
            dos.writeUTF("Nombre de usuario o contraseña incorrectos");
        } else {
            //TODO enviar a la pantalla correspondiente (alumno / profesor)
        }
    }

}
