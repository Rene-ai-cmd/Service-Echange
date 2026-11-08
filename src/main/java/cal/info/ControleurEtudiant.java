package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ControleurEtudiant implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Etudiant> listeTemporaire = new ArrayList<>();
    private final Map<String, Set<Etudiant>> etudiantsParProg = new HashMap<>();

    public ControleurEtudiant() {
        Etudiant etudiantTest = new Etudiant(1, "Yohann Ritter àéîøü", "Informatique", 111);
        Hackathon hackathonTest = new Hackathon(1, "HackCanada()", "2027-01-08", "Guelph, Ontario", "https://hackcanada.org");
        etudiantTest.ajouterUnePreference(hackathonTest);
        etudiantTest.ajouterUnePreference(hackathonTest);

        // 6. Ajoutons l'étudiant à la liste
        listeTemporaire.add(etudiantTest);

        // 7. Ajoutons aussi l'étudiant au tableau des programmes
        String prog = etudiantTest.getProgramme();
        if (!etudiantsParProg.containsKey(prog))
            etudiantsParProg.put(prog, new HashSet<>());
        etudiantsParProg.get(prog).add(etudiantTest);
    }

    @Override
    public void handle(HttpExchange echange) throws IOException {
        String typeRequete = echange.getRequestMethod();
        switch (typeRequete) { // On regarde le TYPE de requête pour savoir quoi faire
            case "POST":
                ajouterEtudiant(echange);
                break; // ⚠️ Ne pas oublier le break !
            case "GET":
                String filtre = echange.getRequestURI().getQuery();
                if (filtre == null) {
                    listerEtudiants(echange);
                } else if (filtre.contains("idHackathon=")) {
                    String param = filtre.split("idHackathon=")[1];
                    int idHackathon = Integer.parseInt(param);
                    rechercherParHackathon(echange, idHackathon);
                } else if (filtre.contains("programme=")) {
                    String programme = filtre.split("programme=")[1];
                    rechercherParProgramme(echange, programme);
                }
                break;
            case "PATCH":
                ajouterPreferences(echange);
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
        String corpsRecu = new String(fluxEntree.readAllBytes(), StandardCharsets.UTF_8);

        // 3. On ferme le tuyau (bonne pratique)
        fluxEntree.close();

        // 4. On affiche ce qu'on a reçu pour vérifier
        System.out.println("Données reçues du client : " + corpsRecu);

        // 5. Construisons notre objet
        Etudiant etudiantRecu = objectMapper.readValue(corpsRecu, Etudiant.class);

        // 6. Ajoutons l'étudiant à la liste
        System.out.println("L'étudiant va être ajouté");
        listeTemporaire.add(etudiantRecu);

        // 7. Ajoutons aussi l'étudiant au tableau des programmes
        String prog = etudiantRecu.getProgramme();
        if (!etudiantsParProg.containsKey(prog))
            etudiantsParProg.put(prog, new HashSet<>());
        etudiantsParProg.get(prog).add(etudiantRecu);

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Etudiant ajouté avec succès : " + etudiantRecu.toString() ;
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis le fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }

    public void listerEtudiants(HttpExchange echange) throws IOException {
        System.out.println("Les etudiants vont être listés");
        byte[] etudiantsConvertis = objectMapper.writeValueAsBytes(listeTemporaire);

        echange.getResponseHeaders().set("Content-Type", "application/json");
        echange.sendResponseHeaders(200, etudiantsConvertis.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis le fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(etudiantsConvertis);
        fluxSortie.close();
    }

    private void rechercherParHackathon(HttpExchange echange, int idHackathon) throws IOException {
        System.out.println("Recherche d'étudiants par id d'hackathon (" + idHackathon + ")");

        List<Etudiant> listeFiltree = new ArrayList<>();
        for (Etudiant etudiant : listeTemporaire) {
            for (Hackathon hackathon : etudiant.getPreferencesHackathons()) {
                if (hackathon.getId() == idHackathon)
                    listeFiltree.add(etudiant);
            }
        }

        byte[] etudiantsConvertis = objectMapper.writeValueAsBytes(listeFiltree);

        echange.getResponseHeaders().set("Content-Type", "application/json");
        echange.sendResponseHeaders(200, etudiantsConvertis.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis le fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(etudiantsConvertis);
        fluxSortie.close();
    }

    private void rechercherParProgramme(HttpExchange echange, String programme) throws IOException {
        System.out.println("Recherche d'étudiants par programme (" + programme + ")");

        Set<Etudiant> listeFiltree = etudiantsParProg.getOrDefault(programme, new HashSet<>());
        byte[] etudiantsConvertis = objectMapper.writeValueAsBytes(listeFiltree);

        echange.getResponseHeaders().set("Content-Type", "application/json");
        echange.sendResponseHeaders(200, etudiantsConvertis.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis le fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(etudiantsConvertis);
        fluxSortie.close();
    }

    public void ajouterPreferences(HttpExchange echange) throws IOException {
        String filtre = echange.getRequestURI().getQuery();
        if (filtre != null && filtre.contains("matricule=")) {
            String matricule= filtre.split("matricule=")[1];
            System.out.println("L'étudiant " + matricule + " va être modifié");

            List<Integer> preferences = objectMapper.readValue(echange.getRequestBody().readAllBytes(), new TypeReference<>() {});
            // Poutine

            String reponse = "Préférences " + preferences + " ajoutées avec succès à l'étudiant " + matricule + " !";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            echange.sendResponseHeaders(200, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        } else {
            String reponse = "Matricule étudiant manquant!";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            echange.sendResponseHeaders(400, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        }
    }

    public void supprimerEtudiant(HttpExchange echange) throws IOException {
        String filtre = echange.getRequestURI().getQuery();
        if (filtre != null && filtre.contains("idEtudiant=")) {
            String param= filtre.split("idEtudiant=")[1];
            int idEtudiant = Integer.parseInt(param);
            System.out.println("L'étudiant " + idEtudiant + " va être supprimé");

            // Poutine

            String reponse = "Étudiant supprimé avec succès.";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            echange.sendResponseHeaders(200, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        } else {
            String reponse = "Identifiant étudiant manquant!";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            echange.sendResponseHeaders(400, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        }
    }
}
