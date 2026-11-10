package cal.info;

import java.util.List;

public interface DaoGenerique<T, Tid> {
    List<T> trouverTous() throws Exception;
    T trouverParId(Tid id) throws Exception ;
    T sauvegarder(T entite) throws Exception;
    boolean supprimer(Tid id) throws Exception;
    boolean existe(Tid id) throws Exception;
}
