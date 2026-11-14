package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class App  {
    public static void main(String[] args) throws IOException, SQLException {
        // Initialisation du pool de connexions à la DB
        ConnectionPool.initialiserPool();

        // Initialiser la base de données en créant les tables si elles n'existent pas
        ConnectionPool.initialiserBase();

        // Exemples pour tester les requêtes à la DB
        GestionHackathon ga = new GestionHackathon();
        ga.sauvegarder(new Hackathon("HackCanada()", "2027-01-08", "Guelph, Ontario", "https://hackcanada.org"));
        System.out.println(ga.trouverTous());
        System.out.println(ga.trouverParId(1));
        System.out.println(ga.supprimer(2));
        System.out.println(ga.existe(3));

        System.out.println( "Hello la belle gang que vous etes." );

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

        serveur.createContext("/etudiant", new ControleurEtudiant());
        serveur.createContext("/hackathon", new ControleurHackathon());

        // Démarrer le serveur
        serveur.setExecutor(null); // Créer un exécuteur par défaut
        serveur.start();

        System.out.println("Serveur démarré et en écoute sur le port 8000");
    }
}
