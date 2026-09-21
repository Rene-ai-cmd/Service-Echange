package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ControleurHackathon implements HttpHandler {

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

    public void listeHackathons(HttpExchange echange) {
        System.out.println("Mes hackathons vont être listés");
    }

    public void rechercheHackathons(HttpExchange echange) {
        System.out.println("Mes hackathons vont être recherchés");
    }

    public void ajouterHackathon(HttpExchange echange) {
        System.out.println("Mon hackathon va etre ajouté");
    }

    public void modifierHackathon(HttpExchange echange) {
        System.out.println("Mon hackathon va etre modifié");
    }

    public void supprimerHackathon(HttpExchange echange) {
        System.out.println("Mon hackathon va être supprimé");
    }

}
