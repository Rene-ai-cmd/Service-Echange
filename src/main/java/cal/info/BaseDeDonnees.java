package cal.info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDeDonnees {
    private static final String URL = "jdbc:h2:./data/hackathonDB"; // Mode fichier
    private static final String USER = "sa"; // Utilisateur par défaut
    private static final String PASSWORD = ""; // MdP par défaut

    static {
        try {
            // Charger le driver H2
            Class.forName("org.h2.Driver");

            // Initialiser la base de données en créant la table si elle n'existe pas
            initialiserBase();
        }
        catch (ClassNotFoundException | SQLException e) {
            // Afficher l'erreur complète en cas d'exception
            e.printStackTrace();
        }
    }

    private static void initialiserBase() throws SQLException {
        String creationTableSQL = """
                CREATE TABLE IF NOT EXISTS Hackathon (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nom VARCHAR(255) NOT NULL,
                date VARCHAR(50) NOT NULL,
                lieu VARCHAR(255) NOT NULL,
                url VARCHAR(255) NOT NULL
                );""";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(creationTableSQL);
        } // finally { stmt.close(); conn.close(); }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
