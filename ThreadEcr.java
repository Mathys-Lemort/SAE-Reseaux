import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadEcr extends Thread{
    private Client client;
    private PrintWriter printWriter;
    private String message;
    private String nomutil;
    
    public ThreadEcr(Client client, String nomutil) {
        this.client = client;
        this.nomutil = nomutil;
    }

    @Override
    public void run() {
        // read constantly the input stream
        try {
            printWriter = new PrintWriter(this.client.getSocketClient().getOutputStream());
            while (this.client.isConnected()) {
                String input =new BufferedReader(new InputStreamReader(System.in)).readLine();
                if (input.equals("/quit")) {
                    this.client.setConnected(false);
                }
                message = nomutil + " : " + input;
                printWriter.println(message);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            this.client.disconnect();
            
        }

}
}
