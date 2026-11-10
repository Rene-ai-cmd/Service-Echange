package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ControleurHackathon implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HackathonDAO hackathonDAO = new HackathonDAO();

    @Override
    public void handle(HttpExchange echange) throws IOException {
        String[] path = echange.getRequestURI().getPath().split("/");
        Integer idHackathon = path.length > 2 ? Integer.parseInt(path[2]) : null;

        switch (echange.getRequestMethod()) {
            case "POST":
                ajouterHackathon(echange);
                break;
            case "GET":
                if (path.length <= 2)
                    listerHackathons(echange);
                else
                    trouverHackathon(echange, idHackathon);
                break;
            case "PUT":
                modifierHackathon(echange, idHackathon);
                break;
            case "DELETE":
                supprimerHackathon(echange, idHackathon);
                break;
            default:
                String reponse = "Fonctionnalité non prise en charge.";
                byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
                echange.sendResponseHeaders(405, octetsReponse.length);
                OutputStream fluxSortie = echange.getResponseBody();
                fluxSortie.write(octetsReponse);
                fluxSortie.close();
                break;
        }
    }

    public void listerHackathons(HttpExchange echange) throws IOException {
        System.out.println("Les hackathons vont être listés");

        // ÉTAPE 1 : préparer le texte de la réponse
        byte[] hackathonsSerialise = objectMapper.writeValueAsBytes(hackathonDAO.trouverTous());

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, hackathonsSerialise.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis le fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(hackathonsSerialise);
        fluxSortie.close();
    }

    public void trouverHackathon(HttpExchange echange, Integer id) throws IOException {
        System.out.println("On cherche le hackathon " + id);

        // ÉTAPE 1 : préparer le texte de la réponse
        byte[] hackathonSerialise = objectMapper.writeValueAsBytes(hackathonDAO.trouverParId(id));

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK ou 404 = Introuvable, + la longueur du corps)
        int codeHTTP = hackathonDAO.existe(id) ? 200 : 404; // Au cas où que le hackathon demandé soit introuvable
        echange.sendResponseHeaders(codeHTTP, hackathonSerialise.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis le fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(hackathonSerialise);
        fluxSortie.close();
    }

    public void ajouterHackathon(HttpExchange echange) throws IOException {
        // 1. On récupère le tuyau d'entrée (les données du client)
        InputStream fluxEntree = echange.getRequestBody();

        // 2. On lit TOUT le contenu du tuyau et on le transforme en texte
        String corpsRecu = new String(fluxEntree.readAllBytes(), StandardCharsets.UTF_8);

        // 3. On ferme le tuyau (bonne pratique)
        fluxEntree.close();

        // 4. On affiche ce qu'on a reçu pour vérifier
        System.out.println("Données reçues du client : " + corpsRecu);

        // 5. On désérialise l'objet reçu
        Hackathon hackathonRecu = objectMapper.readValue(corpsRecu, Hackathon.class);

        // 6. On ajoute le hackathon à la liste (s'il n'existe pas déjà)
        if (hackathonDAO.existe(hackathonRecu.getId()))
            throw new IllegalArgumentException("Le id " + hackathonRecu.getId() + " est déjà pris!");

        System.out.println("Le hackathon va être ajouté");
        hackathonRecu = hackathonDAO.sauvegarder(hackathonRecu);

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Hackathon ajouté avec succès : " + hackathonRecu.toString();
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis le fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }

    public void modifierHackathon(HttpExchange echange, Integer id) throws IOException {
        // 1. On récupère le tuyau d'entrée (les données du client)
        InputStream fluxEntree = echange.getRequestBody();

        // 2. On lit TOUT le contenu du tuyau et on le transforme en texte
        String corpsRecu = new String(fluxEntree.readAllBytes(), StandardCharsets.UTF_8);

        // 3. On ferme le tuyau (bonne pratique)
        fluxEntree.close();

        // 4. On affiche ce qu'on a reçu pour vérifier
        System.out.println("Données reçues du client : " + corpsRecu);

        // 5. On désérialise l'objet reçu
        Hackathon hackathonRecu = objectMapper.readValue(corpsRecu, Hackathon.class);

        // 6. On remplace le hackathon dans la liste
        System.out.println("Le hackathon " + id + " va être modifié");
        if (hackathonRecu.getId() == 0)
            hackathonRecu.setId(id);
        else if (hackathonRecu.getId() != id)
            hackathonDAO.supprimer(id); // Si on veut modifier l'id de l'objet, il faut le supprimer avant de l'ajouter
        hackathonRecu = hackathonDAO.sauvegarder(hackathonRecu);

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Hackathon modifié avec succès : " + hackathonRecu.toString();
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis le fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }

    public void supprimerHackathon(HttpExchange echange, Integer id) throws IOException {
        String reponse;
        int codeHTTP;

        if (id != null) {
            System.out.println("Le hackathon " + id + " va être supprimé");
            if (hackathonDAO.supprimer(id)) {
                reponse = "Hackathon supprimé avec succès.";
                codeHTTP = 200;
            }  else {
                reponse = "Identifiant Hackathon introuvable!";
                codeHTTP = 404;
            }
        } else {
            reponse = "Identifiant Hackathon manquant!";
            codeHTTP = 400;
        }

        //REPONSE
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
        echange.sendResponseHeaders(codeHTTP, octetsReponse.length);
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }
}
