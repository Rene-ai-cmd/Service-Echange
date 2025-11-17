package cal.info;

import cal.info.donnes.HackathonDAO;
import cal.info.metier.GestionHackathon;
import cal.info.presentation.ControleurEtudiant;
import cal.info.presentation.ControleurHackathon;
import cal.info.util.BaseDeDonneesUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );

        // Création du serveur HTTP qui écoutera sur le port 8000
        HttpServer serveur = HttpServer.create(new InetSocketAddress(8000), 0);

        // Première route "/accueil" :
        serveur.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange echange) throws IOException {

                String response = "Une belle application en devenir";
                echange.sendResponseHeaders(200, response.length());
                OutputStream os = echange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // Pool de connexion
        BaseDeDonneesUtil.initialiser();
        DataSource dataSource = BaseDeDonneesUtil.obtenirDataSource();

        // Injection de dépendances
        HackathonDAO dao = new HackathonDAO(dataSource);
        GestionHackathon gestionHackathon = new GestionHackathon(dao);
        ControleurHackathon controleurHackathon = new ControleurHackathon(gestionHackathon);
        serveur.createContext("/hackathon", controleurHackathon);

        serveur.createContext("/etudiant", new ControleurEtudiant());
        
        // Démarrer le serveur
        serveur.setExecutor(null); // Créer un exécuteur par défaut
        serveur.start();

        System.out.println("Serveur démarré et en écoute sur le port 8000");



    }



}
