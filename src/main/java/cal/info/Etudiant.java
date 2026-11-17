package cal.info;

import java.util.ArrayList;
import java.util.List;

public class Etudiant {
    private Integer matricule;
    private String nom;
    private String programme;
    private List<Hackathon> preferencesHackathons;

    public Etudiant() {
        // Constructeur par défaut requis par la librairie Jackson-databind
    }

    public Etudiant(Integer matricule, String nom, String programme) {
        this.matricule = matricule;
        this.nom = nom;
        this.programme = programme;
        this.preferencesHackathons = new ArrayList<>();
    }

    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
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
        return '{' +
                "\"matricule\": \"" + matricule + "\"" +
                ", \"nom\": \"" + nom + "\"" +
                ", \"programme\": \"" + programme + "\"" +
                ", \"preferences\": \"" + preferencesHackathons + "\"" +
                '}';
    }
}
