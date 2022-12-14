import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadLec extends Thread{
    private Client client;
    private BufferedReader bufferedReader;
    private String message;
    
    public ThreadLec(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        // when a message is received, print it
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.client.getSocketClient().getInputStream()));
            while (this.client.isConnected()) {
                message = bufferedReader.readLine();
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
