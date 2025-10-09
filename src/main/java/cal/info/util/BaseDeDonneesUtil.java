package cal.info.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDeDonneesUtil {
    private static final String DEFAULT_URL = "jdbc:h2:./data/hackathonDB"; // Mode fichier
    private static final String TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"; // Mode mémoire
    private static final String USER = "yo";
    private static final String PASSWORD = "";

    private static final String URL;

    static {
        // Déterminer si nous sommes en mode test ou production
        if (Boolean.parseBoolean(System.getProperty("testMode"))) {
            URL = TEST_URL;
        } else {
            URL = DEFAULT_URL;
        }

        try {
            // Charger le driver H2
            Class.forName("org.h2.Driver");

            // Initialiser la base de données en créant la table si elle n'existe pas
            initialiserBase();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void initialiserBase() {
        String creationTableSQL = "CREATE TABLE IF NOT EXISTS Hackathon ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nomHackathon VARCHAR(255) NOT NULL,"
                + "dateHackathon VARCHAR(50) NOT NULL,"
                + "lieuHackathon VARCHAR(255) NOT NULL,"
                + "urlHackathon VARCHAR(255) NOT NULL"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(creationTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
