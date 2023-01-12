import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Salon {
    private List<Session> sessions;
    private Server server;
    private String nomSalon;
    private Integer nbutil;
    private Time heureDeb;

    public Salon(Server server, String nomSalon) {
        this.sessions = new ArrayList<Session>();
        this.server = server;
        this.nomSalon = nomSalon;
        this.nbutil = 0;
        this.heureDeb = new Time(System.currentTimeMillis());
        
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
            if (!session.getNomUtil().equals(nomutil)){
            noms += " | "+session.getNomUtil();}
        }
        return noms;
    }
    public Server getServer() {
        return server;
    }
    public int getNbUtil(){
        return nbutil;
    }

    public Time getUptime(){
        Time uptime = new Time(System.currentTimeMillis() - heureDeb.getTime());
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
