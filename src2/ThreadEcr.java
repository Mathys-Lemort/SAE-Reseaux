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
            while (this.client.isConnected()) {
                String message = "";
                String input =new BufferedReader(new InputStreamReader(System.in)).readLine();
                String[] commandes = new String[] {"/nbuser", "/users", "uptime", "/help"};
                if (input.equals("/quit") || input.equals("null")) {
                    break;
                }
                // si commence par @
                else if (input.startsWith("@")){
                    message = input;
                    printWriter.println(message);
                    printWriter.flush();
                }
                
                else if (!java.util.Arrays.asList(commandes).contains(input)){
                    message = input;
                    printWriter.println(message);
                    printWriter.flush();
                }
                else{
                    printWriter.println(input);
                    printWriter.flush();
                }
            }
            
        } catch (IOException e) {
            System.out.println(Couleur.RED_BOLD+"Erreur de connexion"+Couleur.WHITE);
        }
        finally {            
        }

}
}
