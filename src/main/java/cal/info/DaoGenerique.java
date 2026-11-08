package cal.info;

import java.util.List;

public interface DaoGenerique<T, Tid> {
    List<T> trouverTous();
    T trouverParId(Tid id);
    T sauvegarder(T entite);
    boolean supprimer(Tid id);
    boolean existe(Tid id);
}
