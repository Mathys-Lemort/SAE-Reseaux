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


    public Session(Salon salon, Socket socketc, String nomutil) {
        this.socketClient = socketc;
        this.nomutil = nomutil;
        this.salon = salon;
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
                else if (output.equals("/nbuser")){
                    printWriter.println("Il y a " + salon.getSessions().size() + " utilisateurs connectés");
                    printWriter.flush();
                }
                else if (output.equals("/users")){
                    String liste = "Utilisateurs connectés "+salon.getNomsUtils();
                    printWriter.println(liste);
                    printWriter.flush();
                }
                else if (output.equals("uptime")){
                    String temps = ""+salon.getUptime();
                    printWriter.println(temps);
                    printWriter.flush();
                }
                else if (output.equals("/help")){
                    String liste = "Commandes disponibles : /quit, /nbuser, /users, uptime, /help, @nomutil message";
                    printWriter.println(liste);
                    printWriter.flush();
                }
                else if (output.startsWith("@")){
                    String[] parts = output.split(" ", 2);
                    String message = parts[1];
                    String send = this.nomutil;
                    String nom = parts[0].substring(1);
                    for (Session session : salon.getSessions()){
                        if (nom.equals(session.getNomUtil())){
                        session.getPrintWriter().println(Couleur.YELLOW_BOLD+"[message privé de "+send + "] " + message);
                        session.getPrintWriter().flush();}
                    }
                }
                else{
                String message = output;
                for (Session session : salon.getSessions()){
                    if (!(this.nomutil.equals(session.getNomUtil()))){
                    session.getPrintWriter().println(message);
                    session.getPrintWriter().flush();}
                }}
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

    public String getNomUtil() {
        return this.nomutil;
    }
    
    
}
