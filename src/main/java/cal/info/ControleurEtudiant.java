package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

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

        // 1. On récupère le tuyau d'entrée (les données du client)
        InputStream fluxEntree = echange.getRequestBody();

        // 2. On lit TOUT le contenu du tuyau et on le transforme en texte
        String corpsRecu = new String(
                fluxEntree.readAllBytes(),
                StandardCharsets.UTF_8
        );

        // 3. On ferme le tuyau (bonne pratique)
        fluxEntree.close();

        // 4. On affiche ce qu'on a reçu pour vérifier
        System.out.println("Données reçues du client : " + corpsRecu);
        System.out.println("Mon etudiant vont être ajoutés");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Etudiant ajouté avec succès !";
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();

    }

    public void afficherEtudiants(HttpExchange echange) throws IOException {

        System.out.println("Mes etudiants vont être affichés");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Liste d'étudiants !";
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }

    public void modifierEtudiant(HttpExchange echange) throws IOException {
        // 1. On récupère le tuyau d'entrée (les données du client)
        InputStream fluxEntree = echange.getRequestBody();

        // 2. On lit TOUT le contenu du tuyau et on le transforme en texte
        String corpsRecu = new String(
                fluxEntree.readAllBytes(),
                StandardCharsets.UTF_8
        );

        // 3. On ferme le tuyau (bonne pratique)
        fluxEntree.close();

        // 4. On affiche ce qu'on a reçu pour vérifier
        System.out.println("Données reçues du client : " + corpsRecu);

        System.out.println("Mon etudiant va être modifié");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Etudiant modifié avec succès !";
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();

    }

    public void supprimerEtudiant(HttpExchange echange) throws IOException {
        System.out.println("Mon etudiant va être supprimé");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Etudiant supprimé avec succès !";
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }

}
