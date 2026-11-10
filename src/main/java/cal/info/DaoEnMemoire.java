package cal.info;

import java.util.*;

public abstract class DaoEnMemoire<T, Tid extends Comparable<Tid>> implements DaoGenerique<T, Tid> {
    //protected Map<Tid, T> stockage = new HashMap<>();
    protected TreeMap<Tid, T> stockage = new TreeMap<>(); // On préfère garder les données en ordre (pour la lisibilité)

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
        if (id == null) // On peut laisser le stockage assigner un ID unique à une nouvelle entité (comme une DB ferait)
            id = assignerId(entite);
        stockage.put(id, entite);
        return entite;
    }

    @Override
    public boolean supprimer(Tid id) {
        return stockage.remove(id) != null;
    }

    @Override
    public boolean existe(Tid id) {
        //return trouverParId(id) != null;
        return stockage.containsKey(id); // plus rapide
    }

    // Méthode abstraite pour extraire l'ID de l'entité
    protected abstract Tid extraireId(T entite);

    // Méthode abstraite pour générer un nouvel ID unique
    protected abstract Tid assignerId(T entite);
}
