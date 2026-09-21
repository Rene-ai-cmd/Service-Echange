package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ControleurEtudiant implements HttpHandler {

    @Override
    public void handle(HttpExchange echange) throws IOException {

        // On regarde le TYPE de requête pour savoir quoi faire
        String typeRequete = echange.getRequestMethod();

        switch (typeRequete) {
            case "POST":
                ajouterEtudiant(echange);
                break;            // ⚠️ Ne pas oublier le break !
            case "GET":
                afficherEtudiants(echange);
                break;
            case "PATCH":
                modifierEtudiant(echange);
                break;
            case "DELETE":
                supprimerEtudiant(echange);
                break;
            default:
                break;
        }
    }

    public void ajouterEtudiant(HttpExchange echange) throws IOException {

        new String(echange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);



        System.out.println("Mes etudiants vont être ajoutés");
    }

    public void afficherEtudiants(HttpExchange echange) {
        System.out.println("Mes etudiants vont être affichés");
    }

    public void modifierEtudiant(HttpExchange echange) {
        System.out.println("Mon etudiant va être modifié");
    }

    public void supprimerEtudiant(HttpExchange echange) {
        System.out.println("Mon etudiant va être supprimé");
    }

}
