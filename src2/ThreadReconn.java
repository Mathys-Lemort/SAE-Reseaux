import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
        printWriter.println(Couleur.PURPLE_BOLD+"Choisissez un salon (index)"+Couleur.WHITE);
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
            try{if (num >= server.getListeSalons().size()) {
                printWriter.println(Couleur.RED_BOLD+"Salon inexistant"+Couleur.WHITE);
                printWriter.flush();
                }
            else{
                printWriter.println(Couleur.PURPLE_BOLD+"Bienvenue sur "+server.getListeSalons().get(Integer.parseInt(numSalon)).getNomSalon()+" "+session.getNomUtil()+"!"+Couleur.WHITE);
                printWriter.flush();
                trouve = true;
            }}
            catch (Exception e){
                printWriter.println(Couleur.RED_BOLD+"Entrez un nombre"+Couleur.WHITE);
                printWriter.flush();
            }
        }
        Salon salon = server.getListeSalons().get(Integer.parseInt(numSalon));
        session.setSalon(salon);
        salon.ajoutSession(session);
        session.start();
        server.finConnEnCours();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
