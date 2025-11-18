package cal.info.metier;

import cal.info.modele.Etudiant;
import cal.info.modele.Hackathon;
import cal.info.donnes.HackathonDAO;

import java.sql.SQLException;
import java.util.List;

public class GestionHackathon {

    private final HackathonDAO dao;

    public GestionHackathon(HackathonDAO hackathonDAO) {
        this.dao = hackathonDAO;
    }

    public void ajouterHackathon(Hackathon hackathon) throws SQLException {
        dao.ajouterHackathon(hackathon);
    }

    public List<Hackathon> retrouverHackathons() throws SQLException {
        return dao.retrouverHackathons();
    }

    public void modifierHackathon(Hackathon hackathon) {
        dao.modifierHackathon(hackathon);
    }

    public void creerEtudiants(List<Etudiant>  etudiants) {
        dao.creerEtudiants(etudiants);
    }

    public void formerEquipe(List<Etudiant> etudiants, String nomEquipe) {
        dao.formerEquipe(etudiants, nomEquipe);
    }
}
