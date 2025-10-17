package cal.info;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class ControleurEtudiant implements HttpHandler {

    public GestionHackathon gestionHackathon = new GestionHackathon();

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange echange) throws IOException {
        String typeRequete = echange.getRequestMethod();
        try {
            switch (typeRequete) {
                case "POST":
                    ajouterEtudiants(echange);
                default:
                    echange.sendResponseHeaders(405, -1); // Method Not Allowed
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void ajouterEtudiants(HttpExchange echange) throws IOException {
        InputStream corpRequete = echange.getRequestBody();
        InputStreamReader lecteur = new InputStreamReader(corpRequete, StandardCharsets.UTF_8);

       List<Etudiant> etudiants = mapper.readValue(lecteur, new TypeReference<List<Etudiant>>() {});
//       gestionHackathon.ajouterEtudiant(etudiants);

        String reponse = "Le Hackathon a bien été ajouté";
        byte[] reponseEncodee = reponse.getBytes(StandardCharsets.UTF_8);

        echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        echange.sendResponseHeaders(201, reponseEncodee.length);

        OutputStream os = echange.getResponseBody();
        os.write(reponseEncodee);
        os.close();
    }






}
