import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;




public class Session extends Thread {
    private Socket socketClient;
    private PrintWriter printWriter;
    private String nomutil;
    private Server server;


    public Session(Server server, Socket socketc, String nomutil) {
        // this.server = server;
        this.socketClient = socketc;
        this.nomutil = nomutil;
        this.server = server;
        try {
            this.printWriter = new PrintWriter(socketClient.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
    @Override
    public void run() {
        // read constantly the input stream
        try {
            while (true) {
                String output=new BufferedReader(new InputStreamReader(socketClient.getInputStream())).readLine();
                if (output.equals("/quit")) {
                    break;
                }
                String message = output;
                for (Session session : server.getSessions()) {
                    session.getPrintWriter().println(message);
                    session.getPrintWriter().flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socketClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
