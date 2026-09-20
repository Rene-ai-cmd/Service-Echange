package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class ControleurEtudiant implements HttpHandler {

    @Override
    public void handle(HttpExchange echange) throws IOException {

        String typeRequete = echange.getRequestMethod();
        switch (typeRequete) {
            case "POST":
                ajouterEtudiants();
            default:
                break;
        }
    }

    public void ajouterEtudiants()  {
        System.out.println("Mes etudiants vont être ajoutés");
    }

    public void afficherEtudiants() {
        System.out.println("Mes etudiants vont être affichés");
    }

    public void modifierEtudiant() {
        System.out.println("Mon etudiant va être modifié");

    }

    public void supprimer() {
        System.out.println("Mon etudiant va être supprimé");

    }

}
