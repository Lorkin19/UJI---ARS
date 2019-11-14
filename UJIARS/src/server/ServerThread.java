package server;


import users.Cliente;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSession;
    private HashMap<String, Cliente> usuarios;

    public ServerThread(Socket clienteSocket, int idSession, HashMap usuarios) {
        this.clientSocket = clienteSocket;
        this.idSession = idSession;
        this.usuarios = usuarios;

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
        String user;
        String password;

        try {
            mensaje = dis.readUTF();
            opciones = mensaje.split("#");
            switch (opciones[0]) {
                case ("iniciaSesion"):
                    dos.writeUTF("Nombre de usuario: ");
                    user = dis.readUTF();
                    dos.writeUTF("Contraseña: ");
                    password = dis.readUTF();
                    //TODO consulta a la base de datos en lugar de hashMap
                    if (!usuarios.get(user).getPassword().equals(password)){
                        dos.writeUTF("Nombre de usuario o contraseña incorrectos");
                    }else {
                        //TODO enviar a la pantalla correspondiente (alumno / profesor)
                    }
                    break;
                case ("Registrar"):
                    dos.writeUTF("Nombre de usuario: ");
                    user = dis.readUTF();
                    dos.writeUTF("Contraseña: ");
                    password = dis.readUTF();
                    //TODO consulta a la base de datos en lugar de hashMap
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
