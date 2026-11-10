package cal.info;

import java.util.List;

public class HackathonDAO extends DaoEnMemoire<Hackathon, Integer> {

    @Override
    protected Integer extraireId(Hackathon entite) {
        return entite.getId() > 0 ? entite.getId() : null;
    }

    @Override
    protected Integer assignerId(Hackathon entite) {
        int lastId = stockage.isEmpty() ? 0 : stockage.lastKey();
        entite.setId(++lastId);
        return lastId;
    }

    public List<Hackathon> trouverParNom(String nom) {
        return stockage.values().stream()
                .filter(hackathon -> hackathon.getNom().equalsIgnoreCase(nom))
                .toList();
    }

    public List<Hackathon> hackathonsApres(String date) {
        return stockage.values().stream()
                .filter(hackathon -> hackathon.getDate().compareTo(date) > 0)
                .toList();
    }
}
