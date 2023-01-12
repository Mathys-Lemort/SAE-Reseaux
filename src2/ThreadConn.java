import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadConn implements Runnable{
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
        printWriter.println(Couleur.GREEN_BOLD+"Choisissez votre nom d'utilisateur"+Couleur.WHITE);
        printWriter.flush();
        String nomutil = "";
        boolean valide = false;
        while (!valide){
            nomutil = in.readLine();
            if (server.getListeNoms().contains(nomutil)) {
                printWriter.println(Couleur.RED_BOLD+"Nom d'utilisateur déjà utilisé"+Couleur.WHITE);
                printWriter.flush();
            }
            else if (nomutil.equals("") || nomutil.contains(" ")){
                printWriter.println(Couleur.RED_BOLD+"Nom d'utilisateur invalide"+Couleur.WHITE);
                printWriter.flush();
            }
            else{
                server.getListeNoms().add(nomutil);;
                valide = true;}
        }
        printWriter.println(Couleur.GREEN_BOLD+"Choisissez un salon (index)"+Couleur.WHITE);
        printWriter.flush();
        String listeS ="";
        int cpt = 1;
        for (Salon salon : server.getListeSalons()){
            listeS += cpt+" - "+salon.getNomSalon()+" | ";
            cpt++;
        }
        printWriter.println(Couleur.PURPLE_BOLD+listeS+Couleur.WHITE);
        printWriter.flush();
        printWriter.println(Couleur.GREEN_BOLD+"Entrez un nombre"+Couleur.WHITE);
        printWriter.flush();
        String numSalon = "";
        boolean trouve = false;
        while (! trouve){
            numSalon = in.readLine();
            int num = Integer.parseInt(numSalon)-1;
            try{if (num < 0 || num >= server.getListeSalons().size() || numSalon.equals("")) {
                printWriter.println(Couleur.RED_BOLD+"Salon inexistant ou nombre invalide"+Couleur.WHITE);
                printWriter.flush();
                printWriter.println(Couleur.PURPLE_BOLD+listeS+Couleur.WHITE);
                printWriter.flush();
            }
            else{
                printWriter.println(Couleur.PURPLE_BOLD+"Bienvenue sur "+server.getListeSalons().get(num).getNomSalon()+" "+nomutil+"!"+Couleur.WHITE);
                printWriter.flush();
                trouve = true;
            }}
            catch (Exception e){
                e.printStackTrace();
                printWriter.println(Couleur.RED_BOLD+"Entrez un nombre"+Couleur.WHITE);
                printWriter.flush();
                printWriter.println(Couleur.PURPLE_BOLD+listeS+Couleur.WHITE);
                printWriter.flush();
            }
        }
        Salon salon = server.getListeSalons().get(Integer.parseInt(numSalon)-1);
        Session session = new Session(salon,socketClient, nomutil);
        session.start();
        salon.ajoutSession(session);
        server.finConnEnCours();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
