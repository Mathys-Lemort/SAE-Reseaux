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
    private int connEnCours = 0;

    
    public Server() {
        try {
            this.socketServer= new ServerSocket(4444);
            Salon salon1 = new Salon(this,"Salon 1");
            listeSalons.add(salon1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ajouterClient(Socket socketClient) {
        this.connEnCours++;
        Thread threadConn = new Thread(new ThreadConn(this,socketClient));
        threadConn.start();
    }
    public int getConnEnCours() {
        return connEnCours;
    }
    public void ajtConnEnCours() {
        connEnCours++;
    }
    public void finConnEnCours(){
        connEnCours--;
    }
    public List<String> getListeNoms() {
        return listeNoms;
    }
    public List<Salon> getListeSalons() {
        return listeSalons;
    }
    public boolean ouvert() {
        return !(socketServer.isClosed());
    }

    public void fermer() {
        try {
            for (Salon salon : listeSalons) {
                salon.fermer();
            }
            socketServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reconnecter(Session session) {
        Thread threadConn = new Thread(new ThreadReconn(this,session));
        threadConn.start();
    }
    public void demarrer() {
        Thread ecoute = new Thread(new ThreadEcouteServ(this));
        ecoute.start();
        while (!socketServer.isClosed()){
            try{
                if (socketServer.isClosed()) {
                    break;
                }
                Socket socketClient = socketServer.accept();
                System.out.println(Couleur.CYAN_BOLD+"Connexion d’un client"+Couleur.WHITE);
                while (connEnCours >= 3){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ajouterClient(socketClient);
            }
            catch (IOException e) {
                break;
                }
            }
        
        }
    public static void main(String[] args) {
            Server server = new Server();
            server.demarrer();
        
    }    

}