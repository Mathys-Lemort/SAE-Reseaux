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
    private List<Session> sessions= new ArrayList<Session>();
    private List<String> listeNoms = new ArrayList<String>();
    private List<String> listeNomsSalons = new ArrayList<String>();
    private List<Salon> listeSalons = new ArrayList<Salon>();

    
    public Server() {
        Salon s1 = new Salon(this, "Salon1");
        Salon s2 = new Salon(this, "Salon2");
        Salon s3 = new Salon(this, "Salon3");
        listeSalons.add(s1);
        listeSalons.add(s2);
        listeSalons.add(s3);
        try {
            this.socketServer= new ServerSocket(4444);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Session> getSessions() {
        return sessions;
    }
    public List<String> getListeNoms() {
        return listeNoms;
    }
    public List<Salon> getListeSalons() {
        return listeSalons;
    }
    public List<String> getListeNomsSalons() {
        return listeNomsSalons;
    }
    public void demarrer() {
        while (true) {
            try{
                Socket socketClient = socketServer.accept();
                System.out.println("connexion d’un client");
                ThreadConn threadConn = new ThreadConn(this,socketClient);
                threadConn.start();}
            catch (IOException e) {
                e.printStackTrace();
            }

               
        }
    }
    public static void main(String[] args) {
            Server server = new Server();
            server.demarrer();
        
    }

}