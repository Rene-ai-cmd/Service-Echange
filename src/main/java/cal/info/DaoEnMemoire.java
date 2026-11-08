package cal.info;

import java.util.*;

public abstract class DaoEnMemoire<T, Tid extends Comparable<Tid>> implements DaoGenerique<T, Tid> {
    //protected Map<Tid, T> stockage = new HashMap<>();
    protected Map<Tid, T> stockage = new TreeMap<>();

    @Override
    public List<T> trouverTous() {
        return new ArrayList<>(stockage.values());
    }

    @Override
    public T trouverParId(Tid id) {
        return stockage.get(id);
    }

    @Override
    public T sauvegarder(T entite) {
        Tid id = extraireId(entite);
        stockage.put(id, entite);
        return entite;
    }

    @Override
    public boolean supprimer(Tid id) {
        return stockage.remove(id) != null;
    }

    @Override
    public boolean existe(Tid id) {
        return stockage.containsKey(id);
    }

    // Méthode abstraite pour extraire l'ID de l'entité
    protected abstract Tid extraireId(T entite);
}
