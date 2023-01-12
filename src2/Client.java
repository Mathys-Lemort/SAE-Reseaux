import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private boolean connected ;
    private Socket socketClient;

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
            System.out.println("connexion établie");
            PrintWriter printWriter = new PrintWriter(socketClient.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            String recu = in.readLine();
            System.out.println(recu);
            recu = "Nom d'utilisateur déjà utilisé";
            while (recu.equals("Nom d'utilisateur déjà utilisé")) {
                Scanner sc = new Scanner(System.in);
                String nom = sc.nextLine();
                printWriter.println(nom);
                printWriter.flush();
                recu = in.readLine();
                if (recu.equals("Nom d'utilisateur déjà utilisé")) {
                    System.out.println(recu);
                }
            }
            String nom=recu;
            recu = in.readLine();
            System.out.println(recu);
            recu = in.readLine();
            System.out.println(recu);
            recu = in.readLine();
            System.out.println(recu);
            recu = "Salon inexistant";
            while (recu.equals("Salon inexistant") || recu.equals("Entrez un nombre")) {
                Scanner sc = new Scanner(System.in);
                int numSalon = sc.nextInt();
                printWriter.println(numSalon);
                printWriter.flush();
                recu = in.readLine();
                System.out.println(recu);
            }
            ThreadEcr ecr=new ThreadEcr(this, nom);
            ThreadLec lec=new ThreadLec(this);
            ecr.start();
            lec.start();
        } catch (IOException e) {
            e.printStackTrace();
            }
    }
    public void disconnect() {
        try {
            this.socketClient.close();
            this.connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }
   
    
}
