import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Salon {
    private List<Session> sessions;
    private Server server;
    private String nomSalon;
    private Integer nbutil;
    private Instant heureDeb;

    public Salon(Server server, String nomSalon) {
        this.sessions = new ArrayList<Session>();
        this.server = server;
        this.nomSalon = nomSalon;
        this.nbutil = 0;
        this.heureDeb = Instant.now();
        
    }

    public List<Session> getSessions() {
        return sessions;
    }
    public String getNomSalon() {
        return nomSalon;
    }
    public void ajoutSession(Session session) {
        sessions.add(session);
        nbutil++;
    }
    public String getNomsUtils(String nomutil){
        String noms = "";
        for (Session session : sessions){
            noms += " | "+session.getNomUtil();
        }
        return noms;
    }
    public Server getServer() {
        return server;
    }
    public int getNbUtil(){
        return nbutil;
    }

    public String getUptime(){
        Instant end = Instant.now();
        Duration duration = Duration.between(heureDeb, end);
        String uptime = duration.toHours()+":"+duration.toMinutes()+":"+duration.getSeconds();
        return uptime;
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        nbutil--;
    }

    public void fermer(){
        for (Session session : sessions){
            session.fermer();
        }
        sessions.clear();
    }
}
