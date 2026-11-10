package cal.info;

public class Hackathon {

    private int id = 0; // L'id par défaut (temporaire) est explicitement `0`
    private String nom;
    private String date;
    private String lieu;
    private String url;

    public Hackathon() {
        // Constructeur par défaut requis par la librairie Jackson-databind
    }

    public Hackathon(String nom, String date, String lieu, String url) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.url = url;
    }

    public Hackathon(int id, String nom, String date, String lieu, String url) {
        this(nom, date, lieu, url);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return '{' +
                "\"id\": " + id +
                ", \"nom\": \"" + nom + "\"" +
                ", \"date\": \"" + date + "\"" +
                ", \"lieu\": \"" + lieu + "\"" +
                ", \"url\": \"" + url + "\"" +
                '}';
    }
}
