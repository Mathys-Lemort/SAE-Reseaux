import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socketClient = new Socket("localhost", 4444);
            System.out.println("connexion au serveur");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socketClient.getOutputStream());
            String message = bufferedReader.readLine();
            while (message != null) {
                System.out.println(message);
                message = bufferedReader.readLine();
            }
            bufferedReader.close();
            printWriter.close();
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
