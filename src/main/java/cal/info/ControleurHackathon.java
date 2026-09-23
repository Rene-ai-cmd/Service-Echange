package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ControleurHackathon implements HttpHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange echange) throws IOException {

        String typeRequete = echange.getRequestMethod();

        switch (typeRequete) {
            case "POST":
                ajouterHackathon(echange);
                break;            // ⚠️ Ne pas oublier le break !
            case "GET":
                listeHackathons(echange);
                break;
            case "PATCH":
                modifierHackathon(echange);
                break;
            case "DELETE":
                supprimerHackathon(echange);
                break;
            default:
                break;
        }
    }

    public void listeHackathons(HttpExchange echange) throws IOException {
        System.out.println("Mes hackathons vont être listés");

        Hackathon hackathonTest = new Hackathon("Hack Canada àéî()", "2027-01-08", "Guelph, Ontario", "https://hackcanada.org");
        byte[] hackathonSerialise = objectMapper.writeValueAsBytes(hackathonTest);

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
//        String reponse = "Liste des Hackathons !";
//        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, hackathonSerialise.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(hackathonSerialise);
        fluxSortie.close();
    }

    public void ajouterHackathon(HttpExchange echange) throws IOException {
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

        // 5. Je Déserialise
        Hackathon hackathonRecu = objectMapper.readValue(corpsRecu, Hackathon.class);


        System.out.println("Mon hackathon va etre ajouté");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Hackathon ajouté avec succès : " + hackathonRecu.toString();
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();



    }

    public void modifierHackathon(HttpExchange echange) throws IOException {

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
        System.out.println("Données reçues du hackathon : " + corpsRecu);
        System.out.println("Mon hackathon va etre modifié");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Hackathon modifié avec succès !";
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }

    public void supprimerHackathon(HttpExchange echange) throws IOException {
        System.out.println("Mon hackathon va être supprimé");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Hackathon supprimé avec succès !";
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }

}
