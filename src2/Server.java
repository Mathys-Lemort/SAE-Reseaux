import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket socketServer;
    private List<String> listeNoms = new ArrayList<String>();
    private List<Salon> listeSalons = new ArrayList<Salon>();
    private OutputStream out;
    private boolean ouvert;

    
    public Server() {
        try {
            this.socketServer= new ServerSocket(4444);
            this.ouvert = true;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OutputStream getOut() {
        return out;
    }
    public List<String> getListeNoms() {
        return listeNoms;
    }
    public List<Salon> getListeSalons() {
        return listeSalons;
    }
    public boolean ouvert() {
        return ouvert;
    }
    public void fermer() {
        this.ouvert = false;
        //affiche a tout le monde fermeture en rouge + ferme les clients et fini par interrompre demarrer
    }
    public void demarrer() {
        Thread ecoute = new Thread(new ThreadEcouteServ(this));
        ecoute.start();
        while (this.ouvert) {
            if (!this.ouvert) {
                break;
            }
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