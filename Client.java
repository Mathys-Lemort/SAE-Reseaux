import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            System.out.println("Choisir un nom d'utilisateur");
            // lit la ligne suivante pour le nom d'utilisateur
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String nomutil = in.readLine();
            ThreadEcr ecr=new ThreadEcr(this,nomutil);
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
