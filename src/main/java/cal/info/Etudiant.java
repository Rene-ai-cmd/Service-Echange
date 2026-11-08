package cal.info;

import java.util.ArrayList;
import java.util.List;

public class Etudiant {

    private int id = 0;
    private String nom;
    private String programme;
    private Integer matricule;
    private List<Hackathon> preferencesHackathons;

    public Etudiant() {
        // Constructeur par défaut requis par la librarie Jackson-databind
    }

    public Etudiant(String nom, String programme, int matricule) {
        this.nom = nom;
        this.programme = programme;
        this.matricule = matricule;
        this.preferencesHackathons = new ArrayList<>();
    }

    public Etudiant(int id, String nom, String programme, int matricule) {
        this(nom, programme, matricule);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public void ajouterUnePreference(Hackathon hackathon){
        this.preferencesHackathons.add(hackathon);
    }

    public List<Hackathon> getPreferencesHackathons() {
        return this.preferencesHackathons;
    }

    @Override
    public boolean equals(Object obj) {
        // si l'objet est comparé à lui-même
        if (this == obj) return true;

        // si l'objet comparé est null ou de type différent
        if (obj == null || getClass() != obj.getClass()) return false;

        // Alors, on peut convertir l'objet en Etudiant
        Etudiant etudiant = (Etudiant) obj;

        // retourner vrai si le matricule et le nom de l'etudiant sont les mêmes
        return matricule.equals(etudiant.matricule) && nom.equals(etudiant.nom);
    }

    @Override
    public int hashCode() {
        // utiliser des nombres premiers et utiliser les méthodes hashCode()
        // pour chaque attribut utilisé dans equals()
        return 31 * matricule.hashCode() + nom.hashCode();
    }

    @Override
    public String toString() {
        return "On dit bonjour à : {" +
                "\"nom\": \"" + nom + "\"" +
                ", \"programme\": \"" + programme + "\"" +
                ", \"matricule\": \"" + matricule + "\"" +
                ", \"preferences\": \"" + preferencesHackathons + "\"" +
                "}";
    }
}
