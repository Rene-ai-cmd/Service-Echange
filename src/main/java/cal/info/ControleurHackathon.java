package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ControleurHackathon implements HttpHandler {

    @Override
    public void handle(HttpExchange echange) throws IOException {

    }

    public void listeHackathons() {
        System.out.println("Mes hackathons vont être listés");

    }

    public void rechercheHackathons() {
        System.out.println("Mes hackathons vont être recherchés");
    }

    public void ajouterHackathon() {
        System.out.println("Mon hackathon va etre ajouté");

    }

    public void modifierHackathon() {
        System.out.println("Mon hackathon va etre modifié");
    }

    public void supprimerHackathon() {
        System.out.println("Mon hackathon va être supprimé");
    }

}
