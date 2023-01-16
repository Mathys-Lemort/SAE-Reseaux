import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadLec implements Runnable{
    private Client client;
    private BufferedReader bufferedReader;
    private String message;
    private boolean decoForcee = false;
    
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
                    decoForcee = true;
                    break;
                }
                System.out.println(message);
            }
        } catch (Exception e) {
        }
        finally{
            try {
                client.getSocketClient().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.getThreadEcr().interrupt();
            if (decoForcee){
                System.out.println(Couleur.PURPLE_BOLD+"Au revoir, appuyez sur entrée pour finir"+Couleur.WHITE);
            }
            else{
            System.out.println(Couleur.PURPLE_BOLD+"Au revoir!"+Couleur.WHITE);}
        }
    }
    
}
