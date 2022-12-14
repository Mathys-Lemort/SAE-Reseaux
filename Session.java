import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Session extends Thread {
    // private ServerSocket server;
    private Socket socketClient;
    private BufferedReader buffeReader;

    public Session( Socket socketc) {
        // this.server = server;
        this.socketClient = socketc;
    }

    @Override
    public void run() {
        try {
            buffeReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            String message = buffeReader.readLine();
            while (message != null) {
                System.out.println(message);
                message = buffeReader.readLine();
            }
            buffeReader.close();
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
