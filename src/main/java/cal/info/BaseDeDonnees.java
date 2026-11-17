package cal.info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Cette classe n'est plus utilisée depuis que nous avons ajouté le ConnectionPool; on la garde juste comme référence.
public class BaseDeDonnees {
    private static final String URL = "jdbc:h2:./data/hackathonDB"; // Mode fichier
    private static final String USER = "sa"; // Utilisateur par défaut
    private static final String PASSWORD = ""; // MdP par défaut

    static { // Bloc statique: sera exécuté automatiquement dès le chargement de la classe par la JVM
        try {
            // Charger le driver H2
            Class.forName("org.h2.Driver");

            // Initialiser la base de données en créant les tables si elles n'existent pas
            initialiserBase();
        }
        catch (ClassNotFoundException | SQLException e) {
            // Afficher l'erreur complète en cas d'exception
            e.printStackTrace();
        }
    }

    private static void initialiserBase() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Hackathon (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nom VARCHAR(255) NOT NULL,
                date VARCHAR(50) NOT NULL,
                lieu VARCHAR(255) NOT NULL,
                url VARCHAR(255) NOT NULL);"""
            );
        } // finally { stmt.close(); conn.close(); }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
