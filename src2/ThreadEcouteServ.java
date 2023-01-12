import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadEcouteServ implements Runnable{
    private Server server;

    public ThreadEcouteServ(Server s){
        this.server = s;
    }

    @Override
    public void run() {
        // read constantly the input stream waiting for commands
        while (server.ouvert()){
            try{
            System.out.println("Attente de commande");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String input = in.readLine();
            if (input.equals("/quit")){
                server.fermer();
            }
            else if (input.equals("/addS")){
                System.out.println("Nom du salon à ajouter: ");
                String nom = in.readLine();
                Salon salon = new Salon(server,nom);
                server.getListeSalons().add(salon);
            }
            else if (input.equals("/salons")){
                System.out.println("Salons disponibles: ");
                for (Salon s : server.getListeSalons()){
                    System.out.println(s.getNomSalon());
                }
            }
            else if (input.equals("/delS")){
                System.out.println("Salons disponibles: ");
                for (Salon s : server.getListeSalons()){
                    System.out.println(s.getNomSalon());
                }
                System.out.println("Nom du salon à supprimer: ");
                String nom = in.readLine();
                for (Salon s : server.getListeSalons()){
                    if (s.getNomSalon().equals(nom)){
                        server.getListeSalons().remove(s);
                    }
                }
            }
            else if (input.equals("/help")){
                System.out.println("Commandes disponibles : /quit, /addS, /salons, /delS, /help");
            }
            else{
                System.out.println("Commande inconnue");
        }}
            catch (Exception e){
                e.printStackTrace();
            }
        }}
}
