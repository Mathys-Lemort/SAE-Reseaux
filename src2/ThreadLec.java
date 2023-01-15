import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadLec implements Runnable{
    private Client client;
    private BufferedReader bufferedReader;
    private String message;
    
    public ThreadLec(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.client.getSocketClient().getInputStream()));
            while (! (client.isClosed())) {
                message = bufferedReader.readLine();
                if (message.equals(Couleur.RED_BOLD+"Fermeture de la connexion"+Couleur.WHITE)){
                    break;
                }
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try {
                client.getSocketClient().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.getThreadEcr().interrupt();
            System.out.println(Couleur.PURPLE_BOLD+"Au revoir!"+Couleur.WHITE);
        }
    }
    
}
