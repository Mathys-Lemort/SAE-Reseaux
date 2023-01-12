import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadConn extends Thread{
    private Socket socketClient;
    private Server server;

    public ThreadConn(Server server,Socket socketclient){
        this.socketClient = socketclient;
        this.server = server;
    }
    
    @Override
    public void run() {
        try{
        PrintWriter printWriter = new PrintWriter(socketClient.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        printWriter.println("Choisissez votre nom d'utilisateur");
        printWriter.flush();
        String nomutil = "";
        boolean valide = false;
        while (!valide){
            nomutil = in.readLine();
            if (server.getListeNoms().contains(nomutil)) {
                printWriter.println("Nom d'utilisateur déjà utilisé");
                printWriter.flush();
            }
            else{
                printWriter.println(nomutil);
                printWriter.flush();
                server.getListeNoms().add(nomutil);;
                valide = true;}
        }
        printWriter.println("Choisissez un salon (index)");
        printWriter.flush();
        String listeS ="";
        int cpt = 0;
        for (Salon salon : server.getListeSalons()){
            listeS += cpt+" - "+salon.getNomSalon()+" | ";
            cpt++;
        }
        printWriter.println(listeS);
        printWriter.flush();
        printWriter.println("Entrez un nombre");
        printWriter.flush();
        String numSalon = "";
        boolean trouve = false;
        while (! trouve){
            numSalon = in.readLine();
            int num = Integer.parseInt(numSalon);
            try{if (num >= server.getListeSalons().size()) {
                printWriter.println("Salon inexistant");
                printWriter.flush();
            }
            else{
                printWriter.println("Bienvenue sur "+server.getListeSalons().get(Integer.parseInt(numSalon)).getNomSalon()+" "+nomutil+"!");
                printWriter.flush();
                trouve = true;
            }}
            catch (Exception e){
                printWriter.println("Entrez un nombre");
                printWriter.flush();
            }
        }
        Salon salon = server.getListeSalons().get(Integer.parseInt(numSalon));
        Session session = new Session(salon,socketClient, nomutil);
        session.start();
        salon.ajoutSession(session);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
