import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadReconn implements Runnable {
    private Session session;
    private Server server;

    public ThreadReconn(Server server,Session session){
        this.session = session;
        this.server = server;
    }
    
    @Override
    public void run() {
        try{
        PrintWriter printWriter = new PrintWriter(session.getSocketClient().getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(session.getSocketClient().getInputStream()));
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
        while (!trouve){
            numSalon = in.readLine();
            try{
                List<String> listeChiffres = new ArrayList<>();
                listeChiffres.addAll(Arrays.asList("0","1","2","3","4","5","6","7","8","9"));
                if (numSalon.equals("") || !(listeChiffres.contains(numSalon.substring(0,1)))){
                    printWriter.println(Couleur.RED_BOLD+"Entrez un nombre"+Couleur.WHITE);
                    printWriter.flush();
                    printWriter.println(Couleur.PURPLE_BOLD+listeS+Couleur.WHITE);
                    printWriter.flush();
                }
                else if (Integer.parseInt(numSalon)-1 < 0 || Integer.parseInt(numSalon)-1 >= server.getListeSalons().size()) {
                printWriter.println(Couleur.RED_BOLD+"Salon inexistant ou nombre invalide"+Couleur.WHITE);
                printWriter.flush();
                printWriter.println(Couleur.PURPLE_BOLD+listeS+Couleur.WHITE);
                printWriter.flush();
                }
                else{
                    printWriter.println(Couleur.PURPLE_BOLD+"Bienvenue sur "+server.getListeSalons().get(Integer.parseInt(numSalon)-1).getNomSalon()+" "+session.getNomUtil()+"!"+Couleur.WHITE);
                    printWriter.flush();
                    printWriter.println(Couleur.PURPLE_BOLD+"Pour quitter le salon, tapez /quit"+Couleur.WHITE);
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
        session.setSalon(salon);
        salon.ajoutSession(session);
        session.run();
        server.finConnEnCours();
        }
        catch (Exception e){
            System.out.println(Couleur.RED_BOLD+"Erreur lors de la connexion"+Couleur.WHITE);
        }
    }
}
