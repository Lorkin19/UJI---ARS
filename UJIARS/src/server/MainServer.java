package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss;
        System.out.println("Inicilizando servidor...");

        try {
            ss = new ServerSocket(1234);
            System.out.println("OK");
            int idSession = 0;
            while (true){
                Socket clientSocket;
                clientSocket = ss.accept();
                System.out.println("Nueva conexión");
                ((ServerThread) new ServerThread(clientSocket, idSession)).start();
                idSession++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
