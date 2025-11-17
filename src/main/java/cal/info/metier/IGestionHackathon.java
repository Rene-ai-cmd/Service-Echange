package cal.info.metier;

import cal.info.modele.Etudiant;

import java.sql.SQLException;
import java.util.List;

public interface IGestionHackathon {

    void ajouterEtudiant(List<Etudiant> etudiants) throws SQLException;

}
