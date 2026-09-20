package cal.info.presentation;

import cal.info.modele.Etudiant;
import cal.info.donnes.HackathonDAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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

    private void ajouterEtudiants()  {
        String reponse = "Mes etudiants vont être ajouté";
    }






}
