package cal.info;

import java.util.List;

public class HackathonDAO extends DaoEnMemoire<Hackathon, Integer> {

    @Override
    protected Integer extraireId(Hackathon entite) {
        return entite.getId();
    }

    public List<Hackathon> trouverParNom(String nom) {
        return stockage.values().stream()
                .filter(hackathon -> hackathon.getNomHackathon().equalsIgnoreCase(nom))
                .toList();
    }

    public List<Hackathon> hackathonsApres(String date) {
        return stockage.values().stream()
                .filter(hackathon -> hackathon.getDateHackathon().compareTo(date) > 0)
                .toList();
    }
}
