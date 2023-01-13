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
            System.out.println(Couleur.CYAN_BOLD+"Entrez votre commande:"+Couleur.WHITE);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String input = in.readLine();
            if (input.equals("/quit")){
                break;
            }
            else if (input.equals("/addS")){
                System.out.println(Couleur.GREEN_BOLD+"Nom du salon à ajouter: "+Couleur.WHITE);
                String nom = in.readLine();
                Salon salon = new Salon(server,nom);
                server.getListeSalons().add(salon);
                System.out.println(Couleur.GREEN_BOLD+"Salon ajouté"+Couleur.WHITE);
            }
            else if (input.equals("/salons")){
                System.out.println(Couleur.CYAN_BOLD+"Salons disponibles: "+Couleur.WHITE);
                for (Salon s : server.getListeSalons()){
                    System.out.println(Couleur.CYAN_BOLD+s.getNomSalon()+Couleur.WHITE);
                }
            }
            else if (input.equals("/delS")){
                System.out.println(Couleur.CYAN_BOLD+"Salons disponibles: "+Couleur.WHITE);
                for (Salon s : server.getListeSalons()){
                    System.out.println(Couleur.CYAN_BOLD+s.getNomSalon()+Couleur.WHITE);
                }
                System.out.println(Couleur.GREEN_BOLD+"Nom du salon à supprimer: "+Couleur.WHITE);
                String nom = in.readLine();
                Salon salon = null;
                for (Salon s : server.getListeSalons()){
                    if (s.getNomSalon().equals(nom)){
                        salon = s;
                    }
                }
                if (salon != null){
                server.getListeSalons().remove(salon);
                System.out.println(Couleur.YELLOW_BOLD+"Salon supprimé"+Couleur.WHITE);}
                else{
                    System.out.println(Couleur.RED_BOLD+"Salon inexistant"+Couleur.WHITE);
                }
            }
            else if (input.equals("/help")){
                System.out.println(Couleur.CYAN_BOLD+"Commandes disponibles : /quit, /addS, /salons, /delS, /help"+Couleur.WHITE);
            }
            else{
                System.out.println(Couleur.RED_BOLD+"Commande inconnue"+Couleur.WHITE);
            }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(Couleur.RED_BOLD+"Fermeture du serveur"+Couleur.WHITE);
        server.fermer();
        }
    }

