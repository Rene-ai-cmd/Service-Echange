package cal.info;

import java.util.List;

public interface DaoGenerique<T, Tid> {
    List<T> trouverTous() throws Exception;
    T trouverParId(Tid id) throws Exception ; // Retourne `null` si l'id est introuvable.
    T sauvegarder(T entite) throws Exception;
    // ^Ici on retourne l'objet reçu en paramètre pour indiquer qu'il pourrait avoir été modifié lors de la sauvegarde.
    boolean supprimer(Tid id) throws Exception;
    boolean existe(Tid id) throws Exception;
    // ^Cette méthode revient à faire `trouverParId(id) != null`, mais c'est plus clair (et rapide) comme ça.
}
