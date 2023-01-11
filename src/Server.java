import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket socketServer;
    private List<Session> sessions;
    private List<String> listeNoms = new ArrayList<String>();
    
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
                PrintWriter printWriter = new PrintWriter(socketClient.getOutputStream());
                printWriter.println("Confirmez votre nom d'utilisateur");
                printWriter.flush();
                String nomutil ="";
                while (nomutil.equals("") && listeNoms.contains(nomutil)){
                    BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                    nomutil = in.readLine();
                    if (listeNoms.contains(nomutil)) {
                        printWriter.println("Nom d'utilisateur déjà utilisé");
                        printWriter.flush();
                    }
                    else{
                        System.out.println(listeNoms);
                        printWriter.println("Bienvenue "+nomutil);
                        printWriter.flush();
                        listeNoms.add(nomutil);}
                }
                Session session = new Session(this,socketClient, nomutil);
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