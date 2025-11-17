package cal.info.metier;

import cal.info.Etudiant;
import cal.info.Hackathon;
import cal.info.HackathonDAO;

import java.sql.SQLException;
import java.util.List;

public class GestionHackathon {

    public GestionHackathon(HackathonDAO hackathonDAO) {
        this.dao = hackathonDAO;
    }

    HackathonDAO dao;

    public void ajouterHackathon(Hackathon hackathon) throws SQLException {
        dao.ajouterHackathon(hackathon);
    }
}
