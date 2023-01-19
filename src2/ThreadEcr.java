import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadEcr implements Runnable{
    private Client client;
    
    public ThreadEcr(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        // read constantly the input stream
        try {
            PrintWriter printWriter = new PrintWriter(this.client.getSocketClient().getOutputStream());
            while (!(client.isClosed())){
                String input =new BufferedReader(new InputStreamReader(System.in)).readLine();
                printWriter.println(input);
                printWriter.flush();
            }
            
        } catch (Exception e) {
            System.out.println(Couleur.RED_BOLD+"Erreur de connexion"+Couleur.WHITE);
        }

}
}
