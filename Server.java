import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket socketServer = new ServerSocket(4444);
            while (true) {
                Socket socketClient = socketServer.accept();
                System.out.println("connexion d’un client");
                Session session = new Session(socketServer,socketClient);
                session.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
    }
}