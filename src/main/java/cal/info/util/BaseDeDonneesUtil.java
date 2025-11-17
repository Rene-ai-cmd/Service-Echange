package cal.info.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class BaseDeDonneesUtil {

    private static HikariDataSource dataSource;

    public static void initialiser() {
        HikariConfig config = new HikariConfig();

        // Configuration de la base de données H2
        config.setJdbcUrl("jdbc:h2:./data/hackathonDB");
        config.setUsername("yo");
        config.setPassword("");
        config.setDriverClassName("org.h2.Driver");

        // Configuration du pool
        config.setMaximumPoolSize(10);        // Maximum 10 connexions
        config.setMinimumIdle(2);             // Minimum 2 connexions en attente
        config.setConnectionTimeout(30000);    // Timeout de 30 secondes
        config.setIdleTimeout(600000);         // Fermer après 10 minutes d'inactivité
        config.setMaxLifetime(1800000);        // Durée de vie maximale : 30 minutes

        // Nom du pool (pour le debugging)
        config.setPoolName("PoolH2Principal");

        // Requête de test de connexion
        config.setConnectionTestQuery("SELECT 1");

        // Auto-commit désactivé par défaut (bonne pratique)
        config.setAutoCommit(true);

        // Créer le pool
        dataSource = new HikariDataSource(config);

        System.out.println("✓ Pool de connexion initialisé");
    }

    /**
     * Obtenir le DataSource (pool de connexion)
     */
    public static DataSource obtenirDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException(
                    "Le pool n'est pas initialisé. Appelez initialiser() d'abord."
            );
        }
        return dataSource;
    }

    /**
     * Fermer le pool (à appeler à la fin de l'application)
     */
    public static void fermer() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("✓ Pool de connexion fermé");
        }
    }

//    private static final String DEFAULT_URL = "jdbc:h2:./data/hackathonDB"; // Mode fichier
//    private static final String TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"; // Mode mémoire
//    private static final String USER = "yo";
//    private static final String PASSWORD = "";
//
//    private static final String URL;
//
//    static {
//        // Déterminer si nous sommes en mode test ou production
//        if (Boolean.parseBoolean(System.getProperty("testMode"))) {
//            URL = TEST_URL;
//        } else {
//            URL = DEFAULT_URL;
//        }
//
//        try {
//            // Charger le driver H2
//            Class.forName("org.h2.Driver");
//
//            // Initialiser la base de données en créant la table si elle n'existe pas
//            initialiserBase();
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void initialiserBase() {
//        String creationTableSQL = "CREATE TABLE IF NOT EXISTS Hackathon ("
//                + "id INT AUTO_INCREMENT PRIMARY KEY,"
//                + "nomHackathon VARCHAR(255) NOT NULL,"
//                + "dateHackathon VARCHAR(50) NOT NULL,"
//                + "lieuHackathon VARCHAR(255) NOT NULL,"
//                + "urlHackathon VARCHAR(255) NOT NULL"
//                + ");";
//
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement()) {
//            stmt.execute(creationTableSQL);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
}
