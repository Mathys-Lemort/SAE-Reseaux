import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
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
            e.printStackTrace();
        }
    }
    
    public PrintWriter getPrintWriter() {
        return printWriter;
    }
    public String getNomUtil(){
        return this.nomutil;
    }
    public Salon getSalon() {
        return salon;
    }
    public void setSalon(Salon salon) {
        this.salon = salon;
    }
    public Socket getSocketClient() {
        return socketClient;
    }
    public void quitter(){
        Server server = null;
        if (this.salon != null) {
            server = this.salon.getServer();
            salon.removeSession(this);
        }
        try{
        PrintWriter printWriter = new PrintWriter(this.getSocketClient().getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(this.getSocketClient().getInputStream()));
        Scanner sc = new Scanner(System.in);
        printWriter.println(Couleur.YELLOW_BOLD+"Voulez-vous vous déconnecter du serveur(/quit) ou vous reconnecter à un salon(/reco) ?"+Couleur.WHITE);
        printWriter.flush();
        String commande = sc.nextLine();
        while (!(commande.equals("/quit")) && !(commande.equals("/reco"))){
            commande = sc.nextLine();
            printWriter.println(commande);
            printWriter.flush();
            if (!(sc.nextLine().equals("/quit")) && !(sc.nextLine().equals("/reco"))){
                printWriter.println(Couleur.RED_BOLD+"Commande inconnue"+Couleur.WHITE);
                printWriter.flush();
            }
            }
        if (commande.equals("/quit")){
            this.fermer();}
        else if (commande.equals("/reco")){
            server.reconnecter(this);
            String recu = in.readLine();
            System.out.println(recu);
            recu = in.readLine();
            System.out.println(recu);
            while (!(recu.startsWith(Couleur.PURPLE_BOLD+"Pour"))){
                try{
                String numSalon = sc.nextLine();
                printWriter.println(numSalon);
                printWriter.flush();
                recu = in.readLine();
                System.out.println(recu);
                recu = in.readLine();
                System.out.println(recu);}
                catch (Exception e) {
                    printWriter.println(Couleur.RED_BOLD+"Entrez un nombre"+Couleur.WHITE);
                }
                }
                }
            }
            
            catch (Exception e) {
                printWriter.println(Couleur.RED_BOLD+"Erreur de connexion"+Couleur.WHITE);
            }
    }
    public void fermer(){
        this.salon = null;
        printWriter.println(Couleur.RED_BOLD+"Fermeture forcée du serveur"+Couleur.WHITE);
        printWriter.flush();
        printWriter.close();
        try {
            this.socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    printWriter.println(Couleur.BLUE_BOLD+"Il y a " + salon.getSessions().size() + " utilisateurs connectés"+Couleur.WHITE);
                    printWriter.flush();
                }
                else if (output.equals("/users")){
                    String liste = Couleur.BLUE_BOLD+"Utilisateurs connectés "+salon.getNomsUtils(nomutil)+Couleur.WHITE;
                    printWriter.println(liste);
                    printWriter.flush();
                }
                else if (output.equals("uptime")){
                    String temps = Couleur.BLUE_BOLD+salon.getUptime()+Couleur.WHITE;
                    printWriter.println(temps);
                    printWriter.flush();
                }
                else if (output.equals("/help")){
                    String liste = Couleur.BLUE_BOLD+"Commandes disponibles : /quit, /nbuser, /users, uptime, /help, @nomutil message"+Couleur.WHITE;
                    printWriter.println(liste);
                    printWriter.flush();
                }
                else if (output.startsWith("@")){
                    String[] parts = output.split(" ", 2);
                    String message = parts[1];
                    String send = this.nomutil;
                    String nom = parts[0].substring(1);
                    boolean trouve = false;
                    for (Session session : salon.getSessions()){
                        if (nom.equals(session.getNomUtil())){
                        session.getPrintWriter().println(Couleur.YELLOW_BOLD+"[message privé de "+send + "] " + message+Couleur.WHITE);
                        session.getPrintWriter().flush();
                        trouve = true;
                        }
                    }
                    if (!trouve){
                        printWriter.println(Couleur.RED_BOLD+"Utilisateur introuvable"+Couleur.WHITE);
                        printWriter.flush();
                    }
                    }
                else if (output.startsWith("/")){
                    printWriter.println(Couleur.RED_BOLD+"Commande inconnue"+Couleur.WHITE);
                    printWriter.flush();
                }
                else if (output.equals("") || output.equals(" ")){
                    printWriter.println(Couleur.RED_BOLD+"Message vide"+Couleur.WHITE);
                    printWriter.flush();
                }
                else{
                String message = Couleur.GREEN_BOLD+"["+this.nomutil + "] " +Couleur.WHITE+ output;
                for (Session session : salon.getSessions()){
                    if (!(this.nomutil.equals(session.getNomUtil()))){
                    session.getPrintWriter().println(message);
                    session.getPrintWriter().flush();}
                }}
            }
        } catch (Exception e) {
            
        }
        finally {
            this.quitter();
        }
    }

    
    
    
}
