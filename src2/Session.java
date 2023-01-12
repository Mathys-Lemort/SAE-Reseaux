import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;




public class Session extends Thread {
    private Socket socketClient;
    private PrintWriter printWriter;
    private String nomutil;
    private Salon salon;


    public Session(Salon server, Socket socketc, String nomutil) {
        this.socketClient = socketc;
        this.nomutil = nomutil;
        this.salon = server;
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
                for (Session session : salon.getSessions()){
                    if (!(this.nomutil.equals(session.getNomutil()))){
                    session.getPrintWriter().println(message);
                    session.getPrintWriter().flush();}
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

    public String getNomutil() {
        return this.nomutil;
    }
    
    
}
