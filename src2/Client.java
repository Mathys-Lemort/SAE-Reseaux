import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socketClient;
    private Thread threadLec;
    private Thread threadEcr;

    public Client() {
    }
    public Socket getSocketClient() {
        return socketClient;
    }
    public boolean isClosed() {
        return socketClient.isClosed();
    }
    public Thread getThreadLec() {
        return threadLec;
    }
    public Thread getThreadEcr() {
        return threadEcr;
    }
    public void connect() {
        try {
            this.socketClient = new Socket("localhost", 4444);
            System.out.println(Couleur.PURPLE_BOLD+"Connexion établie"+Couleur.WHITE);
            PrintWriter printWriter = new PrintWriter(socketClient.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            String recu = in.readLine();
            System.out.println(recu);
            recu = Couleur.RED_BOLD+"Nom";
            Scanner sc = new Scanner(System.in);
            while (recu.startsWith(Couleur.RED_BOLD+"Nom")) {
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
            while (!(recu.startsWith(Couleur.PURPLE_BOLD+"Pour"))){
                try{
                String numSalon = sc.nextLine();
                printWriter.println(numSalon);
                printWriter.flush();
                recu = in.readLine();
                System.out.println(recu);
                recu = in.readLine();
                System.out.println(recu);}
                catch (Exception e) {
                    printWriter.println(Couleur.RED_BOLD+"Entrez un nombre"+Couleur.WHITE);
                }
            }
            threadLec = new Thread(new ThreadLec(this));
            threadEcr = new Thread(new ThreadEcr(this));
            threadLec.start();
            threadEcr.start();
        } catch (Exception e) {
            System.out.println(Couleur.RED_BOLD+"Erreur lors de la connexion"+Couleur.WHITE);
            }
    }
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }
   
    
}
