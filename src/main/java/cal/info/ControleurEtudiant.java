package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class ControleurEtudiant implements HttpHandler {

    @Override
    public void handle(HttpExchange echange) throws IOException {

        // On regarde le TYPE de requête pour savoir quoi faire
        String typeRequete = echange.getRequestMethod();

        switch (typeRequete) {
            case "POST":
                ajouterEtudiant();
                break;            // ⚠️ Ne pas oublier le break !
            case "GET":
                afficherEtudiants();
                break;
            case "PATCH":
                modifierEtudiant();
                break;
            case "DELETE":
                supprimerEtudiant();
                break;
            default:
                break;
        }
    }

    public void ajouterEtudiant()  {
        System.out.println("Mes etudiants vont être ajoutés");
    }

    public void afficherEtudiants() {
        System.out.println("Mes etudiants vont être affichés");
    }

    public void modifierEtudiant() {
        System.out.println("Mon etudiant va être modifié");
    }

    public void supprimerEtudiant() {
        System.out.println("Mon etudiant va être supprimé");
    }

}
