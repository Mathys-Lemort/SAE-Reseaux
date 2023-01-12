import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private boolean connected ;
    private Socket socketClient;
    private Thread threadLec;
    private Thread threadEcr;

    public Client() {
        this.connected = false;
    }
    public boolean isConnected() {
        return connected;
    }
    public Socket getSocketClient() {
        return socketClient;
    }
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    public void connect() {
        try {
            this.socketClient = new Socket("localhost", 4444);
            this.connected = true;
            System.out.println(Couleur.PURPLE_BOLD+"Connexion établie"+Couleur.WHITE);
            PrintWriter printWriter = new PrintWriter(socketClient.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            String recu = in.readLine();
            System.out.println(recu);
            recu = Couleur.RED_BOLD+"Nom d'utilisateur déjà utilisé"+Couleur.WHITE;
            Scanner sc = new Scanner(System.in);
            while (recu.equals(Couleur.RED_BOLD+"Nom d'utilisateur déjà utilisé"+Couleur.WHITE) || 
            recu.equals(Couleur.RED_BOLD+"Nom d'utilisateur invalide"+Couleur.WHITE)) {
                String nom = sc.nextLine();
                printWriter.println(nom);
                printWriter.flush();
                recu = in.readLine();
                System.out.println(recu);
            }
            recu = in.readLine();
            System.out.println(recu);
            recu = in.readLine();
            System.out.println(recu);
            recu = Couleur.RED_BOLD+"Salon inexistant"+Couleur.WHITE;
            while (recu.equals(Couleur.RED_BOLD+"Salon inexistant"+Couleur.WHITE) || recu.equals(Couleur.RED_BOLD+"Entrez un nombre"+Couleur.WHITE) 
            || recu.equals(Couleur.RED_BOLD+"Salon inexistant ou nombre invalide"+Couleur.WHITE)) {
                try{
                int numSalon = sc.nextInt();
                printWriter.println(numSalon);
                printWriter.flush();
                recu = in.readLine();
                System.out.println(recu);
                recu = in.readLine();
                System.out.println(recu);}
                catch (Exception e) {
                    System.out.println(Couleur.RED_BOLD+"Entrez un nombre"+Couleur.WHITE);
                }
            }
            threadEcr=new Thread(new ThreadEcr(this));
            threadLec=new Thread(new ThreadLec(this));
            threadEcr.start();
            threadLec.start(); 
        } catch (IOException e) {
            e.printStackTrace();
            }
    }
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }
   
    
}
