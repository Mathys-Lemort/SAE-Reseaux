import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket socketServer;
    private List<Session> sessions;
    
    public Server() {
        this.sessions = new ArrayList<Session>();
        try {
            this.socketServer= new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Session> getSessions() {
        return sessions;
    }
    public void demarrer() {
        while (true) {
            try {
                Socket socketClient = socketServer.accept();
                System.out.println("connexion d’un client");
                Session session = new Session(this,socketClient, "util");
                session.start();
                sessions.add(session);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
            Server server = new Server();
            server.demarrer();
        
    }

}